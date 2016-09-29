package Sprites;

public class ShootingMissile extends ShootingSprite {
	
	private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 2;

	public ShootingMissile(int x, int y) {
		super(x, y);
		initMissile();
	}

	private void initMissile() {
		loadImage("missile.png");
		getImageDimensions();
	}
	
	public void move(){
		x+=MISSILE_SPEED;
		if(x > BOARD_WIDTH){
			vis = false;
		}
	}

}
