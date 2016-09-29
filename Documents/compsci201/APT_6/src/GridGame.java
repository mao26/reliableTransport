

public class GridGame {
	//  data structure to store the grid

	String[][] grid = new String[4][4];

	public static void main(String[] args){

		GridGame a = new GridGame();
		String[] gridInput = {".X.X",
				"..X.",
				".X..",
		"...."};
		
		String[] input = {"....",
				 "....",
				 ".X..",
				 
				 "...."};
		System.out.println(a.winningMoves(input));
	}

	public boolean isInGrid(int row, int col){
		if(row < 0 || col < 0){
			return false;
		}
		if(row >= 4 || col >= 4){
			return false;
		}
		return true;
	}
	
	public boolean canPlaceAt(int row, int col) {
		// returns true if you can place an X at row, col
		
		boolean inGrid = isInGrid(row, col);
		if(inGrid && grid[row][col].equals("X")){
			return false;
		}
		boolean inGrid2 = isInGrid(row+1, col);
		if(inGrid2 && grid[row+1][col].equals("X")){
			return false;
		}
		boolean inGrid3 = isInGrid(row-1, col);
		if(inGrid3 && grid[row-1][col].equals("X")){
			return false;
		}
		boolean inGrid4 = isInGrid(row, col+1);
		if(inGrid4 && grid[row][col+1].equals("X")){
			return false;
		}
		boolean inGrid5 = isInGrid(row, col-1);
		if(inGrid5 && grid[row][col-1].equals("X")){
			return false;
		}
//		System.out.println("I can place an x at : " + row + " " + col);
		return true;
	}
	public int winningMoves(String[] gridInput) {
		//		int count = 0;
		// store input into your grid
		for(int i = 0; i < gridInput.length; i++){
			for(int j = 0; j < gridInput[i].length(); j++){
				String temp = gridInput[i].substring(j, j+1);
				//				count++;
				grid[i][j] = temp;
			}
			//			count = 0;
		}
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
//				System.out.print(grid[i][j]);		
			}
//			System.out.println();
		}

		return wins(); // recursive helper  function
	}
	public int wins() {	
		int winCounter  = 0;
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				if(canPlaceAt(r,c)) {
					grid[r][c] = "X";
					if(wins() == 0) winCounter++;
						// increment count
//					System.out.println("winCounter: " + winCounter);
					grid[r][c] = "."; // backtrack
				}
			}
		}
//		System.out.println("final wincounter: " + winCounter);
		return winCounter;
	}
}