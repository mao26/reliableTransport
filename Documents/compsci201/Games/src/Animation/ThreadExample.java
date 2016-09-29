package Animation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;

public class ThreadExample extends JFrame {

	public ThreadExample(){
		initUI();
	}
	
	private void initUI() {
		
		add(new BoardThread());
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setTitle("Star");
		
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				JFrame te = new ThreadExample();
				te.setVisible(true);
				te.getContentPane().add(new Label(), BorderLayout.CENTER);
			}
			
		});
	}
	
}
