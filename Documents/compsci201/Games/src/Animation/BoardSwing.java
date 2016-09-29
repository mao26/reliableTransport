package Animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardSwing extends JPanel implements ActionListener {

	private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private Image star;
    private Timer timer;
    private int x, y;
	
    public BoardSwing(){
    	initBoardSwing();
    }
    
	private void initBoardSwing() {
		
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
		loadImage();
		
		x = INITIAL_X;
		y = INITIAL_Y;
		
		timer = new Timer(DELAY, this);
		timer.start();
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawStar(g);
	}

	private void drawStar(Graphics g) {
		g.drawImage(star, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}

	private void loadImage() {
		ImageIcon ii = new ImageIcon("star.png");
		star = ii.getImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		x += 1;
		y += 1;
		
		if(y > B_HEIGHT)
		{
			
			y = INITIAL_Y;
			x = INITIAL_X;
			
		}
		
		repaint();
		
	}

	
	
}
