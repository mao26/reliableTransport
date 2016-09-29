package Breakout;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel implements Commons, ActionListener{
	
	private Paddle paddle;
	private Timer timer;
	private Brick[] bricks;
	private boolean ingame = true;
	private Ball ball;
	
	public Board(){
		initBoard();
	}

	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		bricks = new Brick[N_OF_BRICKS];
		paddle = new Paddle();
		timer = new Timer();
		setDoubleBuffered(true);
		timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
	}
	
	private class ScheduleTask extends TimerTask {
		@Override
		public void run() {
			ball.move();
			paddle.move();
			checkCollision();
			repaint();
		}

	}
	
	private void checkCollision() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(ingame) drawObjects(g2d);
		else drawGameOver(g2d);
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawGameOver(Graphics2D g2d) {
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);
	}

	private void drawObjects(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	private void gameInit() {
		// TODO Auto-generated method stub
		ball = new Ball();
		paddle = new Paddle();
	}

	private class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
}
