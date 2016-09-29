public class GridGame {

	String[][] game = new String[4][4];
	
	public static void main(String[] args) {
		String[] grid = { ".X.X", "..X.", ".X..", "...." };
		GridGame gg = new GridGame();
		System.out.println(gg.winningMoves(grid));

	}

	public int winningMoves(String[] grid) {
		
		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[i].length(); k++) {
				game[i][k] = "" + grid[i].charAt(k);
//				System.out.print(game[i][k]);
			}
		}
		for(int i = 0; i < game.length; i++){
			for(int j = 0; j < game[i].length; j++){
				
			}
		}
		return wins();
	}
	
	public int wins(){
		int winCounter = 0; 
		for(int r = 0; r < game.length; r++){
			for(int c = 0; c < game[r].length; c++){
				if(canPlace(r, c)){
					game[r][c] = "X";
					if (wins() == 0){
						winCounter++;
					}
					game[r][c] = ".";
				}
			}
		}
		return winCounter;
	}
	
	public boolean inGrid(int row, int col){
		if(row < 0 || col < 0){
			return false;
		}
		if(row >= 4 || col >= 4){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean canPlace(int row, int col){
		boolean inBounds = inGrid(row, col);
		if(inBounds && game[row][col].equals("X")){
			return false;
		}
		if(inGrid(row+1, col) && game[row+1][col].equals("X")){
			return false;
		}
		if(inGrid(row-1, col) && game[row-1][col].equals("X")){
			return false;
		}
		if(inGrid(row, col+1) && game[row][col+1].equals("X")){
			return false;
		}
		if(inGrid(row, col-1) && game[row][col-1].equals("X")){
			return false;
		}
		return true;
	}

}
