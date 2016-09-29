package OwnCollision;

public class Alien extends Sprite{
	
	private final int INITIAL_X = 400;
	
	public Alien(int x, int y){
		super(x,y);
		initAlien();
	}

	private void initAlien() {
		loadImage("alien.png");
		getImageDimensions();
	}

	public void move(){
		x -= 1;
		if(x < 0) x = INITIAL_X;
	}
	
}
