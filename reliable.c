
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

static int quit = 0;
void iterPackNAdd(packet_t * pack, rel_t * s);
static int counter = 0;
static packet_t* windowfull = NULL;
static int eofsent=0;
static int eofseqno=0;
static int eofacked=0;

struct packetnode {
	int length;
	packet_t* packet;
	struct packetnode* next;
};

struct rec_slidingWindow {
	int rws; // upper boaund on no. of out-of-order frames that the receiver is willing to accept
	int laf; // seqNum of largest acceptable frame
	int lfr; // seqNum of last frame received
	int laf_min_lfr; // laf - lfr <= rws
	struct packetnode* head;
};

struct send_slidingWindow {
	int sws; //upper bound on no. of unacked frames that sender can transmitt
	int lar; // sequence of last ack received
	int lfs; //last frame sent
	int lfs_min_lar; // lfs - lar <= sws
	struct packetnode* head;
};



struct reliable_state {
	rel_t *next;			/* Linked list for traversing all connections */
	rel_t **prev;

	conn_t *c;			/* This is the connection object */
	packet_t * packet;
	/* Add your own data fields below this */
	int window;
	int timeout;
	int seqnum;
	char* senderbuffer;
	char* receiverbuffer;
	int acknum;
	int acked;
	int seqNumToAck; // largest seqNum not yet ack
	// so if(seqNum <= seqNumToAck then frame is received

	/*bc rel_t gets passed btw all functions it should keep track of our sliding windows*/
	struct send_slidingWindow * send_sw;
	struct rec_slidingWindow * rec_sw;
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
	r->packet = malloc(sizeof(packet_t));
	r->seqnum = 1;
	r->senderbuffer = malloc(sizeof(char));
	r->receiverbuffer = malloc(sizeof(char));
	r->acknum = 1;
	r->acked = 0;
	//fprintf(stderr,"ccwin:%d",cc->window);
	r->window = 1;//cc->window;
	r->timeout = cc->timeout;

	/*init our sliding windows*/
	r->rec_sw = xmalloc(sizeof(struct send_slidingWindow));
	r->send_sw = xmalloc(sizeof(struct rec_slidingWindow));
	r->rec_sw->rws = r->window;//cc->window; //window size
	r->rec_sw->lfr = 0; //no frames recieved
	r->rec_sw->laf = r->rec_sw->lfr + r->rec_sw->rws; //last acceptable frame
	//will be our window size plus last seqnum accepted
	r->send_sw->sws = r->window;//cc->window; //window size
	r->send_sw->lar = 0; //no acks received
	r->send_sw->lfs = 0; //no frames sent so far
	r->send_sw->lfs_min_lar = r->send_sw->lfs - r->send_sw->lar;
	r->rec_sw->laf_min_lfr = r->rec_sw->laf - r->rec_sw->lfr;
	//r->rec_sw->head = malloc(sizeof(struct packetnode));
	//r->send_sw->head = malloc(sizeof(struct packetnode));
	//r->send_sw->head->length = 0;
	//r->rec_sw->head->length = 0;
	r->seqNumToAck = 1;
	return r;
}

void
rel_destroy (rel_t *r)
{
	//fprintf(stderr,"rel_destroy\n");
	//if (r->next)
	//	r->next->prev = r->prev;
	//*r->prev = r->next;
	conn_destroy (r->c);


	/* Free any other allocated memory here */
	free(r->senderbuffer);
	free(r->receiverbuffer);
}

/* Signal Handler for SIGINT */
void sigintHandler(int sig_num)
{
	//signal(SIGINT, sigintHandler);
	if (quit == 1) {
		exit(0);
	}
	//fprintf(stderr, "\n Cannot be terminated using Ctrl+C \n");
	quit = 1;
	//fflush(stdout);
}

void rec_deletenodes(rel_t* r) {
	fprintf(stderr,"\ninitial seqno:%d, lfr:%d",ntohl(r->rec_sw->head->packet->seqno),r->rec_sw->lfr);
	fprintf(stderr, "hello%s\n", rel_list->packet->data);
	while(r->rec_sw->head!=NULL && ntohl(r->rec_sw->head->packet->seqno) <= (r->rec_sw->lfr)+1) {
		fprintf(stderr,"started");
		if (conn_bufspace(r->c)<500*sizeof(char)) { //not enough room in output buffer, don't print
			return;
		}
		int dataindex=0;
		while (dataindex<((ntohs(r->rec_sw->head->packet->len)-12)/sizeof(char))) {
			*(r->receiverbuffer) = r->rec_sw->head->packet->data[dataindex];
			conn_output(r->c, (void *)(r->receiverbuffer), sizeof(char));
			//rel_output(r);
			dataindex++;
		}
		struct packetnode * temp = r->rec_sw->head;
		r->rec_sw->head = r->rec_sw->head->next;
		free(temp);
		fprintf(stderr,"freed/%d",r->rec_sw->head==NULL);
	}
	//fprintf(stderr,"\nrecv head seqno:%d, lfr:%d",ntohl(r->rec_sw->head->packet->seqno),r->rec_sw->lfr);
}

