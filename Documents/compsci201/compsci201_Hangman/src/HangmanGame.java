import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * You write code here to play a game of Hangman.
 * Some sample code provided at the start. You'll probably remove almost 
 * all of it (readString might stick around).
 * 
 * @author YOUR NAME HERE
 */

public class HangmanGame {

	// Used for reading data from the console.
	Scanner myInput;

	public HangmanGame() {
		// Set up our read-from-console.
		myInput = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	}

	/**
	 * Get a line from the user and return the line as a String.
	 * 
	 * @param prompt
	 *            is printed as an instruction to the user
	 * @return entire line entered by the user
	 */
	public String readString(String prompt) {
		System.out.printf("%s ", prompt);
		String entered = myInput.nextLine();
		return entered;
	}

	/**
	 * Play one game of Hangman. This should prompt user for parameters and then
	 * play a complete game. You'll likely want to call other functions from
	 * this one. The existing code may provide some helpful examples.
	 */

	public void play() {
		Random random = new Random();
		HangmanFileLoader data = new HangmanFileLoader();
		data.readFile("lowerwords.txt");

		// creating our guessCount and the Length that the user inputs, and then
		// creates our secretWord accordingly
		String input = readString("How many guesses?");
		int guessCount = Integer.parseInt(input);
		String lengthInput = readString("How long of a word between length 4 and 12 do you want to guess?");
		int Length = Integer.parseInt(lengthInput);
		String secretWord = data.getRandomWord(Length); // pass this the length that the user inputs to create SecretWord
		//String secretWord = "antimony";
		
//		String dash = "";

		System.out.println("Your randomly chosen secret word of length " + Length + " is: " + secretWord);
		// System.out.println("6 letter secret word is " +
		// data.getRandomWord(6));
//		for (int i = 0; i < secretWord.length(); i++) { // making the dashes that represent the secretWord
//			dash = dash + "- ";
//		}
//		System.out.println(dash);

		
		boolean gameWon = false;
		int countOfGuessedLet = 0;
		String charGuessedWrong = ""; //if guess letter wrong put into this array
		char[] charTryingToFind = new char[secretWord.length()]; 
		char[] buildingWord = new char[secretWord.length()];
		for(int i = 0; i < secretWord.length(); i++){
			charTryingToFind[i] = secretWord.charAt(i);
		}
		for(int i = 0; i < secretWord.length(); i++){
			buildingWord[i] = '-';
		}
		System.out.println(buildingWord);
		
/*
 * iterate through my secretWord and put all of the chars into charTryingToFind array. If 
 */
		
		for (int k = 0; k < guessCount; k += 1) {
			String guess = readString("Guess a character or the secret word:");
//			String newDash = "";
			if (guess.length() == 1) {// if a char is being guessed
				if(secretWord.contains(guess)){
					countOfGuessedLet++;
					k--;
					String word = secretWord.substring(0, secretWord.length());
					int temp = 0;
					while(true){
						
						int idxOfCharGuess = word.indexOf(guess);
						if(word.contains(guess) == true){
							word = word.substring(idxOfCharGuess+1, word.length());
							buildingWord[idxOfCharGuess + temp] = secretWord.charAt(idxOfCharGuess + temp);
							temp = temp + idxOfCharGuess+1;
						}
						else {
							break;
						}
					}
					System.out.println(buildingWord);
					if(countOfGuessedLet == charTryingToFind.length){
						gameWon = true;
						break;
					}
				}
				if(!secretWord.contains(guess)){
					System.out.println(buildingWord);
					charGuessedWrong += guess + ", ";
					System.out.println("Incorrect guesses: " + charGuessedWrong);
				//guessCount is automatically decreased by my for loop iterator increasing towards guessCount
//				System.out.println(dash);
				}
			}
				/*
				 * need to add a clause that says if wordBuilder is equal to wordTryingToFind then games over
				 * 
				 * also need to keep track of missed letters and show the player what those missed letters are
				 */
				
		/*
		 * If guess is one char, we initialize a count, then if that char being guessed is in the secretWord
		 * increase our guessCount to not take away a guess, then while my count is not equal to the lenght of
		 * my secretword, make a segment of the word, so that I can use indexOf method (returns only the first
		 * instance of occuring), then create the index value of where the char appears in our substring
		 */
						
						
						
						// if(secretWord.contains(guess)){ //check to see if the
						// word contains the letter being guessed
						// //then compare that char to each letter
						// //int numOfCharsInWord = secretWord;
						// //int idxOfGuess = secretWord.indexOf(guess);
						// } else {
						// guessCount--;
				
			if (guess.length() > 1) {

					// if the full word is guessed you win the game... if not it
					// decreases the guessCount
				if (guess.equals(secretWord)) {

					System.out.println("You guessed my word!");
					gameWon = true;
					break;

				} else {

						// Note the difference between "print" and "println"!
					System.out.print("Nope, ");

				}
			} 

		}

		if (!gameWon) {
				System.out.println("you lost, secret word was " + secretWord);
		}
		if (gameWon == true) {
			System.out.println("You won! Secret word was " + secretWord);
		}
	}
}

