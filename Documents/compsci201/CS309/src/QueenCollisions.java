import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class QueenCollisions {

	ArrayList<String> connections = new ArrayList<String>();

	public static void main(String[] args) {
		QueenCollisions qc = new QueenCollisions();
		qc.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while (true) {
			int n = scan.nextInt();
			if (n == 0)
				break;
			String[][] board = createBoard(n);
			int g = scan.nextInt();
			for (int i = 0; i < g; i++) {
				int k = scan.nextInt();
				int x = scan.nextInt();
				int y = scan.nextInt();
				int s = scan.nextInt();
				int t = scan.nextInt();
				System.out.println();
				addQueens(k, x, y, s, t, board, n);
				if (i == g - 1)
					printBoard(n, board);
			}
		}
		scan.close();

	}

	private void addQueens(int k, int x, int y, int s, int t, String[][] board, int n) {
		int count = 0;
		for (int i = 0; i < k; i++) {
			int newX = (x + (i * s)) - 1;
			int newY = (y + (i * t)) - 1;
			board[newY][newX] = "Q";
			count += up(board, newY, newX, 1, n);
		}
	}

	private int up(String[][] board, int y, int x, int increase, int n) {
		if(!inBounds(y + increase, x, n)){
			return 0;
		} 
		if(board[y+increase][x].equals("Q")){
			if(!check(x,y,increase,0)){
				createConnection(x, y, 0, increase);
				return 1;
			} //if these two have been checked before then return 0
			return 0;
		}
		return 0 + up(board, y, x, increase, n);
	}
	
	private int down(String[][] board, int y, int x, int increase, int n) {
		if(!inBounds(y + increase, x, n)){
			return 0;
		} 
		if(board[y+increase][x].equals("Q")){
			if(!check(x,y,increase,0)){
				createConnection(x, y, 0, increase);
				return 1;
			} //if these two have been checked before then return 0
			return 0;
		}
		return 0 + up(board, y, x, increase, n);
	}

	private boolean check(int x, int y, int yinc, int xinc){
		for(String each: connections){
			if(each.contains("x "+ (x + xinc)) && each.contains("y " + (y + yinc))) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	private void createConnection(int x, int y, int xincrease, int yincrease) {
		String need = "x " + (x+xincrease);
		need = " y " + (y+yincrease);
		connections.add(need);
	}

	private boolean inBounds(int y, int x, int n) {
		if (y >= n || x >= n || y <= 0 || x <= 0)
			return false;
		return true;
	}

	private String[][] createBoard(int n) {
		String[][] board = new String[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = "*";
			}
		}
		return board;
	}

	private void printBoard(int n, String[][] board) {
		for (int i = 0; i < n + 1; i++)
			System.out.print("- ");
		System.out.println();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print("|");
				System.out.print(board[i][j]);
			}
			System.out.println("|");
			for (int k = 0; k < n + 1; k++)
				System.out.print("- ");
			System.out.println();
		}
	}

}
