import java.util.Scanner;

public class JugHard {

	private String run(int a, int b, int c) {
		if(a == b){
			
			return "cannot divide by zero";
		}
		
		
		if(c == a || c == b){
			System.out.println("1\n");
			return "Yes";
		}
		if(c % b == 0 || c % a == 0){
			System.out.println("2\n");
			return "Yes";
		}
		int remainder_b = a % b;
		int remainder_a = b % a;
		if(remainder_a == 0 || remainder_b == 0){
			System.out.println("cannot divide by zero");
			return "No";
		}
		if(c % remainder_b == 0){
			System.out.println("3\n");
			return "Yes";
		}
		if(c % remainder_a == 0){
			System.out.println("4\n");
			return "Yes";
		}
		
		return "No";
		
	}
	
	
	public static void main(String[] args){
		
		JugHard pe = new JugHard();
		
		Scanner scanner = new Scanner(System.in);
		int determine_T = scanner.nextInt();
		for(int i = 0; i < determine_T; i++){
			
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			int c = scanner.nextInt();
			
			System.out.printf("a = %d b = %d c = %d\n", a, b, c);
			
			String answer = pe.run(a, b, c);
			System.out.println(answer);
			
		}
		/*
		 *
3
8 1 5
4 4 3
5 3 4
10 2 7 
		 
		 
		 
		 */
		
		
	}
	
	
}
