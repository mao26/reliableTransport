package Basics;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform; 
import java.awt.geom.Ellipse2D;

//The Board is a panel where the game takes place.
public class Board extends JPanel{

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		paintDonut(g);
	}
	//public Board(){};
	
	private void paintDonut(Graphics g){
		
		Graphics2D g2g = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2g.setRenderingHints(rh);
		Dimension size = getSize();
		double w = size.getWidth();
		double h = size.getHeight();
		Ellipse2D e2d = new Ellipse2D.Double(0, 0, 80, 130);
		g2g.setStroke(new BasicStroke(1));
		g2g.setColor(Color.GRAY);
		
		for(double degree = 0; degree < 360; degree += 5){
			
			AffineTransform at = AffineTransform.getTranslateInstance(w/2, h/2);
			at.rotate(Math.toRadians(degree));
			g2g.draw(at.createTransformedShape(e2d));
			
		}
	}
}
