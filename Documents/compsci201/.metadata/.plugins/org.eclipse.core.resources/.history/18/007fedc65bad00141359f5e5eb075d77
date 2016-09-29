import java.util.*;
/**
 * Facilitate stress-testing a Hangman Auto-guesser
 * @author ola
 *
 */


public class GuessExecutor {
	private ArrayList<String> myWords;
	private int myMisses;
	private boolean myDebug;
	private IHangGuesser myGuesser;
	
	/**
	 * Initialize a guesser to stress-test all words, no debugging enabled.
	 * @param length is length of words being stress-tested
	 * @param misses is number of misses until hung in testing
	 * @param guesser is the class-instance being stress-tested
	 */
	public GuessExecutor(int length, int misses, IHangGuesser guesser){
		this(length, misses, guesser, false);
	}
	
	/**
	 * Initialize a guesser to stress-test all words
	 * @param length is length of words being stress-tested
	 * @param misses is number of misses until hung in testing
	 * @param guesser is the class-instance being stress-tested
	 * @param debug specifies debugging status
	 */
	public GuessExecutor(int length,int misses, IHangGuesser guesser, boolean debug){
		myWords = HangmanFileLoader.getAllWords(length);
		myMisses = misses;
		myDebug = debug;
		myGuesser = guesser;
	}
	
	/**
	 * Guess every word stored in myWords using the IHangGuesser
	 * that's specified in method guess. 
	 * @return  true if all words guessed, else false (at least one one word not guessed)
	 */
	public boolean stress(){

		ArrayList<String> notables = new ArrayList<String>();
		int wins = 0;
		for(String w : myWords){
			boolean won = guess(w);
			if (won){
				wins++;
			}
			else {
				notables.add(w);
			}
			if (myDebug) {
				System.out.printf("%s\t%s\n", won,w);
			}
			
		}
		if (myDebug) {
			System.out.printf("%d words %d wins\n", myWords.size(),wins);
			for(String w : notables){
				System.out.println(w);
			}
		}
		System.out.printf("%d words %d wins\n", myWords.size(),wins);
		return wins == myWords.size();
	}
	
	protected boolean guess(String word){

		HangmanGame hg = new HangmanGame(word, myMisses);
		myGuesser.gameSetup(hg);

		while (true) {
			boolean over = myGuesser.nextGuess();
			if (over) {
				break;
			}
		}
		if (hg.gameOverGuessed()) {
			return true;
		} else if (hg.gameOverHung()) {
			return false;
		}
		throw new IllegalStateException("game ended in bad state");
	}
	
	public static void main(String[] args) {
		int guesses = 10;
		int wlength = 7;
		GuessExecutor ng = new GuessExecutor(wlength,guesses,new NaiveGuesser(false));
		ng.stress();
	}
}
