package Sprites;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class ShootingMissilesEx extends JFrame {

	public ShootingMissilesEx(){
		initUI();
	}
	
	private void initUI() {
		setSize(400, 300);
		setResizable(false);
		setTitle("Shooting Missiles");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new ShootingBoard());
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				ShootingMissilesEx ex = new ShootingMissilesEx();
				ex.setVisible(true);
			}
			
		});
	}
	
}
