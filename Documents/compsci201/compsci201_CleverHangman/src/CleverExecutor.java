
public class CleverExecutor {

	
	public static void main(String[] args) {
		int wordLength = 10; 
		CleverHangman game = new CleverHangman(wordLength,8);
		HumanGuesser guesser = new HumanGuesser(); 
//		FrequencyGuesser guesser = new FrequencyGuesser(wordLength); 
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
	}

}
