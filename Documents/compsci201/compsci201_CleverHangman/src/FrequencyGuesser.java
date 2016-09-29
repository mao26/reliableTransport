import java.util.ArrayList;


public class FrequencyGuesser implements IHangGuesser{
	private HangmanGame myGame;
	private int myWordLength;
	private ArrayList<String> myWordsForLength;
	private ArrayList<Character> myGuesses;
	
	public FrequencyGuesser(int wordLength){
		this.myWordLength = wordLength;
	}
	
	@Override
	public void gameSetup(HangmanGame game) {
		this.myGame = game;
		myWordsForLength = new ArrayList<String>();
		ArrayList<String> words = HangmanFileLoader.getAllWords(myWordLength);
		for (String word : words){
			myWordsForLength.add(word);
		}
		myGuesses = new ArrayList<Character>();
	}

	@Override  // creating a arrayList that holds how many times a certain letter is guessed in all of the words
	public boolean nextGuess() {
		int[] charCount = new int [26];
		for(String word : myWordsForLength){
			for(int i = 0; i < word.length(); i++){
				Character c = word.charAt(i);
				int index = c-'a';
				if(!myGuesses.contains(c)){
					charCount[index]++;
				}
			}
		}
		int guessIndex = -1;
		int max = -1;
		for(int i = 0; i < charCount.length; i++){ // run through the arrayList and guess the char with the highest
			//frequency in all of the words. This is in a for loop that guesses all of the letters with this same
			//pattern of seeing which letter is the next one with the highest frequency
			if(charCount[i] > max){
				guessIndex = i;
				max = charCount[i];
			}
		}
		char ch = (char) ('a' + guessIndex);
		myGuesses.add(ch);
		boolean miss = myGame.makeGuess(ch);
		if(miss){
			for(int i = myWordsForLength.size()-1; i >= 0; i--){
				String word = this.myWordsForLength.get(i);
				if(word.indexOf(ch, 0) != -1){
					myWordsForLength.remove(i);
				}
			}
		}
		else {
			for (int i = myWordsForLength.size() - 1; i >= 0; i--) {
				String word = this.myWordsForLength.get(i);
				if (word.indexOf(ch, 0) == -1) {
					myWordsForLength.remove(i);
				}
			}
		}
		return myGame.gameOverHung() || myGame.gameOverGuessed();
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

}
