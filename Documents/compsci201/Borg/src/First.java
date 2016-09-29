import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class First {

	ArrayList<Integer> chars;
	
	
	public static void main(String[] args) {
		
		First first = new First();
		first.run();
		
		
	}

	private void run() {
		//System.out.println(System.getProperty("user.dir"));
		File file = new File(System.getProperty("user.dir") + "/src/text_files/borg1.txt");
		String[] line = new String[2];
		int a = 0, b = 0, idx = 1;
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNext()){
				
				line = scan.nextLine().split(" ");
				a = Integer.parseInt( line[0]);
				b = Integer.parseInt( line[1]);
				
				if(a == b && a == 0){
					break;
				}
				System.out.printf("Case %d: ", idx);
				idx += 1;
				int c = math(a,b);
				System.out.println(c);
			}
			
			
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private int math(int a, int b) {
		int c = a + b;
		return c;		
	}
	
}
