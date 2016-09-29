import java.util.ArrayList;
import java.util.List;

public class GoodWordOnBoardFinder implements IWordOnBoardFinder {

	@Override
	public List<BoardCell> cellsForWord(BoggleBoard board, String word) {
		ArrayList<BoardCell> list = new ArrayList<>();
		for (int r = 0; r < board.size(); r++) {
			for (int c = 0; c < board.size(); c++) {
				if (findHelper(word, 0, r, c, board, list)) {
					return list;
				}
			}
		}
		list.clear();
		return list;
	}

	private boolean findHelper(String word, int idx, int r, int c, BoggleBoard board, ArrayList<BoardCell> list) {
		if (idx >= word.length()) {
			return true;
		}
		if (r >= board.size() || r < 0) {
			return false;
		}
		if (c >= board.size() || c < 0) {
			return false;
		}
		BoardCell nCell = new BoardCell(r, c);
		if(list.contains(nCell)) return false;
//		for (BoardCell cell : list) {
//			if (cell.compareTo(nCell) == 0) {
//				return false;
//			}
//		}
		if(board.getFace(r, c).equals("qu")){
			if(idx + 2 > word.length()) return false;
			if(!board.getFace(r,c).equals(word.substring(idx, idx+2))) return false;
			if(list.contains(nCell)) return false;
			if(idx == word.length()-1) return false;
			int[] rdelta = { -1, -1, -1, 0, 0, 1, 1, 1 };
			int[] cdelta = { -1, 0, 1, -1, 1, -1, 0, 1 };
			for (int i = 0; i < rdelta.length; i++){
				if(findHelper(word, idx + board.getFace(r, c).length(), r+ rdelta[i], c + cdelta[i], board,list )){
					return true;
				}
			}
		}
		if (word.charAt(idx) != board.getFace(r, c).charAt(0)) {
			return false;
		}
		list.add(nCell);
		int[] rdelta = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] cdelta = { -1, 0, 1, -1, 1, -1, 0, 1 };
		for (int k = 0; k < rdelta.length; k++) {
			if (findHelper(word, idx + 1, r + rdelta[k], c + cdelta[k], board,list)) {
				return true;
			} 
		}
		list.remove(nCell);
		return false;
	}
}
