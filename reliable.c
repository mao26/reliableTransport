
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

#include "rlib.h"



struct reliable_state {
  rel_t *next;			/* Linked list for traversing all connections */
  rel_t **prev;

  conn_t *c;			/* This is the connection object */

  /* Add your own data fields below this */
  int seqnum;
  char* senderbuffer;
  char* receiverbuffer;
  int acknum;
  int acked;
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
  r->seqnum = 1;
  r->senderbuffer = malloc(sizeof(char));
  r->receiverbuffer = malloc(sizeof(char));
  r->acknum = 1;
  r->acked = 0;
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
		r->acknum++;
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
	//fprintf(stderr, "recvpkt: %s", pkt->data);
	if (ntohs(pkt->len)==8) {
		//fprintf(stderr,"ackkkkkkkkkkkkkk");
		r->acked=1;
	}
	else if (ntohs(pkt->len)==12) {
		rel_sendack(r);
		conn_output(r->c,(void *)(r->receiverbuffer),0);
		rel_destroy(r);
		return;
	}
	else if (ntohs(pkt->len)>12) {
		rel_output(r);
		int dataindex = 0;
		//fprintf(stderr,"\nreceiving::::::::: %d \n",(ntohs(pkt->len)));
		while (dataindex<((ntohs(pkt->len)-12)/sizeof(char))) {
			*(r->receiverbuffer) = pkt->data[dataindex];
			conn_output(r->c, (void *)(r->receiverbuffer), sizeof(char));
			dataindex++;
		}
		//fprintf(stderr,"\nconn_bufspace %d \n", conn_bufspace(r->c));
		rel_sendack(r);
	}
}


void
rel_read (rel_t *s)
{
	int r = conn_input(s->c, (void *)(s->senderbuffer), sizeof(char));
	if (r==-1) {
		fprintf(stderr,"eofffffffffffffffffffffff");
		rel_sendeof(s);
		rel_destroy(s);
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
		fprintf(stderr,"\nintermediate: %c",*(s->senderbuffer));
		r = conn_input(s->c, (void *)(s->senderbuffer), 1);
		dataindex++;
		if (dataindex==500) {
			pack->len = htons(12+sizeof(char)*strlen(pack->data));
			pack->cksum = cksum(pack, ntohs(pack->len));
			s->acked=0;
			conn_sendpkt(s->c, pack, sizeof(packet_t));
			s->seqnum++;
			//s->senderbuffer[0]="\0";
			//free(pack);
			pack = malloc(sizeof(packet_t));
			pack->cksum = 0;
			pack->ackno = htonl(1);
			pack->seqno = htonl(s->seqnum);
			dataindex=0;
		}
	}
	if (r==-1) {
		fprintf(stderr,"eofffffffffffffffffffffffffffffffff");
		rel_sendeof(s);
		rel_destroy(s);
		return;
	}
	//fprintf(stderr, "%s", pack->data);
	//pack->data[dataindex]='\0';
	pack->len = htons(12+sizeof(char)*strlen(pack->data));
	pack->cksum = cksum(pack, ntohs(pack->len));
	s->acked=0;
	conn_sendpkt(s->c, pack, sizeof(packet_t));
	//free(pack);
	s->seqnum++;
	
	
	//fprintf(stderr, "\n");
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

}
