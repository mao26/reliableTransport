package Animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BoardThread extends JPanel implements Runnable {

    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private Image star;
    private Thread animator;
    private int x, y;
	
    public BoardThread(){
    	initBoard();
    }
    
	private void initBoard() {
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		/*
		 * 
		 * IMPORTANT!!!!!!!
		 * CREATES THE PICTURE ON MEMORY FIRST, THEN PAINTS IT ON THE BOARD
		 */
		setDoubleBuffered(true);
		setBackground(Color.RED);
		
		x = INITIAL_X;
		y = INITIAL_Y;
		
		loadImage();
	}

	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawStar(g);
	}

	private void drawStar(Graphics g) {
		g.drawImage(star, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}

	private void loadImage() {
		ImageIcon ii = new ImageIcon("star.png");
		star = ii.getImage();
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		while(true){
			cycle();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if(sleep < 0){
            	sleep = 2;
            }
            try{
            	Thread.sleep(sleep);
            } catch(InterruptedException e){
            	System.out.println("Interrupted " + e.getMessage());
            }
            beforeTime = System.currentTimeMillis();
		}
	}

	private void cycle() {
		x+=10;
		y+=10;
		if(y > B_HEIGHT){
			x = INITIAL_X;
			y = INITIAL_Y;
		}
	}

}
