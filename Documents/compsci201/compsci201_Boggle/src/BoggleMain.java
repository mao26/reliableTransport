import java.util.Random;
import java.io.InputStream;


/**
 * Play a game of Boggle. To play code must instantiate the
 * BoggleGui gui object with a working IWordOnBoardFinder and
 * a working ILexicon, as well as a working IAutoPlayer.
 */

public class BoggleMain {

    public static void main(String[] args) {
        
        ILexicon lexicon = new SimpleLexicon();
        IWordOnBoardFinder finder = new GoodWordOnBoardFinder();
        InputStream is = lexicon.getClass().getResourceAsStream("/ospd3.txt");      
        IAutoPlayer compPlayer = new BoardFirstAutoPlayer();
        BoggleGUI bgui = new BoggleGUI(lexicon,finder,is, compPlayer);
    }

}
/*
 * private int wordCount(Node root){
 *   if (root == null){
 *     return 0;
 *   }
 *   int count = isWord ? 1 : 0;
 *   for(Node child: children.values()){
 *      count += wordCount(child);
 *      return count;
 *   }
 * }
 *    
 */


/*
 * Boggle recursion base cases
 * 1) if (index >= word.length) 
 * 2) if (row < 0 || col < 0)) row > board.size || col >= boardsize())
 * 4) if (list.contains(new BoardCell(row, col))
 * 3) if(board.getFace(row, col).equals(word.substring(index, index+1)) 
 * if want to make it more general for 	qu case then
 *    if(board.getFace(row, col).equals(word.substring(index, index+ board.getFace(row, col).length())
 */

