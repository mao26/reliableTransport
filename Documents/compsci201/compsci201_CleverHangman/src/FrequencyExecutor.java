
public class FrequencyExecutor {
	public static void main(String[] args){
		generateStatistics();
	}
	
	public static void generateStatistics() {
		int missCount;
		for (int wlength = 8; wlength <= 12; wlength++) {
			MinExecutor ng = new MinExecutor(wlength, 10, new FrequencyGuesser(wlength), false);
			System.out.printf("word length: %d\n", wlength);
			ng.stress();

			int winsCount = ng.getMyWins();
			int wordCount = ng.getMyWordCount();
			System.out.println("letters: " + wlength  + " / words possible: " + wordCount + " / percentage: " + 100.0 * winsCount/ (float) wordCount);
		}
	}
}