void update_rec_sw(rel_t* r) {
	struct packetnode* runner = r->rec_sw->head;
	int counter = r->rec_sw->lfr;
	while (runner->next!=NULL && ntohl(runner->packet->seqno)==(counter+1)) {
		runner = runner->next;
		counter++;
	}
	//fprintf(stderr,"seqno:%d, counter:%d",ntohl(runner->packet->seqno),counter);
	r->seqNumToAck = counter+2;
	r->rec_sw->lfr = (r->seqNumToAck)-1;
	r->rec_sw->laf = r->rec_sw->lfr + r->rec_sw->rws;
	rec_deletenodes(r);
}

void retransmitSpecificPacket(rel_t * s, int seqnum)
{
	struct packetnode * current = s->send_sw->head;
	if(current == NULL){
		//fprintf(stderr, "you're asking to transmit a packet that doesn't exist");
		return;
	}
	while(ntohl(current->packet->seqno) != seqnum){
		if(current->next == NULL){
			//fprintf(stderr, "you're asking to transmit a packet that doesn't exist");
		}
		current = current->next;
	}
	fprintf(stderr,"retransmit");
	counter=0;
	conn_sendpkt(s->c, current->packet, sizeof(packet_t));
}

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

	if (count >= s->send_sw->sws) {
	//fprintf(stderr, "outsideofwindow: %s", pack->data);
		windowfull=pack;
		return 0;
	}
	current = malloc(sizeof(struct packetnode));
	current->next = NULL;
	current->packet = pack;
	current->length = count+1;
	if (prev == NULL) {
		s->send_sw->head = current;
	}
	else {
		prev->next = current;
	}
	//fprintf(stderr, "packnadd: %s", pack->data);
	counter=0;
	conn_sendpkt(s->c, pack, sizeof(packet_t));
	s->send_sw->lfs = ntohl(pack->seqno);
	return 1;
}

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
	if (prev!=NULL && (ntohl(prev->packet->seqno) == ntohl(pack->seqno))) {
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
}

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
rel_sendack(rel_t *r, int iseof) {
	//fprintf(stderr,"sendack");
	packet_t* ackpack = malloc(sizeof(packet_t));
	ackpack->cksum = 0;
	//r->acknum++; //Not sure if this is necessary
	//fprintf(stderr,"rec_read:%d",ntohl(r->rec_sw->head->packet->seqno));
	ackpack->ackno = htonl((r->seqNumToAck));
	if (iseof==1 && r->rec_sw->head==NULL) {
		//fprintf(stderr,"rec_readddddddddddddddddd:%d",r->seqNumToAck);
		ackpack->ackno = htonl((r->seqNumToAck)+1);
	}
	ackpack->len = htons(8); //not sure if this is correct
	ackpack->cksum = cksum(ackpack, ntohs(ackpack->len));
	conn_sendpkt(r->c, ackpack, sizeof(packet_t));
}
void
rel_sendeof(rel_t *r) {
	//fprintf(stderr,"UPDATE THIS");
	eofseqno = r->seqnum;
	packet_t* eofpack = malloc(sizeof(packet_t));
	eofpack->cksum = 0;
	eofpack->ackno = htonl(r->acknum);
	eofpack->len = htons(12); //not sure if this is correct
	eofpack->seqno = htonl(r->seqnum);
	eofpack->cksum = cksum(eofpack, ntohs(eofpack->len));
	counter=0;
	conn_sendpkt(r->c, eofpack, sizeof(packet_t));
	r->seqnum++;
}

void send_deletenodes(rel_t* r) {
	//fprintf(stderr,"deletenodes called");
	//fprintf(stderr,"\ninitial seqno:%d, lar:%d",ntohl(r->send_sw->head->packet->seqno),r->send_sw->lar);
	while(r->send_sw->head!=NULL && ntohl(r->send_sw->head->packet->seqno) <= r->send_sw->lar) {
		struct packetnode * temp = r->send_sw->head;
		r->send_sw->head=r->send_sw->head->next;
		free(temp);
		fprintf(stderr,"\ndeleted send_sw head");
	}
}

