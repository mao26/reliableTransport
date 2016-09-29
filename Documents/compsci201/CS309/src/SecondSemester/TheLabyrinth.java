package SecondSemester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TheLabyrinth {

	public TheLabyrinth() {
		run();
	}

	private void run(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			int r = in.read();
			int c = in.read();
			String[][] maze = new String[r][c];
			for(int i = 0; i < r; i++){
				String line = in.readLine();
				parseLine(maze, line, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void parseLine(String[][] maze, String line, int row) {
		String[] eachItem = line.split("");
		int i =0;
		for(String each: eachItem){
			maze[row][i] = each;
			i++;
		}
	}

	public static void main(String[] args){
		TheLabyrinth tl = new TheLabyrinth();
	}
	
}
