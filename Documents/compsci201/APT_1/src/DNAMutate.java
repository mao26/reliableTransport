
public class DNAMutate {

	public static String mutate(String dna) {
		String blank = "";
		for(int i = dna.length()-1; i >= 0; i--){
			blank = blank + dna.charAt(i);
		}
		System.out.println(blank);
		System.out.println("The correct answer is 'taattggcc'");
		System.out.println("The correct answer is 'ccttggaa'");
		return blank;
	}
	public static void main(String[] args){
		mutate("ccggttaat");
		mutate("aaggttcc");
	}
}

