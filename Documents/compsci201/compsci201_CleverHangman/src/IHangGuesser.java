/*
 * Designed to allow different types of player to engage in playing
 * a game of hangman. The format for playing a game using an IHangGuesser 
 * is:
 * 
 * <ol>
 * <li>call gameSetUp(game) with the game to be played. Calling other methods
 * before calling gameSetup with a game that's ready to play is an error. </li>
 * 
 * <li> repeatedly call <code>nextGuess</code> which will generate a guess and
 * invoke appropriate methods in the game passed to <code>gameSetup</code> with
 * the guess. Note that <code>nextGuess</code> makes one guess only, and returns
 * true iff game has ended.</li>
 * 
 * <li> call <code>gameOver</code> for any tear-down, reporting activity that 
 * might be done </li>
 * </ol>
 * <P>
 * In working code this could be written as below:
 * <PRE>
        HangmanGame game = new HangmanGame("bagel",8);
		IHangGuesser guesser = new HumanGuesser();
		guesser.gameSetup(game);
		while (true){
			
			if (guesser.nextGuess()){
				break;
			}

		}
		if (game.gameOverHung()) {
			System.out.println("you lose!");
		}
		else if (game.gameOverGuessed()) {
			System.out.println("you win!");
		}
		System.out.printf("secret word is %s\n",game.getSecret());
 * </PRE>
 * </P>
 * 
 * @author Owen Astrachan
 */
public interface IHangGuesser {

	/**
	 * Set up a player to be able to play a game. Must be called before other methods
	 * are called.
	 * @param game is the Hangman game this player will engage in playing
	 */
	public void gameSetup(HangmanGame game);
	
	/**
	 * Makes one guess by calling the <code>makeGuess</code> method of
	 * the game passed to <code>gameSetup</code>. It's the responsibility
	 * of the guesser to avoid making the same guess more than once, though
	 * <code>HangmanGame</code> does not count duplicated guesses as misses.
	 * @return true if and only if game over with hang or guess
	 */
	public boolean nextGuess();
	
	/**
	 * May need to do game or reporting tear-down after the game is over. It's an
	 * error to call this method when the game being played isn't yet over
	 */
	
	public void gameOver();
	
}
