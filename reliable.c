
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <stddef.h>
#include <assert.h>
#include <poll.h>
#include <errno.h>
#include <time.h>
#include <sys/time.h>
#include <sys/socket.h>
#include <sys/uio.h>
#include <netinet/in.h>
#include <signal.h>
#include "rlib.h"

//static int quit = 0;
//void iterPackNAdd(packet_t * pack, rel_t * s);
//static int counter = 0;
//static packet_t* windowfull = NULL;

struct packetnode {
	int length;
	packet_t* packet;
	struct packetnode* next;
};

struct rec_slidingWindow {
	int rws; // upper bound on no. of out-of-order frames that the receiver is willing to accept
	int laf; // seqNum of largest acceptable frame
	int lfr; // seqNum of last frame received
	//int laf_min_lfr; // laf - lfr <= rws
	//struct packetnode* head;
};

struct send_slidingWindow {
	int sws; //upper bound on no. of unacked frames that sender can transmitt
	int lar; // sequence of last ack received
	int lfs; //last frame sent
	//int lfs_min_lar; // lfs - lar <= sws
	//struct packetnode* head;
};



struct reliable_state {
	rel_t *next;			/* Linked list for traversing all connections */
	rel_t **prev;

	conn_t *c;			/* This is the connection object */
	//packet_t * packet;
	/* Add your own data fields below this */
	int eof_seqnum;
	int eof_rec;
	int eof_read;

	packet_t** senderbuffer;
	packet_t** receiverbuffer;

	int window_size;
	int timeout_len;

	//int acknum;
	//int acked;
	//int seqNumToAck; // largest seqNum not yet ack
	// so if(seqNum <= seqNumToAck then frame is received

	/*bc rel_t gets passed btw all functions it should keep track of our sliding windows*/
	struct send_slidingWindow * send_sw;
	struct rec_slidingWindow * rec_sw;

	long* times;
};
rel_t *rel_list;


/* Creates a new reliable protocol session, returns NULL on failure.
 * Exactly one of c and ss should be NULL.  (ss is NULL when called
 * from rlib.c, while c is NULL when this function is called from
 * rel_demux.) */
rel_t *
rel_create (conn_t *c, const struct sockaddr_storage *ss,
            const struct config_common *cc)
{
	rel_t *r;


	r = xmalloc (sizeof (*r));
	memset (r, 0, sizeof (*r));

	if (!c) {
		c = conn_create (r, ss);
		if (!c) {
			free (r);
			return NULL;
		}
	}

	r->c = c;
	r->next = rel_list;
	r->prev = &rel_list;
	if (rel_list)
		rel_list->prev = &r->next;
	rel_list = r;

	//fprintf(stderr,"rel_create\n");
	/* Do any other initialization you need here */

	/*init our packet information*/
	r->window_size= cc->window;
	r->timeout_len = cc->timeout;


	r->senderbuffer = malloc(r->window_size * sizeof(packet_t));
	r->receiverbuffer = malloc(r->window_size * sizeof(packet_t));
	memset(r->senderbuffer, 0, r->window_size * sizeof(packet_t));
	memset(r->receiverbuffer, 0, r->window_size * sizeof(packet_t));

	/*init our sliding windows*/
	r->rec_sw = xmalloc(sizeof(struct send_slidingWindow));
	r->send_sw = xmalloc(sizeof(struct rec_slidingWindow));
	r->rec_sw->rws = r->window_size;//cc->window; //window size
	r->rec_sw->lfr = 0; //no frames recieved
	r->rec_sw->laf = r->rec_sw->lfr + r->rec_sw->rws; //last acceptable frame

	//will be our window size plus last seqnum accepted
	r->send_sw->sws = r->window_size;//cc->window; //window size
	r->send_sw->lar = 0; //no acks received
	r->send_sw->lfs = 0; //no frames sent so far
	//r->rec_sw->head = malloc(sizeof(struct packetnode));
	//r->send_sw->head = malloc(sizeof(struct packetnode));
	//r->send_sw->head->length = 0;
	//r->rec_sw->head->length = 0;
	r->eof_seqnum = 0;
	r->eof_read = 0;
	r->eof_rec = 0;

	r->times = malloc(r->window_size * sizeof(long));
	memset(r->times, 0, r->window_size * sizeof(long));
	return r;
}

void
rel_destroy (rel_t *r)
{
	if (r->next)
		r->next->prev = r->prev;
	*r->prev = r->next;
	conn_destroy (r->c);

	free(r->senderbuffer);
	free(r->receiverbuffer);
	free(r->times);
	free(r);
}

