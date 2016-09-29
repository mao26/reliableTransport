package Breakout;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Paddle extends Sprite implements Commons {
	private int dx;
	public Paddle(){
		ImageIcon ii = new ImageIcon("paddle.png");
		image = ii.getImage();
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		resetState();
	}
	
	public void move(){
		x += dx;
		if(x == 0) x = 0;
		if(x == WIDTH - width) x = WIDTH - width;
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT) dx = 0;
		if(key == KeyEvent.VK_RIGHT) dx = 0;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT) dx = -1;
		if(key == KeyEvent.VK_RIGHT) dx = 1;
	}
	
	private void resetState() {
		x = INIT_PADDLE_X;
		y = INIT_PADDLE_Y;
	}
}
