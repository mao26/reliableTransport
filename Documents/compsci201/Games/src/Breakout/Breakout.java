package Breakout;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Breakout extends JFrame {
	
	public Breakout(){
		initGame();
	}
	
	private void initGame() {
		add(new Board());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Breakout");
		pack();
		//setPreferredSize(new Dimension(400, 400));
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				Breakout bo = new Breakout();
				bo.setVisible(true);
			}
			
		});
	}
}
