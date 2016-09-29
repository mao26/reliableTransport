package StacksNQueues;

public class QueueOfStrings {

	private class Node{
		String s;
		Node next;
	}
	
	private Node first,last = null;
	
	private void enqueue(String item){
		Node oldlast = last;
		Node last = new Node();
		last.s = item;
		last.next = null;
		if(isEmpty()) first = last;
		else oldlast.next = last;
	}
	
	private String dequeue(){
		String item = first.s;
		first = first.next;
		if(isEmpty()) last = null;
		return item;
	}
	
	private boolean isEmpty(){
		return first == null;
	}
	
}


