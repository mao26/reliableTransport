package Agario;

import java.awt.*;
import javax.swing.*;

public class Agario extends JFrame{

	public Agario(){
		initAgario();
	}

	private void initAgario() {
		add(new AgarBoard());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setTitle("Agar.io");
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				Agario agar = new Agario();
				agar.setVisible(true);				
			}
			
		});
	}
	
}