void
rel_recvpkt (rel_t *r, packet_t *pkt, size_t n)
{
	//fprintf(stderr, "\nrecvpkt len: %d\n", ntohs(pkt->len));
	//fprintf(stderr, "my window size is %d", r->rec_sw->rws);
	// Still need check for corrupted packet with checksum
	packet_t* temppack = malloc(ntohs(pkt->len));
	memcpy(temppack, pkt, ntohs(pkt->len));
	temppack->cksum = 0;
	temppack->cksum = cksum(temppack, ntohs(temppack->len));

	// pkt_len = ntohs(pkt->len)
	int pkt_seqno = ntohl(pkt->seqno);

	// Check for corrupted data
	//fprintf(stderr, "cksum=%d, checksum=%d", cksum(pkt, pkt_len), n);
	if (temppack->cksum != pkt->cksum) {
		//drop pack
		fprintf(stderr, "dropped");
		return;
	}


	// Recieving an ACK packet
	else if (ntohs(pkt->len) == sizeof(struct ack_packet)) {
		//fprintf(stderr,"ackkkkkkkkkkkkkk");
		r->send_sw->lar=ntohl(pkt->ackno)-1;
		//fprintf(stderr,"eofseqno:%d, comp:%d",eofseqno,(ntohl(pkt->ackno)-1));
		if (eofseqno == (ntohl(pkt->ackno)-1)) {
			//fprintf(stderr,"destryyyyyyyy");
			eofacked=1;
			//rel_destroy(r);
			return;
		}
		if (eofseqno==0) {
			//fprintf(stderr,"senddeletenodes");
			send_deletenodes(r);
			rel_read(r);
		}
	}
	else{

		if (ntohs(pkt->len) == 12) {
			fprintf(stderr,"goteof");
			rel_sendack(r,1);
			//conn_output(r->c, (void *)(r->receiverbuffer), 0);
			//rel_destroy(r);
			return;
		}
		else if (ntohs(pkt->len) > 12) {
				fprintf(stderr, "seqno:%d, laf:%d", pkt_seqno, r->rec_sw->laf);
			if (pkt_seqno <= r->rec_sw->lfr)
			{
				//frame is outside rec window size-rws and it is
				//discarded
				//may need to retransmitt
				//fprintf(stderr,"outside of rec window lfr=%d, laf=%d", r->rec_sw->lfr, r->rec_sw->laf);
			} else if (pkt_seqno > (r->rec_sw->laf+1)){
				//if less than window size don't retrasnmit
				//if greater --> go ahead and retransmit

				//I commented the next line out, I think if the pkt->seqno > largest acceptable frame then the frame
				//should be discarded right? - Sajal
				//retransmitSpecificPacket(r, pkt_seqno);
				fprintf(stderr, "This frame is out of range and is discarded");
			} else if (r->rec_sw->lfr < pkt_seqno && pkt_seqno <= (r->rec_sw->laf+1))
			{
				if (conn_bufspace(r->c)<500*sizeof(char)) { //not enough room in output buffer, don't ack
					return;
				}
				//frame is accepted
				rec_PackNAdd(pkt, r);
				//int dataindex = 0;
				//fprintf(stderr,"\nreceiving::::::::: %d \n",(ntohs(pkt->len)));
				//while (dataindex<((ntohs(pkt->len)-12)/sizeof(char))) {
				//	*(r->receiverbuffer) = pkt->data[dataindex];
				//	rel_output(r);
				//	dataindex++;
				//}

				//int dataindex = 0;
				//fprintf(stderr,"\nreceiving::::::::: %d \n",(ntohs(pkt->len)));
				//fprintf(stderr, "\nwindow size:: %d \n", r->rec_sw->rws);
				//if (pkt_seqno <= r->seqNumToAck)
				//{
				// all frames, even if higher number of packets have been received will be received and we send an ack
				//not sure if seqNumToAck needs to be incremented
				rel_sendack(r,0);
				//}
			}

		}

	}


}

