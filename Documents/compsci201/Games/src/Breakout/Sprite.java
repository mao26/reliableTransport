package Breakout;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

	public Image image;
	public int x;
	public int y;
	public int width;
	public int height;
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public Image getImage(){
		return image;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
}
