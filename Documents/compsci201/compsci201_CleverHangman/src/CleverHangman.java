import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CleverHangman extends HangmanGame{ //we may want to use some of the methods and variables in regular hangman when creating this version of the game
	private int misses;
	private ArrayList<String> wordList;
	private final char TEMPLATE_BLANK = '-'; // something thought to me by my tutor for implementing a character that we will continuously use in our program
	
	public CleverHangman(int wordlength, int misses) {
		super(wordlength, misses); //this allows us to use the variables from our extended hangman class
		wordList = HangmanFileLoader.getAllWords(wordlength);
		this.misses = misses; // refers to the instance being passed as a parameter into CleverHangman. Also, happens to be the variable taken from Hangman via extending the class
	}
	
	// the meat of our class that takes the the words, creates a map that connects a specific template to each word that fits that template, and then chooses which template to use as our secret word by choosing the template with the most available words
	private String smartWordChooser(char chGuess){// makes the map that has all of our templates
		Map<String, ArrayList<String>> templates = new TreeMap<String, ArrayList<String>>();
		for(String word : wordList){
			String template = makeTemplate(word, chGuess);
			ArrayList<String> list = templates.get(template);
			if(list == null){
				list = new ArrayList<String>();
				templates.put(template, list);
			}
			list.add(word);
		}
		int max = -1;
		String maxTemp = null;
		for(String temp : templates.keySet()){
			ArrayList<String> shortList = templates.get(temp);
			if(max < shortList.size()){
				max = shortList.size();
				maxTemp = temp;
			}
		}
		wordList = templates.get(maxTemp);
		return maxTemp;
	}

	private String makeTemplate(String word, char chGuess) {  // this creates the template from the secret word using a string builder (awesome new class that allows me to set a char to a specific index in a simpler way using setCharAt). 
		StringBuilder template = new StringBuilder(word);
		for(int i = 0; i < template.length(); i++){
			if(template.charAt(i) != chGuess){
				template.setCharAt(i, TEMPLATE_BLANK); //professional use of defining a constant that represents the special char
			}
		}
		return template.toString();
		
	}

	@Override
	public boolean makeGuess(char ch) {//this is taking that old method, and if we don't change this it's going to run
		// it the same way as the old guy, but if we change this then we get to implement the other method
		// but that's only if you keep the return super.makeGuess(ch)
		//return super.makeGuess(ch);
		int count = 0; // is used to keep track of if you guessed the ch right or not (positive for right)
    	if (myLettersGuessed[ch-'a']){
    		return false;
    	}
    	myLettersGuessed[ch-'a'] = true;
    	
    	String template = this.smartWordChooser(ch);
    	for(int i = 0; i < template.length(); i++){
    		if(template.charAt(i) != TEMPLATE_BLANK){
    			count++;
    			myDisplay.setCharAt(i, ch);
    		}
    	}
    	if (count == 0){
    		myMissCount++;
    	}
    	return count == 0;
	}// this converges the template that we picked as our secret word to the template that we are choosing to display to anyone that plays our snarky hangman game
	
	
}
	
	
	
	
	
	
	
	
	
	
//	String convertToPattern(char[] template, String word, char guess){
//		//String secretWord = word.split();
//		String blank = "";
//		String s = "oboe";
//		//Map<String, ArrayList<String>> wordList = new HashMap<String, ArrayList<String>>();
//		//call the random words class from hangman game or hangman fileloader
//		for( int i = 0; i < template.length; i++){
//			if(guess == word.charAt(i)){
//				template[i] = guess; 
//			}
//			s += template[i];
//			//System.out.println(template.toString());
//		}
//		for (char c : template){
//			System.out.println(c);
//		}
//		return s;
//		
//	}
//	public static void main(String[] args){
//		CleverHangman p = new CleverHangman();
//		char[] template = {'_','_','_','_'};
//		p.convertToPattern(template, "oboe", 'o');
//		
//	}
//}
