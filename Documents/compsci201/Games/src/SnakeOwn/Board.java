package SnakeOwn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {

	private final int B_WIDTH = 300;
	private final int B_HEIGHT = 300;
	private final int DELAY = 100;
	private final int RAND_POS = 29;
	private final int ALL_DOTS = 900;
	private final int DOT_SIZE = 10;
	
	private Timer timer;
	
	private Image head;
	private Image apple;
	private Image ball;
	
	private boolean rightDirection = true;
	private boolean leftDirection = false;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean ingame = false;
	
	private int dots;
	private int apple_x;
	private int apple_y;
	private int[] x = new int[ALL_DOTS];
	private int[] y = new int[ALL_DOTS];
	
	public Board(){
		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setFocusable(true);
		setDoubleBuffered(true);
		loadImages();
		initGame();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(ingame) drawObjects(g);
		else drawGameOver(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvitica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg,(B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	private void drawObjects(Graphics g) {
		g.drawImage(apple, apple_x, apple_y, this);
		for(int z = 0; z < dots; z++){
			if(z == 0){
				g.drawImage(head, x[z], y[z], DOT_SIZE, DOT_SIZE, this);
			} else {
				g.drawImage(ball, x[z], y[z], DOT_SIZE, DOT_SIZE, this);
			}
		}
	}

	private class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_UP && (!downDirection)){
				rightDirection = false;
				upDirection = true;
				leftDirection = false;
			}
			if(key == KeyEvent.VK_DOWN && (!upDirection)){
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			if(key == KeyEvent.VK_LEFT && (!rightDirection)){
				downDirection = false;
				upDirection = false;
				leftDirection = true;
			}
			if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
				downDirection = false;
				upDirection = false;
				rightDirection = true;
			}
		}
		
	}
	
	private void initGame() {
		dots = 3;
		for(int i = 0; i < dots; i++){
			x[i] = 50 - i * DOT_SIZE;
			y[i] = 50;
		}
		locateApple();
		timer = new Timer(DELAY, this);
		timer.start();
		ingame = true;
	}

	private void locateApple() {
		int r = (int) (RAND_POS * Math.random());
		apple_x = r * DOT_SIZE;
		r = (int) (RAND_POS * Math.random());
		apple_y = r * DOT_SIZE;
	}

	private void loadImages() {
		head = new ImageIcon("head.png").getImage();
		apple = new ImageIcon("apple.png").getImage();
		ball = new ImageIcon("dot.png").getImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(ingame){
			checkBounds();
			checkApple();
			move();
		}
		repaint();
	}

	private void checkApple() {
		if(x[0] == apple_x && y[0] == apple_y){
			dots++;
			locateApple();
		}
	}

	private void checkBounds() {
		for(int z = 1; z < dots; z++){
			if(x[0] == x[z] && y[0] == y[z]){
				ingame = false;
			}
		}
        if (y[0] >= B_HEIGHT) {
        	y[0] = 0;
//            inGame = false;
        }
        if (y[0] < 0) {
        	y[0] = B_HEIGHT;
//            inGame = false;
        }
        if (x[0] >= B_WIDTH) {
        	x[0] = 0;
//            inGame = false;
        }
        if (x[0] < 0) {
        	x[0] = B_WIDTH;
            //inGame = false;
        }
        if(!ingame) {
            timer.stop();
        }		
	}

	private void move() {
		for(int z = dots; z > 0; z--){
			x[z] = x[z-1];
			y[z] = y[z-1];
		}
		if(rightDirection){
			x[0] += DOT_SIZE;
		}
		if(leftDirection){
			x[0] -= DOT_SIZE;
		}
		if(upDirection){
			y[0] -= DOT_SIZE;
		}
		if(downDirection){
			y[0] += DOT_SIZE;
		}
	}

}