void
rel_read (rel_t *s)
{
	if (quit == 0) {
		signal(SIGINT, sigintHandler);
	}
	fprintf(stderr,"\nrelreaddddddddddddddddddd\n");
	if (windowfull!=NULL) {
	fprintf(stderr,"\nwfnullllllll\n");
		if (iter_PackNAdd(windowfull, s) == 0) {
			//fprintf(stderr, "windowfull resend: head of send_sw: %s", s->send_sw->head->packet->data);
			return;
		}
		else {
			windowfull=NULL;
			s->seqnum++;
		}
	}
	int r = conn_input(s->c, (void *)(s->senderbuffer), sizeof(char));
	if (r == -1 && eofsent==0) {
		fprintf(stderr, "eofffffffffffffffffffffff");
		rel_sendeof(s);
		eofsent=1;
		//rel_destroy(s);
		return;
	}
	else if (r==0) {
		fprintf(stderr, "r==0");
		return;
	}
	int dataindex = 0;
	packet_t* pack = malloc(sizeof(packet_t));
	pack->cksum = 0;
	pack->ackno = htonl(1);
	pack->seqno = htonl(s->seqnum);
	//strcpy(pack->data, senderbuffer);

	while (r > 0) {
		pack->data[dataindex] = *(s->senderbuffer);
		//fprintf(stderr,"\nintermediate: %c",*(s->senderbuffer));
		r = conn_input(s->c, (void *)(s->senderbuffer), 1);
		dataindex++;
		if (dataindex == 500) {
			pack->len = htons(12 + sizeof(char) * strlen(pack->data));
			pack->cksum = cksum(pack, ntohs(pack->len));
			s->acked = 0;
			//fprintf(stderr, "\nsendpkt: %s\n", pack->data);
			if (iter_PackNAdd(pack, s) == 0) {
				//fprintf(stderr, "\nfull, head of send_sw: %s", s->send_sw->head->packet->data);
				return;
			}
			////conn_sendpkt(s->c, pack, sizeof(packet_t));
			s->seqnum++;
			//s->senderbuffer[0]="\0";
			//free(pack);
			pack = malloc(sizeof(packet_t));
			pack->cksum = 0;
			pack->ackno = htonl(1);
			pack->seqno = htonl(s->seqnum);
			dataindex = 0;
			//s->packet = pack;
		}
	}
	//fprintf(stderr, "\nsendpkt: %s\n", pack->data);
	//pack->data[dataindex]='\0';
	pack->len = htons(12 + sizeof(char) * strlen(pack->data));
	pack->cksum = cksum(pack, ntohs(pack->len));
	s->acked = 0;
	//conn_sendpkt(s->c, pack, sizeof(packet_t));
	int notfull = iter_PackNAdd(pack, s);
	//fprintf(stderr, "head of send_sw: %s", s->send_sw->head->packet->data);
	//fprintf(stderr,"notfull %d",notfull);
	if (notfull == 0) {
		//fprintf(stderr, "\nfullfrag, head of send_sw: %s", s->send_sw->head->packet->data);
		return;
	}
	//free(pack);
	s->seqnum++;

	if (r == -1 && eofsent==0) {
		fprintf(stderr, "eofffffffffffffffffffffffffffffffff2");
		rel_sendeof(s);
		eofsent=1;
		//rel_destroy(s);
		return;
	}
	//fprintf(stderr, "\n");
}

void
rel_output (rel_t *r)
{
	rec_deletenodes(r);

	//Get first value from buffer, if exists

	// What are we putting in the reciever buffer? Should we packets instead of chars?
	/*while(r->receiverbuffer[0] != NULL){

		//int packet_len = ntohs(r->receiverbuffer[0]->len) - 12; //
		int avail_Buffer_Space = conn_bufspace(r->c);

		if (packet_len > avail_Buffer_Space){

			fprintf(stderr, "The Reciever Buffer is full");
			return;

		}*/

		//conn_output(r->c, r->receiverbuffer[0], packet_len);

		//r->rec_sw->lfr = SEQNUM of packet for receiverbuffer[0];

		//Make sure that everything within this buffer is in the right spot now
	

	//fprintf(stderr, "reloutputtt\n");
	//if (*(r->receiverbuffer)!=0) {
	//fprintf(stderr, "%c",*(r->receiverbuffer));
		//fprintf(stderr,"\n%d",conn_bufspace(r->c));
		//conn_output(r->c, (void *)(r->receiverbuffer), sizeof(char));
	//}

}

void
rel_timer ()
{
	if (eofacked==1) {
		return;
	}
	/* Retransmit any packets that need to be retransmitted */
	//fprintf(stderr, "hello%s\n", rel_list->packet->data);
	if(counter % 15 == 14)
	{
		if(rel_list->send_sw->head == NULL){
			//head is null at the beginning; no packets sent
			return;
		}		
		//this is what we are callling one rtt check packets
		if(ntohl(rel_list->send_sw->head->packet->seqno) != rel_list->send_sw->lar + 0){
		//if last ack received isn't updated to what send's list holds then possibility of packet having been lost, so resend
			retransmitSpecificPacket(rel_list, ntohl(rel_list->send_sw->head->packet->seqno));

		}
	}
	counter++;
}
