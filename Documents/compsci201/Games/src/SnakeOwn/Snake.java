package SnakeOwn;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame{

	public Snake(){
		initUI();
	}
	
	private void initUI() {
		add(new Board());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Snake 2.0");
		setLocationRelativeTo(null);
		pack();
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {
				Snake snake = new Snake();
				snake.setVisible(true);
			}
		});
	}
}
