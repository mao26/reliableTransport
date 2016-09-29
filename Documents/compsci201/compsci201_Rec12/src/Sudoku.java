import java.util.HashSet;
import java.util.Set;

/**
 * 9x9 Sudoku board solver
 */
public class Sudoku {
	/**
	 * Solve the board if possible and return true. If the board is not
	 * solvable, return false; NOTE: A 0 represents a blank space.
	 */
	public boolean solve(int[][] board) {
		/*
		 * TODO: complete Sudoku solve() using backtracking You should use the
		 * completed nextOpenSpace() and isValidBoard(board) methods.
		 */
		int[] next = nextOpenSpace(board);
		if (next[0] == -1) {
			return true;
		}
		if (!isValidBoard(board)) {
			return false;
		}
		
		
		for (int k = 1; k <= 9; k++) {
			int row = next[0];
			int col = next[1];
			board[row][col] = k;
			if(solve(board)){
				return true;
			}
			board[row][col] = 0;
		}
		return false;
	}

	/**
	 * Return [row, col] coordinate of the next open space. If there are no
	 * remaining open spaces, return [-1, -1].
	 */
	public int[] nextOpenSpace(int[][] board) {
		int n = board.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0) {
					return new int[] { i, j };
				}
			}
		}
		return new int[] { -1, -1 };
	}

	/**
	 * Returns true if the board is valid so far
	 */
	public boolean isValidBoard(int[][] board) {
		for (int i = 0; i < 9; i++) {
			int[] row = board[i];
			int[] col = new int[9];
			int[] box = new int[9];

			for (int j = 0; j < 9; j++) {
				col[j] = board[j][i];
				box[j] = board[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3];
			}
			if (!(validSoFar(row) && validSoFar(col) && validSoFar(box))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Helper method for isValidBoard You should not need to call this method
	 * directly.
	 */
	private boolean validSoFar(int[] arr) {
		Set<Integer> used = new HashSet<Integer>();
		for (int i = 0; i < arr.length; i++) {
			int value = arr[i];
			if (value == 0) {
				continue;
			}
			if (!used.add(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Print the board. NOTE: A 0 represents a blank space.
	 */
	public void print(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// NOTE: A 0 represents a blank space.
		int[][] board = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
				{ 5, 2, 0, 0, 0, 0, 0, 0, 0 }, { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
				{ 0, 0, 3, 0, 1, 0, 0, 8, 0 }, { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
				{ 0, 5, 0, 0, 9, 0, 6, 0, 0 }, { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 7, 4 }, { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
		Sudoku s = new Sudoku();

		if (s.solve(board)) {
			s.print(board);
		} else {
			System.out.println("No solution found");
		}

	}
}
