import java.util.ArrayList;
import java.util.Scanner;

public class MadScientist {

	public static void main(String[] args){
		MadScientist ms = new MadScientist();
		ms.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(true){
			int num = scan.nextInt();
			if(num == 0) break;
			int[] moves = new int[num];
			for(int i = 0; i < num; i++){
				moves[i] = scan.nextInt();
			}
			createMoves(moves);
		}
		scan.close();
	}

	private void createMoves(int[] moves) {
		int count = 0;
		int idx = 1;
		ArrayList<Integer> prax = new ArrayList<Integer>();
		for(int each: moves){
			if(count < each){
				for(int i = count; i < each; i++){
					prax.add(idx);
				}
			}
			idx += 1;
			count += each;
		}
		output(prax);
	}

	private void output(ArrayList<Integer> prax) {
		int idx = 0;
		for(int each: prax){
			if(idx == prax.size()-1) {
				System.out.print(each);
			}
			System.out.print(each + " ");
			idx += 1;
		}
		System.out.println();
	}
	
}
