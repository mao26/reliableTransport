import java.util.ArrayList;

public class MinExecutor extends GuessExecutor {
	public static void main(String[] args) {
		generateStatistic();
		generateMinCounts();
	}

	public static void generateStatistic() {
		int missCount;
		for (int wlength = 8; wlength <= 12; wlength++) {
			MinExecutor ng = new MinExecutor(wlength, 10, new NaiveGuesser(false), false);
			System.out.printf("word length: %d\n", wlength);
			ng.stress();

			int winsCount = ng.getMyWins();
			int wordCount = ng.getMyWordCount();
			System.out.println("letters: " + wlength  + " / words possible: " + wordCount + " / percentage: " + 100.0 * winsCount/ (float) wordCount);
		}
	}

	public static void generateMinCounts() {
		for (int wlength = 8; wlength <= 12; wlength++) {
			for(int alphabetLength = 1; alphabetLength <= 26; alphabetLength++){
				MinExecutor ng = new MinExecutor(wlength, alphabetLength, new NaiveGuesser(false), false);
				System.out.printf("word length: %d\n", wlength);
				boolean ignored = ng.stress();
				if (ignored == true) {
					System.out.println("Minimun number of misses: " + alphabetLength);
					break;
				}
			}
		}
	}

	/**
	 * Initialize a guesser to stress-test all words
	 * 
	 * @param length
	 *            is length of words being stress-tested
	 * @param misses
	 *            is number of misses until hung in testing
	 * @param guesser
	 *            is the class-instance being stress-tested
	 * @param debug
	 *            specifies debugging status
	 */
	public MinExecutor(int length, int misses, IHangGuesser guesser, boolean debug) {
		super(length, misses, guesser, debug);

	}

}
