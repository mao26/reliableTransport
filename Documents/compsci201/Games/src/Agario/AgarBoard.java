package Agario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AgarBoard extends JPanel implements ActionListener{

	private final int INITIAL_X = 100;
	private final int INITIAL_Y = 100;
	private final int DELAY = 40;
	
	private boolean right = false;
	private boolean up = false;
	private boolean left = false;
	private boolean down = false;
	
	private int apple_x;
	private int apple_y;
	
	public Image meimage;
	public Image appleImg;
	public Me me;
	public Timer timer;
	public Ball ball;
	
	public AgarBoard()
	{
		setFocusable(true);
		addKeyListener(new TAdapter());
		initGame();
		
	}

	private void initGame() {
		loadImages();
		locateApple();
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ball = new Ball(200, 200, Color.YELLOW);
		me = new Me(INITIAL_X, INITIAL_Y);
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	private void loadImages() {
		ImageIcon ii = new ImageIcon("apple.png");
		appleImg = ii.getImage();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ball.drawCircle(g);
		g.drawImage(me.getImage(), me.getX(), me.getY(), me.width, me.height, this);
		g.drawImage(appleImg, apple_x, apple_y, this);
	}

	private void move(){
//		System.out.println("are we in here?");
		if(right) {
			me.setX(me.getX() + me.getImage().getWidth(null)/3);
		}
		if(up) me.setY(me.getY() - me.getImage().getHeight(null)/3);
		if(left) {
			me.setX(me.getX() - me.getImage().getWidth(null)/3);
		}
		if(down) me.setY(me.getY() + me.getImage().getHeight(null)/3);
	}

	private class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_RIGHT) right = true;
			if(key == KeyEvent.VK_UP) up = true;
			if(key == KeyEvent.VK_DOWN) down = true;
			if(key == KeyEvent.VK_LEFT) left = true;
//			System.out.println("I pressed the key");
//			me.KeyPressed(e);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_RIGHT) right = false;
			if(key == KeyEvent.VK_UP) up = false;
			if(key == KeyEvent.VK_DOWN) down = false;
			if(key == KeyEvent.VK_LEFT) left = false;
//			me.KeyReleased(e);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		checkApple();
		repaint();
	}

	private void checkApple() {
		if(apple_x > me.getX()-5 && apple_x < me.getX() +5 && apple_y > me.getY()-5 && apple_y < me.getY()+5){
			System.out.println(true);
			me.resizeImage(1, 1);
			locateApple();
		}
	}

	private void locateApple() {
		int r = (int) (Math.random() * 29);
		apple_x = r * 10;
		r = (int) (Math.random() * 29);
		apple_y = r * 10;
 	}
}
