import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiteaproblem {

	static boolean yes = false;
	
	public static void main(String[] args){
		
		Quiteaproblem qap = new Quiteaproblem();
		try {
			qap.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private void run() throws FileNotFoundException {

		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			String input = scanner.nextLine();
			String[] listOfWords = input.split(" ");
			for(int i = 0; i < listOfWords.length; i++){
				if(listOfWords[i].toLowerCase().contains("problem")){
					yes = true;
					break;
				}
			}
			if(yes){
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
			yes = false;
		}
		
		
	}
	
	
}
