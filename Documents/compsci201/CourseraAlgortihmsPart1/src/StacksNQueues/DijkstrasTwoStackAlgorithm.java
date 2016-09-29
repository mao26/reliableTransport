package StacksNQueues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
/*
 * 
 * push values onto the value stack
 * push operators onto the operator stack
 * ignore left parenthesis
 * when see a right parenthesis, pop operator and pop two values; push the result of applying that operator
 * to the two values and push that value back onto the stack
 * continue this process until you get your result
 * This seems similar to how the Lisp language reads mathematical inputs
 * 
 */
public class DijkstrasTwoStackAlgorithm {
	public static void main(String[] args){
		Stack<String> ops = new Stack<String>();
		Stack<Double> vals = new Stack<Double>();
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			if(s.equals("(")) continue;
			else if(s.equals("+")) ops.push(s);
			else if(s.equals("*")) ops.push(s);
			else if(s.equals(")")) {
				String op = ops.pop();
				if(op.equals("+")) vals.push(vals.pop() + vals.pop());
				else if(op.equals("*")) vals.push(vals.pop() * vals.pop());
			}
			else vals.push(Double.parseDouble(s));
		}
		StdOut.println(vals.pop());
	}
}
