import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TheFlood {
	
	
	
	//declare your edge path
	//write our breath first search
	//make our two dimensional array
	
	static ArrayList<TheFlood> ogArray = new ArrayList<TheFlood>();
	
	public TheFlood(){
		
	}
	
	public TheFlood(int[] pos, boolean flooded, boolean isItLand, boolean edge, boolean vis){
		int position[] = pos;
		boolean isItFlooded = flooded;
		boolean land = isItLand;
		boolean isItEdgePath = edge;
		boolean visited = vis;
	}
	
	public void createArray(int[][] arrayInput){
		boolean land, flood, edge, visited = false;
		int position[] = {0, 0};
		for(int r = 0; r < arrayInput.length; r++){
			for(int c = 0; c < arrayInput[r].length; c++){
				position[0] = r;
				position[1] = c;
				if(arrayInput[r][c] == 0){
					 land = false;
					 flood = true;
				} else {
					land = true;
					flood = false;
				}
			}
		}
	}
	
	 
	public ArrayList<TheFlood> createOurEdgePath(int[][] array){ 
		//for
		
		return null;
	}

	public void readFile(int[][] prax) throws IOException {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the filename: "); // Prompt the user for a file
													// name
		String fileName = scanner.nextLine(); // get a file name from the user
		File file = new File(fileName);
		 if(!file.exists()) createArray(prax);
			 
		// file = new File("c:\\Users\\mario_oliver93\\Desktop\\ICPC\\theFlood.txt");
		if (file.exists()) // check that the file exists
		{ // before trying to create a
			// Scanner to read the file
			// Create a Scanner from the file.
			// This statement can cause a FileNotFoundException.
			Scanner inFile = new Scanner(file);

			// For each line in the file, read in the line and display it with
			// the line number
			int lineNum = 0;
			String line;

			// Use the results of calling the hasNext method to
			// determine if you are at the end of the file before
			// reading the next line of the file.
			while (inFile.hasNext()) {
				line = inFile.nextLine(); // read the next line

				// Output the line read to the screen for the user
				System.out.println(++lineNum + ": " + line);
			}
			System.out.println("we are closing our file");
			// When we're done reading the file,
			// close the Scanner object attached to the file
			inFile.close();
		} else
			System.out.println("our file doesn't exist");
	}

	public static void main(String[] args) throws IOException {

		int[][] prax = new int[][] { { 5, 5, 5, 5, 7}, { 4, 1, 1, 1, 4}, { 4, 1, 2, 1, 4}, { 7, 1, 0, 0, 4}, { 7, 3, 4, 4, 4} };
		
		int nums[] = {5,5};
		TheFlood flood = new TheFlood();
		flood.readFile(prax);

	}

}
