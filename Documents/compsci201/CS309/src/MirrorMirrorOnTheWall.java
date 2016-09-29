import java.util.Scanner;

public class MirrorMirrorOnTheWall {

	String opposites;
	String possibleMirrors;
	
	public MirrorMirrorOnTheWall(){
		possibleMirrors = "biodwvxpq";
		opposites = "diobwvxqp";
	}
	
	public static void main(String[] args){
		MirrorMirrorOnTheWall m = new MirrorMirrorOnTheWall();
		m.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		boolean yo = true;
		while(yo){
			String input = scan.nextLine();
			if(input.equals("vivid")) yo = false;
			if(input == "#") break;
			String flip = flip(input);
			mirror(flip);
		}
		scan.close();
		
	}

	private String flip(String input) {
		String output = "";
		for(int i = input.length()-1; i >= 0; i--){
			output += input.charAt(i);
		}
		return output;
	}

	private void mirror(String input) {
		String output = "";
		if(input.equals("#")) return;
		for(int i = 0; i < input.length(); i++){
			char need = input.charAt(i);
			if(!possibleMirrors.contains(""+need)){
				output = "INVALID";
				break;
			}
			int idx = possibleMirrors.indexOf(need);
			output += "" + opposites.charAt(idx);
		}
		if(input.equals("vivid")){
			System.out.print(output);
		} else{
			System.out.println(output);
		}
	}
	
}
