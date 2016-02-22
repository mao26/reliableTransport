
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

  /*init our sliding windows*/
  r->rec_sw = xmalloc(sizeof(struct send_slidingWindow));
  r->send_sw = xmalloc(sizeof(struct rec_slidingWindow));
  r->rec_sw->rws = cc->window; //window size
  r->rec_sw->laf = r->seqnum + r->rec_sw->rws; //last acceptable frame
  //will be our window size plus last seqnum accepted
  r->rec_sw->lfr = 0; //no frames recieved
  r->send_sw->sws = cc->window; //window size
  r->send_sw->lar = 0; //no acks received
  r->send_sw->lfs = 0; //no frames sent so far
  r->send_sw->lfs_min_lar = r->send_sw->lfs - r->send_sw->lar;
  r->rec_sw->laf_min_lfr = r->rec_sw->laf - r->rec_sw->lfr;
  r->rec_sw->head = malloc(sizeof(struct packetnode));
  r->send_sw->head = malloc(sizeof(struct packetnode));
  r->send_sw->head->length = 0;
  r->rec_sw->head->length = 0;
  return r;
}

void
rel_destroy (rel_t *r)
{
  if (r->next)
    r->next->prev = r->prev;
  *r->prev = r->next;
  conn_destroy (r->c);

  //fprintf(stderr,"rel_destroy\n");
  /* Free any other allocated memory here */
  free(r->senderbuffer);
  free(r->receiverbuffer);
  free(r->packet);
}

/* Signal Handler for SIGINT */
void sigintHandler(int sig_num)
{
    //signal(SIGINT, sigintHandler);
	if (quit==1) {
		exit(0);
	}
    fprintf(stderr, "\n Cannot be terminated using Ctrl+C \n");
    quit=1;
    //fflush(stdout);
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
rel_sendack(rel_t *r) {
	packet_t* ackpack = malloc(sizeof(packet_t));
	ackpack->cksum = 0;
	r->acknum++;
	ackpack->ackno = htonl(r->acknum);
	ackpack->len = htons(8); //not sure if this is correct
	ackpack->cksum = cksum(ackpack, ntohs(ackpack->len));
	conn_sendpkt(r->c, ackpack, sizeof(packet_t));
}
void
rel_sendeof(rel_t *r) {
		packet_t* eofpack = malloc(sizeof(packet_t));
		eofpack->cksum = 0;
		eofpack->ackno = htonl(r->acknum);
		eofpack->len = htons(12); //not sure if this is correct
		eofpack->seqno = htonl(r->seqnum);
		eofpack->cksum = cksum(eofpack, ntohs(eofpack->len));
		conn_sendpkt(r->c, eofpack, sizeof(packet_t));
		r->seqnum++;
}

void
rel_recvpkt (rel_t *r, packet_t *pkt, size_t n)
{
	//fprintf(stderr, "\nrecvpkt: %s\n", pkt->data);
	//fprintf(stderr, "my window size is %d", r->rec_sw->rws);
	
	if (ntohs(pkt->len)==8) {
		//fprintf(stderr,"ackkkkkkkkkkkkkk");
		r->acked=1;
	}
	else if (ntohs(pkt->len)==12) {
		rel_sendack(r);
		conn_output(r->c,(void *)(r->receiverbuffer),0);
		//rel_destroy(r);
		return;
	}
	else if (ntohs(pkt->len)>12) {
	if(r->seqnum <= r->rec_sw->lfr || r->seqnum > r->rec_sw->laf)
	{
		//frame is outside rec window size-rws and it is 
		//discarded 
		//may need to retransmitt
	} else if(r->rec_sw->lfr < r->seqnum && r->seqnum <= r->rec_sw->laf)
	{
		//frame is accepted
		rel_output(r);
		int dataindex = 0;
		//fprintf(stderr,"\nreceiving::::::::: %d \n",(ntohs(pkt->len)));
		fprintf(stderr,"\nwindow size:: %d \n",r->rec_sw->rws);
		if(r->seqnum <= r->seqNumToAck)
		{
			// all frames, even if higher number of packets have been received will be received and we send an ack
			r->rec_sw->lfr = r->seqNumToAck;
			r->rec_sw->laf = r->rec_sw->lfr + r->rec_sw->rws;
			//not sure if seqNumToAck needs to be incremented
		rel_sendack(r);
}
	}
		
		
		
	}
}


void
rel_read (rel_t *s)
{
	if (quit==0) {
		signal(SIGINT, sigintHandler);
	}

	int r = conn_input(s->c, (void *)(s->senderbuffer), sizeof(char));
	if (r==-1) {
		fprintf(stderr,"eofffffffffffffffffffffff");
		rel_sendeof(s);
		//rel_destroy(s);
		return;
	}
	int dataindex = 0;
	packet_t* pack = malloc(sizeof(packet_t));
	pack->cksum = 0;
	pack->ackno = htonl(1);
	pack->seqno = htonl(s->seqnum);
	//strcpy(pack->data, senderbuffer);

	while (r>0) {
		pack->data[dataindex]=*(s->senderbuffer);
		//fprintf(stderr,"\nintermediate: %c",*(s->senderbuffer));
		r = conn_input(s->c, (void *)(s->senderbuffer), 1);
		dataindex++;
		if (dataindex==500) {
			pack->len = htons(12+sizeof(char)*strlen(pack->data));
			pack->cksum = cksum(pack, ntohs(pack->len));
			s->acked=0;
			//fprintf(stderr, "\nsendpkt: %s\n", pack->data);
			conn_sendpkt(s->c, pack, sizeof(packet_t));
			s->seqnum++;
			//s->senderbuffer[0]="\0";
			//free(pack);
			pack = malloc(sizeof(packet_t));
			pack->cksum = 0;
			pack->ackno = htonl(1);
			pack->seqno = htonl(s->seqnum);
			dataindex=0;
			//s->packet = pack;
		}
	}
	if (r==-1) {
		fprintf(stderr,"eofffffffffffffffffffffffffffffffff");
		rel_sendeof(s);
		//rel_destroy(s);
		return;
	}
	//fprintf(stderr, "\nsendpkt: %s\n", pack->data);
	//pack->data[dataindex]='\0';
	pack->len = htons(12+sizeof(char)*strlen(pack->data));
	pack->cksum = cksum(pack, ntohs(pack->len));
	s->acked=0;
	iterPackNAdd(pack, s);
	conn_sendpkt(s->c, pack, sizeof(packet_t));
	//free(pack);
	s->seqnum++;
	
	
	//fprintf(stderr, "\n");
}

void iterPackNAdd(packet_t * pack, rel_t * s)
{
	if(s->send_sw->head->packet == NULL)
	{
		
		return;
	} 
	while(s->send_sw->head -> next -> packet != NULL)
	{
		s->send_sw->head = s->send_sw->head -> next; 
	}
	s->send_sw->head->next->packet = pack;
}

void
rel_output (rel_t *r)
{
	//fprintf(stderr, "reloutputtt\n");

}

void
rel_timer ()
{
  /* Retransmit any packets that need to be retransmitted */
	fprintf(stderr, "hello%s\n", rel_list->packet->data);
	
}
