package OwnCollision;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {
	
    private Timer timer;
    private Craft craft;
    private ArrayList<Alien> aliens;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;
    
    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
        };
    
    public Board(){
    	initBoard();
    }
    
	private void initBoard() {
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setBackground(Color.BLACK);
		initAliens();
		craft = new Craft(ICRAFT_X, ICRAFT_Y);
		timer = new Timer(DELAY, this);
		timer.start();
		addKeyListener(new TAdapter());
		setFocusable(true);
		ingame = true;
	}

	private void initAliens() {
		aliens = new ArrayList<Alien>();
		for(int[] p: pos){
			aliens.add(new Alien(p[0], p[1]));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(ingame) drawObjects(g);
		else drawGameOver(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawObjects(Graphics g) {
		if(craft.isVisible()) g.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
		ArrayList<Missile> missiles = craft.getMissiles();
		for(Missile m: missiles){
			if(m.isVisible()) g.drawImage(m.getImage(), m.getX(), m.getY(), this);
		}
		for(Alien alien: aliens){
			if(alien.isVisible()) g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
		}
	}

	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg,  (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();
		updateCraft();
		updateMissiles();
		updateAliens();
		checkBounds();
		repaint();
	}

	private void checkBounds() {
		Rectangle r1 = craft.getBounds();
		ArrayList<Missile> missiles = craft.getMissiles();
		for(Missile m: missiles){
			Rectangle r2 = m.getBounds();
			for(Alien a: aliens){
				Rectangle r3 = a.getBounds();
				if(r3.intersects(r2)){
					a.setVisible(false);
					m.setVisible(false);
				}
			}
		}
		for(Alien a: aliens){
			Rectangle r3 = a.getBounds();
			if(r3.intersects(r1)){
				craft.setVisible(false);
				a.setVisible(false);
				ingame = false;
			}
		}
	}

	private void updateAliens() {
		for(int i = 0; i < aliens.size(); i++){
			Alien alien = aliens.get(i);
			if(alien.isVisible()){
				alien.move();
			} else {
				aliens.remove(alien);
			}
		}
	}

	private void updateMissiles() {
		ArrayList<Missile> missiles = craft.getMissiles();
		for(int i = 0; i < missiles.size(); i++){
			Missile m = missiles.get(i);
			if(m.isVisible()){
				m.move();
			} else {
				missiles.remove(i);
			}
		}
	}

	private void updateCraft() {
		if(craft.isVisible()) craft.move();
	}

	private void inGame() {
		if(!ingame) timer.stop();
	}

	class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			craft.KeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			craft.KeyReleased(e);
		}
		
	}
	
}
