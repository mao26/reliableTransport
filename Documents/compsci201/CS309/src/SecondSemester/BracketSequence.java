package SecondSemester;

import java.util.Arrays;
import java.util.Scanner;

public class BracketSequence {

	private char[] left = { '(', '<', '{', '[' };
	private char[] right = { ')', '>', '}', ']' };
	private boolean reinitLoop = true;
	private int overUnder = 0;

	public static void main(String[] args) {
		BracketSequence bs = new BracketSequence();
		bs.go();
	}

	private void go() {
		Scanner scan = new Scanner(System.in);
		if (scan.hasNextLine()) {
			String input = scan.nextLine();
			char[] chars = input.toCharArray();
			//int numOfChanges = sequence(chars, 'N');
			takeOutIndvPairs(input);
		}
		scan.close();
	}

	private int sequence(char[] input, char curr) {
		// this is our first iteration
		if (curr == 'N')
			return 0 + sequence(Arrays.copyOfRange(input, 1, input.length), input[0]);
		// we have gone through our whole array
		if (input.length == 0)
			return 0;
		// this will be our base case where we have closed
		// all previous brackets and need to open up a new bracket
		if (curr == 'E')
			return 0;
		return 0;
	}
	
	//what if instead I went through the string 
	
	private void takeOutIndvPairs(String input){
		while(input.length() != 0){
			char currBracket = 'P';
			char currMatchingBracket = 'O';
			int i = 0;
			for(int j = input.length(); i < input.length(); j--){
				if(reinitLoop) {
					//don't want to reinitialize our currBracket and it's matching pair every time the loop goes around
					//instead if we are still on the process of finding currBrackets pair, then no need to reinit to get 
					//the exact same variables
					reinitLoop = false;
					currBracket = input.charAt(i);
					currMatchingBracket = findMatch(currBracket);
				}
				if(currMatchingBracket == input.charAt(j)){
					//remove both of these cars and break out of our current for loop
					//increment i to get our next bracket to process
					reinitLoop = true;
					input = input.substring(0, i) + input.substring(i, j); // gotta check this --> idx may be off
				} 
				//else just let the j letter get decremented
				if(j == 0){
					// if our j gets decremented all the way through then that means we cannot find a pair for this bracket
					//first we need to check if there is another bracket that faces the same way that can be changed in order
					//to remove this bracket
					//if this isn't true, then we know that this string is not in Standard notation
					
					//we can check this by maybe reinitializing with the next string letter in the loop and removing all the
					//pairs until there are no more left, except for the ones that don't match
					// at this point we can check to see if there are enough left facing and right facing so that each
					//left facing has a pair and that then there are no more brackets left over
					//net sum of zero 
					
					int numberOfChanges = 0; // this has to be zero after the for loop --> deems that all brackets can be closed
					int max = 0; //the max, or number of positive changes will let me know how many brackets I need to change
					for(int k = 0; k < input.length(); k++){
						if(leftContains(input.charAt(k))){
							numberOfChanges += 1;
							max += 1;
						} else {
							numberOfChanges -= 1;
						}
					}
					if(numberOfChanges == 0){
						System.out.println(max);
					} else{
						System.out.println("impossible");
					}
				}
			}
		}
	}

	private boolean leftContains(char curr){
		for(char each: left){
			if(curr == each) return true;
		}
		return false;
	}
	
	private char findMatch(char currBracket) {
		//this finds the matching pair of our current bracket by the index of our two initialized arrays
		//matching pairs are seen by being in the same index in the opposite array (left facing vs right facing brackets)
		int i = 0;
		for(char each: left){
			if(each == currBracket) return right[i]; 
			i++;
		}
		int j = 0;
		for(char each: right){
			if(each == currBracket) return left[j];
			j++;
		}
		return 'F';
	}

}
