package NewTetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris1 extends JFrame {

	private JLabel statusbar;

	public Tetris1() {
		initUI();
	}

	private void initUI() {
		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);
		Board1 board = new Board1(this);
		add(board);
		board.start();
		
		setSize(200, 400);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JLabel getStatusBar(){
		return statusbar;
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Tetris1 tetris = new Tetris1();
				tetris.setLocationRelativeTo(null);
				tetris.setVisible(true);
			}
		});
		
	}

}