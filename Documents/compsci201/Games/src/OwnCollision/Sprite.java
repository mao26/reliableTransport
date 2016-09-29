package OwnCollision;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {

	protected int x;
	protected int y;
	protected boolean vis;
	protected Image image;
	protected int width;
	protected int height;
	
	public Sprite(int x, int y){
		this.x = x;
		this.y = y;
		this.vis = true;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void loadImage(String image1){
		ImageIcon ii = new ImageIcon(image1);
		image = ii.getImage();
	}
	
	public void getImageDimensions(){
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public boolean isVisible(){
		return vis;
	}
	
	public void setVisible(boolean maybe){
		this.vis = maybe;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
}
