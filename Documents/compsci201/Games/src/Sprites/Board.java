package Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

    private Timer timer;
    private Craft craft;
    private final int DELAY = 10;
    
    public Board(){
    	initBoard();
    }
    
	private void initBoard() {
		craft = new Craft();
		timer = new Timer(DELAY, this);
		timer.start();
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		craft.move();
		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doPainting(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void doPainting(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
		
	}

	private class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			craft.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			craft.keyReleased(e);
		}
		
	}
	
}
