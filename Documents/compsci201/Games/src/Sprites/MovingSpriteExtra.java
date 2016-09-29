package Sprites;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class MovingSpriteExtra extends JFrame{

	public MovingSpriteExtra(){
		initUI();
	}

	private void initUI() {
		add(new Board());
		setSize(400, 300);
		setResizable(false);
		setTitle("Moving Spaceship Sprite");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MovingSpriteExtra ex = new MovingSpriteExtra();
				ex.setVisible(true);
			}
		});
	}
	
}
