package Basics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImgBoard extends JPanel {

	private Image bardejov;
	private BufferedImage bardejob;
	
	public ImgBoard(){
		initBoard();
	}

	private void initBoard() {
		loadImage();
		int w = bardejov.getWidth(this);
		int h = bardejov.getHeight(this);
		setPreferredSize(new Dimension(w, h));
	}

	private void loadImage() {
		ImageIcon ii = new ImageIcon("star.png");
		bardejov = ii.getImage();
		try {
			BufferedImage img = ImageIO.read(new File("star.png"));
			bardejob = img;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {

        g.drawImage(bardejov, 0, 0, null);
    }
	
}
