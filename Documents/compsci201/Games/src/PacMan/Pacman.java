package PacMan;

import java.awt.*;
import javax.swing.JFrame;

public class Pacman extends JFrame{

	public Pacman(){
		initGame();
	}
	
	private void initGame() {
		add(new Board());
	}

	public static void main(String[]args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				Pacman pacman = new Pacman();
				pacman.setVisible(true);
			}
			
		});
	}
	
}
