package Collision;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;
	
    public Sprite(int x, int y){
    	this.x = x;
    	this.y = y;
    	vis = true;
    }
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }
    
    protected void loadImage(String image1){
    	ImageIcon ii = new ImageIcon(image1);
    	image = ii.getImage();
    }
    
    public Image getImage(){
    	return image;
    }
    
    protected void getImageDimensions(){
    	width = image.getWidth(null);
    	height = image.getHeight(null);
    }
    
    public boolean isVisible(){
    	return vis;
    }
    
    public void setVisible(boolean visible){
    	vis = visible;
    }
    
    public Rectangle getBounds(){
    	return new Rectangle(x, y, width, height);
    }
    
}
