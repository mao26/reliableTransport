import java.util.Scanner;

public class MissingPages {

	public static void main(String[] args) {
		MissingPages mp = new MissingPages();
		mp.run();
	}

	private void run() {
		Scanner s = new Scanner(System.in);
		while (true) {
			int numberPages = s.nextInt();
			if(numberPages == 0) break;
			int[] out = new int[3];
			int ripped = s.nextInt();
			if(ripped > numberPages/2){
				int offset = ripped - (numberPages/2);
				int x = (numberPages/2) - offset;
				if(ripped % 2 == 0){
					out[0] = ripped -1;
					out[1] = x +1;
					out[2] = x +2;
				} else {
					out[0] = ripped +1;
					out[1] = x;
					out[2] = x+1;
				}
			} else {
				int offset = (numberPages/2) - ripped;
				int x = (numberPages/2) + offset; //add one because we need to start on the higher page of half our input
				if(ripped % 2 == 0){
					out[0] = ripped -1;
					out[1] = x +1;
					out[2] = x +2;
				} else {
					out[0] = ripped +1;
					out[1] = x;
					out[2] = x+1;
				}
			}
			String output = "";
			int temp = 0;
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					if(out[i] < out[j]){
						temp = out[i];
						out[i] = out[j];
						out[j] = temp;
					}
				}
			}
			for(int each: out){
				output += each;
				if(!(each == out[2])){ //if our last number to add, don't add space
					output += " ";
				}
			}
			System.out.println(output);
		}
		s.close();
	}
}
