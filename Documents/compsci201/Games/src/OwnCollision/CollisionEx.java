package OwnCollision;

import java.awt.*;
import javax.swing.JFrame;

public class CollisionEx extends JFrame{

	public CollisionEx(){
		initUI();
	}
	
	private void initUI() {
		add(new Board());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setTitle("Let's do this for my family");
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				CollisionEx ex = new CollisionEx();
				ex.setVisible(true);
			}
			
		});
	}
	
}
