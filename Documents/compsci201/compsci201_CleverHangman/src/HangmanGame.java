
import java.util.*;

/**
 * Hangman Game to play by different players, and to subclass as needed
 * for other, smarter versions of Hangman
 * 
 * @author Owen Astrachan
 * 
 */

public class HangmanGame {
	
	public static char BLANK = '_';
	
    protected String mySecret;
    protected StringBuilder myDisplay;
    protected boolean[] myLettersGuessed;
    protected int myMissCount;
    protected int myHangCount;
   
    /**
     * Construct a game of Hangman designed to be played by some kind of IHangGuesser.
     * @param secret is the secret word being guessed
     * @param misses is the number of missed-guesses until hung
     */

    public HangmanGame(String secret, int misses) {
    	mySecret = secret;
    	myLettersGuessed = new boolean[26];
    	myHangCount = misses;
    	myMissCount = 0;
    	myDisplay = new StringBuilder(secret.length());
    	
    	for(int k=0; k < secret.length(); k++){
    		myDisplay.append(BLANK);
    	}
    }
    
    /**
     * Construct a Hangman game with a random word chosen from list returned via 
     * HangmanFileLoader. The game will be played using some kind of IHangGuesser.
     * @param wordlength is length of secret word that will be guessed, chosen at random
     * @param misses is the number of missed-guesses until hung
     */
    public HangmanGame(int wordlength, int misses){
    	this(HangmanFileLoader.getRandomWord(wordlength),misses);
    }
    
    /**
     * Determines if game over with a loss, player hung
     * @return true if number of missed letters results in hanging, false otherwise
     */
    public boolean gameOverHung() {
    	return myMissCount >= myHangCount;
    }
    
    /**
     * Determines if game over with win, word guessed
     * @return true if word has been guessed, false otherwise
     */
    public boolean gameOverGuessed(){
    	for(int k=0; k < myDisplay.length(); k++){
    		if (myDisplay.charAt(k) == BLANK){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * Returns the secret word being guessed by player if the game is over, 
     * otherwise returns a message and the displayed string
     * @return secret word or message about game not over
     */
    public String getSecret(){
    	if (gameOverHung() || gameOverGuessed()){
    		return mySecret;
    	}
    	return "game is not over, you know "+myDisplay;
    }

    /**
     * Displays letters guessed in correct position and "_" BLANK for 
     * locations in secret word that haven't been guessed
     * @return String to display to player as part of game play, correctly
     * guessed letters revealed.
     */
    public String getDisplay(){
    	return myDisplay.toString();
    }
    
    /**
     * Make a guess, update internal state based on guessed character
     * and return value to assist with game play.
     * @param ch is character being guessed
     * @return true if ch is a miss, false if ch in word or already guessed
     */
    public boolean makeGuess(char ch){
    	int count = 0;
    	if (myLettersGuessed[ch-'a']){
    		return false;
    	}
    	myLettersGuessed[ch-'a'] = true;
    	
    	for(int k=0; k < mySecret.length(); k++){
    		if (mySecret.charAt(k) == ch){
    			count++;
    			myDisplay.setCharAt(k,ch);
    		}
    	}
    	if (count == 0){
    		myMissCount++;
    	}
    	return count == 0;
    }       
    
    /**
     * Return number of misses remaining until hung/game lost. Initially this
     * is the number specified when HangmanGame constructed, but decreases after each miss
     * @return
     */
    public int missesLeft(){
    	return myHangCount - myMissCount;
    }
}
