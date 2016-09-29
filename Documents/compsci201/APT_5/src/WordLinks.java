import java.util.ArrayList;



public class WordLinks {
	
	public static void main (String[] args){
		String[] words = new String[]{"pits", "fits", "sits", "bits", "hits", "pots", "pins", "pats", "puts", "pets", "pips", "site", "mite", "bite", "fite", "pine", "pate", "mile", "mole"};
		String from = "pith";
		String to = "vole";
		WordLinks wordLinks = new WordLinks();
		wordLinks.isLinked(words, from, to);
		
	}
	
	private class Node{
		int level; //never really used it so I can get rid of it
		String wordVal;
		ArrayList<Node> next = new ArrayList<Node>();
		Node parent;
		
		public Node(String value, int level, Node parent){ // constructor with all of our things
			this.level = level;
			wordVal = value;
			this.parent = parent;
			
		}
	}
	
	
	public String isLinked(String[] inner, String from, String to){
		Node head = new Node(from, 0, null);// first parent Node 
		ArrayList<Node> prevLevel = new ArrayList<Node>(); //work on a previous level, while making the next level (our working level)
		ArrayList<Node> givenLevel = new ArrayList<Node>(); // use both lists to keep track of our prev. parent so that we can keep track of what our list should have that we are able to add to the next link
		int level = 0;
		int total = 0;
		prevLevel.add(head);
		boolean foundWinner = false;
		int[] result = new int[2];
		result[0] = 0;
		result[1] = 0;
		
		for(int j = 0; j <= inner.length + 1; j++){
			for(Node word: prevLevel){ // loop through everything in prev level
				// check to see which word Nodes can link to the word in our parent Node, but want to avoid words in the current track for this word/link
				ArrayList<String> wordsInTrack = new ArrayList<String>();
				Node temp = word.parent; // points to the first word at first, and then the words that it finds that can connect to that first word (getting rid of the first word); its our current word
				
				
				while(temp != null){ //walks up the tree and finds all of the wods in that segment of the tree
					wordsInTrack.add(temp.wordVal); // adds the word curr working with to our list
					temp = temp.parent;
				}// wordsInTrack has all the words we cannot use
				
				
				for(String nextWord: inner){ //checking to see if the words are able to be used
					if(wordsInTrack.contains(nextWord) || onPrevList(nextWord, prevLevel)){ //(if it's on our track or if it's on the working level of our tree then skip) our wordsInTrack contains all of the parents, but not the current word, so by adding the current word to a check here, then we are checking each word that could be a child at a time
						continue; //if it does contain the word then skip that word (already used words)
						
					}
					if(willLink(word.wordVal, nextWord)){ // if it does link then add to the path as a new node
						Node child = new Node(nextWord, level+1,word); //puts the word value in the right place below the tree
						givenLevel.add(child); //also adding him to the givenLevel
						if(willLink(nextWord, to)){ // we have found a winner
							return "ladder";
						}
					}
				}
				
			}
			level++;
			prevLevel = givenLevel; //this way we can always stay working on our prev level, then recreate givenLevel
			givenLevel = new ArrayList<Node>();
			if(foundWinner == true){
				break;
			}
		}
		
		return "none";
			
	}
	private boolean onPrevList(String word, ArrayList<Node> prevList){
		for(Node n : prevList){
			if(n.wordVal.equals(word)){
				return true;
			}
		}
		return false;
	}

	private boolean willLink(String word, String nextWord) { //used to determine if it will link
		int mismatches = 0;
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) != nextWord.charAt(i)){
				mismatches++;
				if(mismatches > 1){
					return false;
				}
			}
		}
		return true;
	}
}
		
	

