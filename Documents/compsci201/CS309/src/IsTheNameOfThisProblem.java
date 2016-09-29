import java.util.Scanner;

public class IsTheNameOfThisProblem {

	public static void main(String[] args) {
		IsTheNameOfThisProblem is = new IsTheNameOfThisProblem();
		is.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(true){
			String input = scan.nextLine();
			if(input.equals("END")) break;
			if(!input.contains("\"")){
				System.out.println("not a quine");
				continue;
			}
			if(input.equals("\"\"")){
				System.out.println("not a quine");
				continue;
			}
			if(input.charAt(0) != '"'){
				System.out.println("not a quine");
				continue;
			}
			if(!input.substring(1, input.length()).contains("\"")){
				System.out.println("not a quine");
				continue;
			}
			String quine = input.substring(1, input.indexOf('"', 1));
			String second = input.substring(input.indexOf('"', 1)+2);
			if(quine.equals(second)){
				if(input.charAt(input.indexOf('"', 1)+1) != ' '){
					System.out.println("not a quine");
					continue;
				}
				else{
					System.out.println("Quine("+quine+")");
				}
			} else {
				System.out.println("not a quine");
			}
		}
		scan.close();
	}

}