/* Signal Handler for SIGINT */
/*
void sigintHandler(int sig_num)
{
	//signal(SIGINT, sigintHandler);
	if (quit == 1) {
		exit(0);
	}
	//fprintf(stderr, "\n Cannot be terminated using Ctrl+C \n");
	quit = 1;
	//fflush(stdout);
}*/
/*
void rec_deletenodes(rel_t* r) {
	//fprintf(stderr,"\ninitial seqno:%d, lfr:%d",ntohl(r->rec_sw->head->packet->seqno),r->rec_sw->lfr);
	while (ntohl(r->rec_sw->head->packet->seqno) <= r->rec_sw->lfr) {
		struct packetnode * temp = r->rec_sw->head;
		r->rec_sw->head = r->rec_sw->head->next;
		free(temp);
		//fprintf(stderr,"\ndeleted rec_sw head seqno:%d, lfr:%d",ntohl(r->rec_sw->head->packet->seqno),r->rec_sw->lfr);
	}
}*/
/*
void update_rec_sw(rel_t* r) {
	struct packetnode* runner = r->rec_sw->head;
	int counter = r->rec_sw->lfr;
	while (runner->next != NULL && ntohl(runner->packet->seqno) == (counter + 1)) {
		runner = runner->next;
		counter++;
	}
	//fprintf(stderr,"seqno:%d, counter:%d",ntohl(runner->packet->seqno),counter);
	r->seqNumToAck = counter + 2;
	r->rec_sw->lfr = (r->seqNumToAck) - 2;
	r->rec_sw->laf = r->rec_sw->lfr + r->rec_sw->rws;
	rec_deletenodes(r);
}*/
/*
void retransmitSpecificPacket(rel_t * s, int seqnum)
{
	struct packetnode * current = s->send_sw->head;
	if (current == NULL) {
		//fprintf(stderr, "you're asking to transmit a packet that doesn't exist");
		return;
	}
	while (ntohl(current->packet->seqno) != seqnum) {
		if (current->next == NULL) {
			//fprintf(stderr, "you're asking to transmit a packet that doesn't exist");
		}
		current = current->next;
	}
	conn_sendpkt(s->c, current->packet, sizeof(packet_t));
}*/
/*
int iter_PackNAdd(packet_t * pack, rel_t * s)
{
	struct packetnode* current = s->send_sw->head;
	struct packetnode* prev = NULL;
	int count = 0;
	//if(s->send_sw->head->packet == NULL)
	//{
	//	return;
	//}
	while (current != NULL)
	{
		prev = current;
		current = current -> next;
		count++;
	}
	//fprintf(stderr, "last frame sent: %d", s->send_sw->lfs);
	if (count >= s->send_sw->sws) {
		windowfull = pack;
		return 0;
	}
	current = malloc(sizeof(struct packetnode));
	current->next = NULL;
	current->packet = pack;
	current->length = count + 1;
	if (prev == NULL) {
		s->send_sw->head = current;
	}
	else {
		prev->next = current;
	}
	conn_sendpkt(s->c, pack, sizeof(packet_t));
	s->send_sw->lfs = ntohl(pack->seqno);
	return 1;
}*/
/*
int rec_PackNAdd(packet_t * pack, rel_t * s)
{
	struct packetnode* current = s->rec_sw->head;
	struct packetnode* prev = NULL;
	while (current != NULL)
	{
		if (ntohl(current->packet->seqno) > ntohl(pack->seqno)) {
			break;
		}
		prev = current;
		current = current -> next;

	}
	if (prev != NULL && (ntohl(prev->packet->seqno) == ntohl(pack->seqno))) {
		return 0;
	}
	//if (count >= s->rec_sw->rws) {
	//	return 0;
	//}
	struct packetnode* new = malloc(sizeof(struct packetnode));
	new->next = current;
	packet_t *newpack = malloc(sizeof(packet_t));
	memcpy(newpack, pack, sizeof(packet_t));
	new->packet = newpack;
	new->length = 1;
	if (prev == NULL) {
		s->rec_sw->head = new;
	}
	else {
		prev->next = new;
	}
	update_rec_sw(s);
	return 1;
}*/

