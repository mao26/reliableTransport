import java.util.*;

/**
 * This is a skeleton program. You'll
 * need to modify it, either by adding code in main
 * or in methods called from main.
 * @author YOUR NAME HERE
 *
 */

public class HangmanStats {
	public void numberOfWords(HangmanFileLoader loader){
		
		HashSet<String> set = new HashSet<String>();
		for(int k=0; k < 1000; k += 1) {
			set.add(loader.getRandomWord(4)); //this example has one size that it is creating the stats for.. length4
		}
		System.out.printf("number of 4 letter words = %d\n", set.size());
		//set.size is printing the number of four letter words that were created out of 1000 random words
		//this example only puts the words with length of four into a set and then returns the size of that set, 
		//which is the number of 4 length words actually made
	}
	
	public void statisticalQuestion(HangmanFileLoader loader){
		Map<ArrayList<String>, Integer> difLengthWords = new HashMap<ArrayList<String>, Integer>();
		Random random = new Random();
		ArrayList<String> wordsOfLength4 = new ArrayList<String>();
		ArrayList<String> wordsOfLength5 = new ArrayList<String>();
		ArrayList<String> wordsOfLength6 = new ArrayList<String>();
		ArrayList<String> wordsOfLength7 = new ArrayList<String>();
		ArrayList<String> restOfWords = new ArrayList<String>();
		for(int k = 0; k < 1000; k +=1){
			int randomInt = random.nextInt(17)+4;
			if(randomInt == 4){
				wordsOfLength4.add(loader.getRandomWord(randomInt));
			}if(randomInt == 5){
				wordsOfLength4.add(loader.getRandomWord(randomInt));
			}if(randomInt == 6){
				wordsOfLength4.add(loader.getRandomWord(randomInt));
			}if(randomInt == 7){
				wordsOfLength4.add(loader.getRandomWord(randomInt));
			} else{
				restOfWords.add(loader.getRandomWord(randomInt));
			}
			//need to add each string of each length to a arrayList, then map the arrayList of one length to one number in the map
		}
		//difLengthWords.add(wordsOfLength4, );

		//add code here to solve your own statistical question
		
		/*Map<String, Integer> difLengthWords = new TreeMap<String, Integer>();
		for(int i = 0; i < 1000; i++){ //will increase this to make more trials
//want to create a way to put each set of length word into it's own map... thinking a hashset, where collisions are linked listed
			
			Random random = new Random(); 
			int randomInt = random.nextInt(17)+4; 
			//if(randomInt)
			//creating a random int I will use later to put all words of this int (which
			// is the length of the random word being created) in a set
			//put this into a string array that keeps all words of a certain length;
			//then put this string array into a treemap
			//int word_count = 0; in here in case want to figure out how many characters in this list
			if(difLengthWords.containsKey(loader.getRandomWord(randomInt))){ 
				//if map already contains word then get the number, increment that number, and add word + newcount to map
				int value = difLengthWords.get(loader.getRandomWord(randomInt));
				value++;
				difLengthWords.put(loader.getRandomWord(randomInt), value);
				//word_count++;
			}//Do I even need this guy? Cause then there's 2 items to one value, and .size only returns the size of the map w/o adding all of the counts
			
			difLengthWords.put(loader.getRandomWord(randomInt), 1);
			
			//word_count++;
			
			//difLengthWords.put(loader.getRandomWord(randomInt), loader.getRandomWord(randomInt).length());
			
			//this line puts the randomWord generated by the randomInt we have kept track off for this first iteration
			//through the loop, it then associates the key with this same exact length of 
			//the random word we have created from the randomInt we have kept track off for this iteration.
			
		}*/
		
	}
	public static void main(String[] args) {
		HangmanStats stats = new HangmanStats();
		HangmanFileLoader loader = new HangmanFileLoader();
		loader.readFile("lowerwords.txt");
		
		stats.numberOfWords(loader);
			
	}
}
