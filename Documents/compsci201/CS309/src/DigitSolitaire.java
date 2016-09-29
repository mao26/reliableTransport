import java.util.ArrayList;
import java.util.Scanner;

public class DigitSolitaire {
	
	static ArrayList<String> global;
	static int counter;
	
	public DigitSolitaire(String input){
		global = new ArrayList<String>();
		global.add(input);
		counter = 0;
	}

	public ArrayList<String> mult(String input){
		int length = input.length();
		counter += 1;
		if(length == 1){
			//global.add(input);
			return null;
		}
		int var1, var2, temp = -1;
		for(int i = 0; i < length - 1; i++){
			//by having it go to length -1 we don't need to check if it's in bounds
			if(temp == -1){
				//we haven't used our temp yet
				var1 = Integer.parseInt(input.substring(i, i+1));
				var2 = Integer.parseInt(input.substring(i+1, i+2));
				//System.out.printf("our first var : %d, our second var : %d\n", var1, var2);
				temp = var1 * var2;
				
			} else {
				//variable is stored in our temp
				var1 = Integer.parseInt(input.substring(i+1, i+2));
				temp = temp * var1;
			}
			
			//System.out.printf();
			
		}
		//System.out.println(temp);
		global.add(temp + "");
		return mult(global.get(counter));
	}
	
	
	public static void main(String[] args){
		
		
		// 1. Create a Scanner using the InputStream available.
	    Scanner scanner = new Scanner( System.in );

	    while(true){
	    	String input = scanner.nextLine();
	    	if(input.equals("0")) break;
	 	    DigitSolitaire ds = new DigitSolitaire(input);
	 	    ds.mult(input);
	 	    for(int i = 0; i < global.size(); i++){
	 	    	if(global.get(i) == global.get(global.size()-1)){
	 	    		System.out.print(global.get(i));
	 	    		break;
	 	    	}
	 	    	System.out.print(global.get(i) + " ");
	 	    }
	 	    System.out.println();
	    }
	    // 3. Use the Scanner to read a line of text from the user.
	   
	    // 4. Now, you can do anything with the input string that you need to.
	    // Like, output it to the user.
	    //System.out.println( "input = " + input );
	}
	
	
}
