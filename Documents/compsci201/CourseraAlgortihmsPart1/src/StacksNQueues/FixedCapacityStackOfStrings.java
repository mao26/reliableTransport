package StacksNQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FixedCapacityStackOfStrings {
	
	private String[] s;
	private int N = 0;
	
	public FixedCapacityStackOfStrings(int capacity){
		s = new String[capacity];
	}
	
	public FixedCapacityStackOfStrings(){ //this constructor implements our resizing automatically using resize
		s = new String[1];
	}

	public static void main(String[] args){
		FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(8);
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
		return N == 0;
	}
	
	public void push(String item){
		//s[N++] = item; //implements our original Fixed Capacity with our caveat where client has to implement size beforehand
		//below is the implementation used when we want our code to implement its resize automatically
		if(N == s.length) resize(2 * s.length);
		s[N++] = item;
	}
	
	private void resize(int capacity){
		String[] copy = new String[capacity];
		for(int i = 0; i < N; i++){
			copy[i] = s[i];
		}
		s = copy;
	}
	
	public String pop(){
		String item = s[--N];
		s[N] = null;
		if(N > 0 && N < s.length/4) resize(s.length/2);
		return item;
	}
	
}
