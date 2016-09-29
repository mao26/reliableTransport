package Agario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Ball extends JPanel{
	
	private int centerX;
	private int centerY;
	private int radius;
	private Color myColor;
	
	public Ball(int x, int y, Color color){
		this.centerX = x;
		this.centerY = y;
		this.radius = 50;
		this.myColor = color;
	}

	public void drawCircle(Graphics g){
        int diameter = radius * 2;

        int x = centerX - radius;
        int y = centerY - radius;

        g.setColor(Color.WHITE);
        g.drawRect(x, y, diameter, diameter);
        g.drawLine(x, y, x + diameter, y + diameter);
        g.drawLine(x + diameter, y, x, y + diameter);

        g.setColor(myColor);
        g.drawOval(x, y, diameter, diameter);

        g.fillOval(centerX - 5, centerY - 5, 10, 10);

	}
	
	
}
