import java.util.*;
import java.util.Scanner;

public class Game {
	char[][] board = new char[3][3];
	int turn = 0;
	
	public char[][] play() {
		for(int i = 0; i < board.length; i++){
//			turn++;
			for(int j = 0; j < board[i].length; j++){
				if(inBounds(i,j)){
					board[i][j] = '*';
				}
			}
		}
		printBoard();
		Scanner s = new Scanner(System.in);
		int row;
		int col;
		while(!gameOver()){
			turn++;
			System.out.println("Enter the row you'd like to play on (0,1,2): ");
			row = s.nextInt();
			System.out.println("Enter the column you'd like to play on (0,1,2): ");
			col = s.nextInt();
			if(inBounds(row, col)){
				board[row][col] = playerTurn();
			} else {
				// out of bounds
			}
			printBoard();
		}
		//define which player just won!
		System.out.println("You win!");
		return board;
	}
	
	public char[][] printBoard(){
		System.out.println("-------");
		for(int i = 0; i < board.length; i++){
			System.out.print('|');
			for(int j = 0; j < board[i].length; j++){
				System.out.print(board[i][j]);
				System.out.print('|');
			}
			System.out.println();
			System.out.println("-------");
		}
//		System.out.println("-------");
		return null;
	}
	
	public boolean inBounds(int row, int col){
		if(row < 3) return true;
		if(col < 3) return true;
		if(col >= 0) return true;
		if(col >= 0) return true;
		return false;
	}
	
	public char playerTurn(){
		if(turn % 2 == 1){
		//if turn == true
			return 'X';
		}
		return 'O';
	}
	
	public boolean gameOver(){
		if(board[0][1] == '*' || board[1][0] == '*' || board[1][2] == '*' || board[2][1] == '*' || board[1][1] == '*') return false;
		if(board[0][0] == board[0][1] && board[0][1] == board[0][2]){
			return true;
		}
		if(board[1][0] == board[1][1] && board[1][1] == board[1][2]){
			return true;
		}
		if(board[2][0] == board[2][1] && board[2][1] == board[2][2]){
			return true;
		}
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2]){
			return true;
		}
		if(board[0][2] == board[1][1] && board[1][1] == board[2][0]){
			return true;
		} 
		return false;
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		g.play();
	}
	
}
