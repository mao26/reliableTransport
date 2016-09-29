package StacksNQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StackOfStrings {

	private class Node{
		String item;
		Node next;
	}
	
	private Node first = null;
	
	public static void main(String[] args){
		StackOfStrings stack = new StackOfStrings();
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			if(s.equals("-")){
				StdOut.print(stack.pop());
			} else {
				stack.push(s);
			}
		}
	}
	
	public boolean isEmpty(){
		return first == null;
	}

	private void push(String s) {
		Node oldfirst = first;
		first = new Node();
		first.item = s;
		first.next = oldfirst;
	}

	private String pop() {
		String item = first.item;
		first = first.next;
		return item + " ";
	}
	
}