/* This function only gets called when the process is running as a
 * server and must handle connections from multiple clients.  You have
 * to look up the rel_t structure based on the address in the
 * sockaddr_storage passed in.  If this is a new connection (sequence
 * number 1), you will need to allocate a new conn_t using rel_create
 * ().  (Pass rel_create NULL for the conn_t, so it will know to
 * allocate a new connection.)
 */
void
rel_demux (const struct config_common *cc,
           const struct sockaddr_storage *ss,
           packet_t *pkt, size_t len)
{
}

void
rel_sendack(rel_t *r) {
	//fprintf(stderr,"sendack");
	packet_t *ackpack = malloc(sizeof(*ackpack));
	memset(ackpack, 0, sizeof(*ackpack));
	ackpack->cksum = 0;
	//r->acknum++; //Not sure if this is necessary
	ackpack->ackno = htonl((r->rec_sw->lfr + 1));
	ackpack->len = htons(sizeof(struct ack_packet)); //not sure if this is correct
	ackpack->cksum = cksum(ackpack, sizeof(struct ack_packet));
	conn_sendpkt(r->c, ackpack, sizeof(struct ack_packet));
	free(ackpack);

}

void verify_free(rel_t* r) {

	if (r->eof_rec && r->eof_read
			&& r->send_sw->lar > r->send_sw->lfs && r->receiverbuffer[0]==NULL) {
		rel_destroy(r);
	}
}

void send_packet(packet_t* pkt, rel_t* s, int index, int len) {
	struct timespec* tempTime = malloc(sizeof(struct timespec));
	clock_gettime(CLOCK_MONOTONIC, tempTime);
	long currentTime = tempTime->tv_sec * 1000000000 + tempTime->tv_nsec;
	s->times[index] = currentTime;
	free(tempTime);
	conn_sendpkt(s->c, pkt, len);
}
/*
void
rel_sendeof(rel_t *r) {
	//fprintf(stderr,"UPDATE THIS");
	packet_t* eofpack = malloc(sizeof(packet_t));
	eofpack->cksum = 0;
	eofpack->ackno = htonl(r->acknum);
	eofpack->len = htons(12); //not sure if this is correct
	eofpack->seqno = htonl(r->seqnum);
	eofpack->cksum = cksum(eofpack, ntohs(eofpack->len));
	conn_sendpkt(r->c, eofpack, sizeof(packet_t));
	r->seqnum++;
}*/
/*
void send_deletenodes(rel_t* r) {
	//fprintf(stderr,"deletenodes called");
	//fprintf(stderr,"\ninitial seqno:%d, lar:%d",ntohl(r->send_sw->head->packet->seqno),r->send_sw->lar);
	while (ntohl(r->send_sw->head->packet->seqno) < r->send_sw->lar) {
		struct packetnode * temp = r->send_sw->head;
		r->send_sw->head = r->send_sw->head->next;
		free(temp);
		//fprintf(stderr,"\ndeleted send_sw head");
	}
}*/

void
rel_recvpkt (rel_t *r, packet_t *pkt, size_t n)
{
	//fprintf(stderr, "\nrecvpkt len: %d\n", ntohs(pkt->len));
	//fprintf(stderr, "my window size is %d", r->rec_sw->rws);
	// Still need check for corrupted packet with checksum
	//int checksum = pkt->cksum;
	//int pkt_len = ntohs(pkt->len);
	//int pkt_seqno = ntohl(pkt->seqno);
	//pkt->cksum = 0;

	packet_t* newpack = malloc(ntohs(pkt->len));
	memcpy(newpack, pkt, ntohs(pkt->len));
	newpack->cksum = 0;
	newpack->cksum = cksum(newpack, ntohs(newpack->len));

	// check for corrupted packet
	if (newpack->cksum != pkt->cksum) {
		//fprintf(stderr, "Corrupted Data");
		return;
	}
	// Check for corrupted data
	//fprintf(stderr, "cksum=%d, checksum=%d", cksum(pkt, pkt_len), n);
	/*if (n != 512 || (cksum(pkt, pkt_len) != checksum)) {
		//drop pack
		//fprintf(stderr, "dropped");
		return;
	}*/
	else if (ntohs(pkt->len) < sizeof(struct ack_packet) || ntohs(pkt->len) > sizeof(pkt->data)+12) {
		return;
	} 
	//Handle Ack Packet
	else if (ntohs(pkt->len) == sizeof(struct ack_packet)) {
		//fprintf(stderr,"ackkkkkkkkkkkkkk");
		if (ntohl(pkt->ackno) == r->eof_seqnum + 1) {
			r->eof_read = 1;
		}
		if (ntohl(pkt->ackno) > r->send_sw->lar) {
			r->send_sw->lar = ntohl(pkt->ackno);
		}
		while (r->senderbuffer[0] != NULL) {
			if  (ntohl(r->senderbuffer[0]->seqno) >= r->send_sw->lar) {
				break;
			}
			int i;
			for (i = 0; i < r->window_size - 1; i++) {
				r->senderbuffer[i] = r->senderbuffer[i + 1];
				r->times[i] = r->times[i + 1];
			}
			r->senderbuffer[r->window_size - 1] = NULL;
			r->times[r->window_size - 1] = 0;
		}
		rel_read(r);
	}
	// Handle a data packet
	else {
		if (ntohl(pkt->seqno) <= r->rec_sw->lfr) {///////////////////////////////////////////
			rel_sendack(r);
		}
		else if (ntohl(pkt->seqno) > r->rec_sw->laf) {
			fprintf(stderr, "Packet is greater than largest acceptable frame");
		}


		else {
			if (ntohs(pkt->len) == 12) {
				r->eof_rec = 1;
			}
			int in = (ntohl(pkt->seqno) - (r->rec_sw->lfr + 1)) % (r->window_size);
			packet_t* temppack = malloc(sizeof(packet_t));
			memcpy(temppack, pkt, sizeof(packet_t));
			r->receiverbuffer[in] = temppack;
			rel_output(r);
			rel_sendack(r);

		}
	}

}

