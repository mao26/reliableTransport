package Basics;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame{

	public Application(){
		initUI();
	}

	private void initUI() {
		//Here we put the Board to the center of the JFrame container.
		add(new Board());
		//This line sets the size of the window.
		setSize(250,200);
		setTitle("Application");
		//This will close the application when we click on the close button. It is not the default behaviour.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Passing null to the setLocationRelativeTo() method centers the window on the screen.
		setLocationRelativeTo(null);
	}

	public static void main(String[] args){
		//We create an instance of our code example and make it visible on the screen.
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Application ex = new Application();
				ex.setVisible(true);
			}
		});
	}
	
}
