package Agario;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Me {

	private Image meimage;
	private int x;
	private int y;
	private int dx;
	private int dy;
	public int height;
	public int width;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	
	public Me(int x, int y){
		this.x = x;
		this.y = y;
		ImageIcon ii = new ImageIcon("head.png");
		meimage = ii.getImage();
		height = meimage.getHeight(null);
		width = meimage.getWidth(null);
	}
	
	public Image getImage(){
		return meimage;
	}
	
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
	
	public void move(){
		if(right) x += dx;
		if(left) x -= dx;
		if(up) y += dy;
		if(down) y += dy;
	}
	
	public void KeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT) right = true; dx += 1;
		if(key == KeyEvent.VK_UP) up = true;
		move();
	}
	
	public void KeyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT) right = false;
		if(key == KeyEvent.VK_UP) up = false;
	}

	public void resizeImage(int incx, int incy) {
		height += incy;
		width += incx;
	}
	
}
