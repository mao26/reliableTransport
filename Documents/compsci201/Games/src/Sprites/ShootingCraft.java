package Sprites;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ShootingCraft extends ShootingSprite{

	private int dx;
    private int dy;
    private ArrayList<ShootingMissile> missiles;
	
	public ShootingCraft(int x, int y) {
		super(x, y);
		initCraft();
	}

	private void initCraft() {
		loadImage("craft.png");
		getImageDimensions();
		missiles = new ArrayList<>();
	}
	
	public void move(){
		x += dx;
		y += dy;
	}
	
	public ArrayList<ShootingMissile> getMissiles(){
		return missiles;
	}
	
	public void fire(){
		missiles.add(new ShootingMissile(x + width, y + height/2));
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE) fire();
		if(key == KeyEvent.VK_LEFT){
			dx = -1;
		}
		if(key == KeyEvent.VK_RIGHT){
			dx = 1;
		}
		if(key == KeyEvent.VK_UP) dy = 1;
		if(key == KeyEvent.VK_DOWN) dy = -1;
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP) dy = 0;
		if(key == KeyEvent.VK_DOWN) dy = 0;
		if(key == KeyEvent.VK_RIGHT) dx = 0;
		if(key == KeyEvent.VK_LEFT) dx = 0;
	}

}
