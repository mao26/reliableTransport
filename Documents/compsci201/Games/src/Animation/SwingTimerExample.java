package Animation;

import java.awt.EventQueue;
import javax.swing.JFrame;

import Basics.Board;

public class SwingTimerExample extends JFrame {

	public SwingTimerExample(){
		
		initUI();
		
	}

	private void initUI() {
		
		add(new BoardSwing());
		setResizable(false);
		pack();
		
		setTitle("Star");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame ex = new SwingTimerExample();
				ex.setVisible(true);
			}
		});
	}
	
}
