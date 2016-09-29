package NewTetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import NewTetris.Shape1.Tetrinomes;

public class Board1 extends JPanel implements ActionListener{

    final int BoardWidth = 10;
    final int BoardHeight = 22;

    Timer timer;
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;
    JLabel statusbar;
    Shape1 curPiece;
    Tetrinomes[] board;
    
    public Board1(Tetris1 parent){
    	setFocusable(true);
    	curPiece = new Shape1();
    	timer = new Timer(400, this);
    	timer.start();
    	statusbar = parent.getStatusBar();
    	board = new Tetrinomes[BoardWidth * BoardHeight];
    	addKeyListener(new TAdapter());
    	clearBoard();
    }
    
    public void start(){
    	if(isPaused) return;
    	isFallingFinished = false;
    	numLinesRemoved = 0;
    	isStarted = true;
    	timer.start();
    	clearBoard();
    	newPiece();
    }
    
    private void newPiece() {
    	curPiece.setRandomShape();
    	curX = BoardWidth / 2 - 1;
    	curY = BoardHeight - 1;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = getSize();
		int boardtop = (int) size.getHeight() - BoardHeight * squareHeight();
		for(int i = 0; i < BoardHeight; ++i){
			for(int j = 0; j < BoardWidth; ++ j){
				
			}
		}
		if(curPiece.getShape1() != Tetrinomes.NoShape){
			for(int i = 0; i < 4; ++i){
				int x = curX + curPiece.x(i);
				int y = curY + curPiece.y(i);
				drawSquare(g, 0 + x * squareWidth(),
                        boardtop + (BoardHeight - y - 1) * squareHeight(), curPiece.getShape1());
			}
		}
	}
	
    int squareWidth() { return (int) getSize().getWidth() / BoardWidth; }
    int squareHeight() { return (int) getSize().getHeight() / BoardHeight; }
    Tetrinomes shapeAt(int x, int y) { return board[(y * BoardWidth) + x]; }
	
	private void drawSquare(Graphics g, int x, int y, Tetrinomes shape) {
        Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), 
                new Color(102, 204, 102), new Color(102, 102, 204), 
                new Color(204, 204, 102), new Color(204, 102, 204), 
                new Color(102, 204, 204), new Color(218, 170, 0)
            };


            Color color = colors[shape.ordinal()];

            g.setColor(color);
            g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
            g.setColor(color.brighter());
            g.drawLine(x, y + squareHeight() - 1, x, y);
            g.drawLine(x, y, x + squareWidth() - 1, y);

            g.setColor(color.darker());
            g.drawLine(x + 1, y + squareHeight() - 1,
                             x + squareWidth() - 1, y + squareHeight() - 1);
            g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                             x + squareWidth() - 1, y + 1);

	}

	private class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT) tryMove(curPiece, curX-1, curY);
			if(key == KeyEvent.VK_RIGHT) tryMove(curPiece, curX+1, curY);
			if(key == KeyEvent.VK_DOWN) tryMove(curPiece, curX, curY-1);
		}
    	
    }
    
	private void clearBoard() {
		for(int i = 0; i < BoardWidth * BoardHeight; i++){
			board[i] = Tetrinomes.NoShape;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isFallingFinished){
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}
	
	private boolean tryMove(Shape1 newPiece, int newX, int newY){
    	for(int i = 0; i < 4; i++){
    		int x = newX + newPiece.x(i);
    		int y = newY + newPiece.y(i);
    		if(x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight){
    			//pause();
    			return false;
    		}
    		/*if(shapeAt(x,y) != Tetrinomes.NoShape){
    			return false;
    		}*/
    	}
    	curPiece = newPiece;
    	curX = newX;
    	curY = newY;
    	repaint();
    	return true;
	}
	
    public void pause(){
    	if(!isStarted){
    		return;
    	}
    	isPaused = !isPaused;
    	if(isPaused){
    		timer.stop();
    		statusbar.setText("Game paused");
    	} else {
    		timer.start();
    		statusbar.setText(String.valueOf(numLinesRemoved));
    	}
    	repaint();
    }

	private void removeFullLines() {
		int numFullLines = 0;
        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrinomes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j)
                         board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
                }
            }
        }

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            statusbar.setText(String.valueOf(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setShape(Tetrinomes.NoShape);
            repaint();
        }
	}
    
	/*
	 * I think this method puts the piece to sit where it lands (it !tryMove--> so no move available then come into 
	 * this function) --> and then it puts it into our board array that keeps track of all the spots, and whether 
	 * that spot /spots has a shape at that location--> maybe how we check bounds for other tetrinomes objects
	 */
	private void pieceDropped() {
		for(int i = 0; i < 4; i++){
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y*BoardHeight)+x] = curPiece.getShape1();
			removeFullLines();
			if(!isFallingFinished)
				newPiece();
		}
	}
	
	private void oneLineDown() {
		if(!tryMove(curPiece, curX, curY-1)) pieceDropped();
	}
	
}