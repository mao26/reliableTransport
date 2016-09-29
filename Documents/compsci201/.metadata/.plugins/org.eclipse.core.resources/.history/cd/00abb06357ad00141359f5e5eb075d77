import java.util.*;

/**
 * Human plays hangman while conforming to standard hangman interface,
 * allowing for changing guessing strategy from auto to human in
 * the context of analyzing tradeoffs in hangman
 * @author ola
 *
 */
public class HumanGuesser implements IHangGuesser {
	
	private static Scanner myInput = new Scanner(System.in);
	private HangmanGame myGame;
	private TreeSet<Character> myCharsGuessed;
	
	public HumanGuesser() {
		myCharsGuessed = new TreeSet<Character>();
	}
	
	/**
	 * Get a line from the user and return the line as a String
	 * @param prompt is printed as an instruction to the user
	 * @return entire line entered by the user
	 */
	protected static String readString(String prompt) {
		System.out.printf("%s ",prompt);
		String entered = myInput.nextLine();
		return entered;
	}
	
	@Override
	public void gameSetup(HangmanGame game) {
		myGame = game;
	}

	@Override
	public boolean nextGuess() {	
		System.out.printf("letters guessed: ");
		for(char ch : myCharsGuessed){
			System.out.printf("%c ",ch);
		}
		System.out.println();
		
		System.out.printf("guess this word: %s\n",myGame.getDisplay());
		String guess = readString("your guess: ");
		boolean miss = myGame.makeGuess(guess.charAt(0));
		myCharsGuessed.add(guess.charAt(0));
		return myGame.gameOverGuessed() || myGame.gameOverHung();
	}

	@Override
	public void gameOver() {
		if (! myGame.gameOverGuessed() && ! myGame.gameOverHung()) {
			throw new IllegalStateException("game not over");
		}
	}
}
