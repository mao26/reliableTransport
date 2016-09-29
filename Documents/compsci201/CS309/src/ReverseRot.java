import java.util.Scanner;

public class ReverseRot {

	String str;
	
	public ReverseRot(){
		str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_.ABCDEFGHIJKLMNOPQRSTUVWXYZ_.";
	}
	
	public static void main(String[] args){
		ReverseRot rr = new ReverseRot();
		rr.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(true){
			int x = scan.nextInt();
			if(x == 0) break;
			String word = scan.next();
			rotate(word, x);
		}
		scan.close();
	}

	private void rotate(String word, int rot) {
		String rotatedWord = "";
		for(int i = 0; i < word.length(); i++){
			int idx = str.indexOf(word.charAt(i));
			int idxOfRot = idx + rot;
			rotatedWord += str.charAt(idxOfRot);
		}
		//System.out.println(rotatedWord);
		reverse(rotatedWord);
	}

	private void reverse(String rotatedWord) {
		String reversedWord = "";
		for(int i = rotatedWord.length()-1; i >= 0; i--){
			reversedWord += rotatedWord.charAt(i);
		}
		System.out.println(reversedWord);
	}
	
}
