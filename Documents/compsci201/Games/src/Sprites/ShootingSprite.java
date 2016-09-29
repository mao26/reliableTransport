package Sprites;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ShootingSprite {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean vis;
	protected Image image;

	public ShootingSprite(int x, int y) {

		this.x = x;
		this.y = y;
		vis = true;

	}

	public void loadImage(String imageName) {

		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void getImageDimensions(){
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public boolean isVisible(){
		return vis;
	}
	
	public void setVisible(boolean visible){
		this.vis = visible;
	}
	
}