void
rel_read (rel_t *s)
{
	packet_t* pack = malloc(sizeof(packet_t));
	while (s->eof_seqnum < 1) {/////////////////////////
		if (s->senderbuffer[s->window_size - 1] != NULL) {
			break;
		}

		int input = conn_input(s->c, pack->data, sizeof(pack->data));
		int inputLen = input + 12;
		packet_t *temp = malloc(sizeof(packet_t));
		if (input == -1) {////////////////////////////////////////
			s->eof_seqnum = s->send_sw->lfs + 1;
			inputLen = 12;
		}
		else if (input == 0) {
			break;
		}
		memcpy(temp->data, pack->data, sizeof(temp->data));
		memset(pack, 0, sizeof(packet_t));

		int ind = 0;
		while (s->senderbuffer[ind] != NULL) {
			ind ++;
		}
		s->senderbuffer[ind] = temp;

		temp->cksum = 0;
		temp->len = htons(inputLen);
		temp->seqno = htonl(s->send_sw->lfs + 1);
		temp->ackno = htonl(s->send_sw->lar);
		temp->cksum = cksum(temp, inputLen);

		send_packet(temp, s, ind, inputLen);
		s->send_sw->lfs += 1;

	}
	free(pack);

}

void
rel_output (rel_t *r)
{
	while (r->receiverbuffer[0] != NULL) {
		int pack_size = ntohs(r->receiverbuffer[0]->len) - 12;
		int avail_buf_space = conn_bufspace(r->c);

		if (pack_size > avail_buf_space) {
			return;

		}

		conn_output(r->c, r->receiverbuffer[0]->data, pack_size);

		r->rec_sw->lfr = ntohl(r->receiverbuffer[0]->seqno);
		r->rec_sw->laf = r->rec_sw->lfr + r->window_size;

		int i;
		for (i = 0; i < r->window_size - 1; i++) {
			r->receiverbuffer[i] = r->receiverbuffer[i + 1];
		}

		r->receiverbuffer[r->window_size - 1] = NULL;
	}
	

}

void
rel_timer ()
{
	int i;
	for (i = 0; i < rel_list->window_size; i++) {
		if (rel_list->times[i] > 0) {
			struct timespec* tempTime = malloc(sizeof(struct timespec));
			clock_gettime(CLOCK_MONOTONIC, tempTime);
			long currentTime = tempTime->tv_sec * 1000000000 + tempTime->tv_nsec;
			long elapsedTime = currentTime - rel_list->times[i];
			//fprintf(stderr, "pos:%d, time:%lu\n", i, rel_list->timearray[i]);
			if (elapsedTime > rel_list->timeout_len * 1000000) {
				//fprintf(stderr, "packet seqno %d TIMEOUT, retransmitting!\n",ntohl(rel_list->senderbuffer[i]->seqno));
				send_packet(rel_list->senderbuffer[i], rel_list, i, ntohs(rel_list->senderbuffer[i]->len));
			}
			free(tempTime);
		}

	}
	verify_free(rel_list);

}


