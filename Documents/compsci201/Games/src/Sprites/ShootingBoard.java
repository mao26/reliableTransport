package Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ShootingBoard extends JPanel implements ActionListener{

    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private ShootingCraft craft;
	
    public ShootingBoard() {
    	initBoard();
    }
    
	private void initBoard() {
		craft = new ShootingCraft(ICRAFT_X, ICRAFT_Y);
		timer = new Timer(DELAY, this);
		timer.start();
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		addKeyListener(new TAdapter());
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateMissile();
		updateCraft();
		repaint();
	}

	private void updateMissile() {
		ArrayList<ShootingMissile> ms = craft.getMissiles();
		for(int i = 0; i < ms.size(); i++){
			ShootingMissile m = (ShootingMissile) ms.get(i);
			if(m.isVisible()){
				m.move();
			} else {
				ms.remove(i);
			}
		}
	}

	private void updateCraft() {
		craft.move();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawBoard(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
		ArrayList<ShootingMissile> ms = craft.getMissiles();
		for(Object m1: ms){
			ShootingMissile m2 = (ShootingMissile) m1;
			g2d.drawImage(m2.getImage(), m2.getX(), m2.getY(), this);
		}
	}

	
	
}
