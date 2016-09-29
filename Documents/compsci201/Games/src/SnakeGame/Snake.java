package SnakeGame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {
	
	public Snake(){
		initUI();
	}
	
	private void initUI() {
		add(new Board());
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		pack();
	}

	public static void main(String[]args){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Snake snake = new Snake();
				snake.setVisible(true);
			}
		});
	}
	
}
