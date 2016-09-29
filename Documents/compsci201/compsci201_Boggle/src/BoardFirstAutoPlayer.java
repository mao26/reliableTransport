import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class BoardFirstAutoPlayer extends AbstractAutoPlayer {
	private void ahelper(BoggleBoard board, int row, int col, List<BoardCell> list, ILexicon lex, StringBuilder strbuild, int len) {
//		if (idx >= tempword.toString().length()) {
//			add(tempword.toString());
//			return;
//		}
		if (row >= board.size() || col >= board.size() || row<0 || col<0) {
			return;
		}
//		if(board.getFace(row,col).equals(strbuild.toString().charAt(idx)+"")){
//			return;
//		}
//		if(lex.wordStatus(strbuild.toString())==LexStatus.NOT_WORD){
//			return;
//		}
		String test = board.getFace(row,col);

		BoardCell nCell = new BoardCell(row, col);
		if(list.contains(nCell)) return;
		strbuild.append(test);
		list.add(nCell);
//		if(lex.wordStatus(strbuild.toString())==LexStatus.PREFIX){
//			strbuild.append(board.getFace(row,col));
//		}
		if(lex.wordStatus(strbuild) == LexStatus.WORD){
			if(strbuild.length() >= len){
				add(strbuild.toString());
			}
		}
		if(lex.wordStatus(strbuild) == LexStatus.PREFIX || lex.wordStatus(strbuild) == LexStatus.WORD){
			int[] rdelta = { -1, -1, -1, 0, 0, 1, 1, 1 };
			int[] cdelta = { -1, 0, 1, -1, 1, -1, 0, 1 };
			for(int k = 0; k <rdelta.length; k++){
				ahelper(board, row+rdelta[k], col+cdelta[k], list, lex, strbuild, len);
				
			}
			
		}
		list.remove(nCell);
		if(test.equals("qu")){
//		if(board.getFace(row,col).equals("qu")){
			strbuild.delete(strbuild.length()-2, strbuild.length());

			return;
		}
		strbuild.deleteCharAt(strbuild.length()-1);
		return;
	}

	@Override
	public void findAllValidWords(BoggleBoard board, ILexicon lex, int minLength) {
		clear(); //or just clear();
		List<BoardCell> lista= new ArrayList<BoardCell>();
		for(int r = 0; r <board.size(); r++){
			for(int c = 0; c < board.size(); c++){
				StringBuilder str = new StringBuilder();
				ahelper(board, r, c, lista, lex, str, minLength);
			}
		}
	}

}
