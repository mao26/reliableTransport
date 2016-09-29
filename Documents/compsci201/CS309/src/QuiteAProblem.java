import java.util.Scanner;

public class QuiteAProblem {

	public static void main(String[] args) {
		QuiteAProblem qap = new QuiteAProblem();
		qap.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			String input = scan.nextLine();
			check(input);
		}
		scan.close();
	}

	private void check(String input) {
		if(input.toLowerCase().contains("problem")){
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
		
	}

}
