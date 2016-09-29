import java.util.ArrayList;


public class Robbed {
	public static void main(String[] args) {
		String[] maze = { ".R...", "..X..", "....X", "X.X.X", "...C." };
		Robbed r = new Robbed();
		System.out.println(r.countRoutes(maze));
	}// main method

	public class Node {
		boolean winner;
		int row;
		int col;
	}

	public char mazeChar(int col, int row, String[] maze) {
		if (col >= maze[0].length() || col < 0) {
			return 'Z';
		}
		if (row >= maze.length || row < 0) {
			return 'Z';
		} else {
			return maze[row].charAt(col);
		}
	}

	public int countRoutes(String[] maze) {
		int count = 0;
		ArrayList<Node> previousLvl = new ArrayList<Node>();
		ArrayList<Node> currLvl = new ArrayList<Node>();
		int crow = -1;
		int ccol = -1;
		for (int i = 0; i < maze.length; i++) {
			int location = maze[i].indexOf('C');
			if (location != -1) {
				crow = i;
				ccol = location;
				break;
			}
		}
		int rcol = -1;
		int rrow = -1;
		for (int i = 0; i < maze.length; i++) {
			int location = maze[i].indexOf('R');
			if (location != -1) {
				rrow = i;
				rcol = location;
				break;
			}
		}
		Node head = new Node();
		head.col = rcol;
		head.row = rrow;
		previousLvl.add(head);
		while (true) {
			for (Node node : previousLvl) {
				for (int i = 0; i < 2; i++) { 
					// each node will have only two children
					// our if statement is saying that if 0= first move, else= second move
					
					if(i == 0){ //advance row we do it by getting it closer to the value of the crow
						if(crow < node.row){
							char p = mazeChar(node.col, node.row-1, maze);
							if(p == 'X'){
								continue;
							}
							if(p == 'C'){
								node.winner = true;
								count++;
							} else { // if '.'
								Node notYet = new Node();
								currLvl.add(notYet);
								notYet.row = rrow - 1;
								notYet.col = rcol;
							}
						} else if(crow > node.row){
							char p = mazeChar(node.col, node.row+1, maze);
							if(p == 'X'){
								continue;
							}
							if(p == 'C'){
								node.winner = true;
								count++;
							} else { // if '.'
								Node notYet = new Node();
								currLvl.add(notYet);
								notYet.row = node.row + 1;
								notYet.col = node.col;
							}
						}
						
					} else { // advance col
						if(ccol < node.col){
							char p = mazeChar(node.col - 1, node.row, maze);
							if(p == 'X'){
								continue;
							}
							if(p == 'C'){
								node.winner = true;
								count++;
							} else { // if '.'
								Node notYet = new Node();
								currLvl.add(notYet);
								notYet.row = node.row;
								notYet.col = node.col -1;
							}
						} else if(ccol > node.col){
							char p = mazeChar(node.col+1, node.row, maze);
							if(p == 'X'){
								continue;
							}
							if(p == 'C'){
								node.winner = true;
								count++;
							} else { // if '.'
								Node notYet = new Node();
								currLvl.add(notYet);
								notYet.row = node.row;
								notYet.col = node.col+1;
							}
						}
					}
				}
			}
			if(currLvl.isEmpty()){
				break;
			}
			previousLvl = currLvl;
			currLvl = new ArrayList<Node>();
		}
		return count;
	}
}
