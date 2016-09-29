package Breakout;

import javax.swing.ImageIcon;

public class Ball extends Sprite implements Commons {

	private int xdir;
	private int ydir;
	
	public Ball(){
		int xdir = 1;
		int ydir = -1;
		
		ImageIcon ii = new ImageIcon("ball.png");
		image = ii.getImage();
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		resetState();
	}

	public void move(){
		x += xdir;
		y += ydir;
		
		if(x == 0){
			setXDir(1);
		}
		
		if(x == WIDTH - width){
			setXDir(-1);
		}
		
		if(y == 0){
			setYDir(1);
		}
	}
	
	private void resetState() {
		x = INIT_BALL_X;
		y = INIT_BALL_Y;
	}
	
	private void setXDir(int x){
		xdir = x;
	}
	
	private void setYDir(int y){
		ydir = y;
	}
	
	private int getYDir(){
		return ydir;
	}
	
}
