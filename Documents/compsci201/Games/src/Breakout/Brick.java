package Breakout;

import javax.swing.ImageIcon;

public class Brick extends Sprite {

	public boolean destroyed;
	
	public Brick(int x, int y){
		this.x = x;
		this.y = y;
		
		ImageIcon ii = new ImageIcon("brickie.png");
		image = ii.getImage();
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		destroyed = false;
	}
	
	public boolean isDestroyed(){
		return destroyed;
	}
	
	public void setDestroyed(boolean val){
		destroyed = val;
	}
	
}
