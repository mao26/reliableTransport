import java.util.Scanner;

public class PowerEggs {

	

	private int run(int floors, int eggs) {
		
		if(eggs == 1){
			
			System.out.printf("number of drops is: %d\n", floors);
			
			return floors;
		}
		
		
		
		return 0;
		
	}
	
	
	public static void main(String[] args){
		
		PowerEggs pe = new PowerEggs();
		
		Scanner scanner = new Scanner(System.in);
		int determine_T = scanner.nextInt();
		for(int i = 0; i < determine_T; i++){
			
			int floors_N = scanner.nextInt();
			int eggs_K = scanner.nextInt();
			
			System.out.printf("amount of floors %d amount of eggs %d\n", floors_N, eggs_K);
			
			pe.run(floors_N, eggs_K);
			
		}
		/*
		 *
4
10 1
100 2
30 30
2000000000 2
		 
		 
		 
		 */
		
		
	}
	
}
