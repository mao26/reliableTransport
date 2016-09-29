package Puzzle;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MyButton extends JButton {
	
	private boolean isLastButton;
	
	public MyButton(){
		super();
		initUI();
	}
	
	public MyButton(Image image){
		super(new ImageIcon(image));
		initUI();
	}

	private void initUI() {
		isLastButton = false;
		BorderFactory.createLineBorder(Color.GRAY);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	public void setLastButton(){
		isLastButton = true;
	}
	
	public boolean isLastButton(){
		return isLastButton;
	}
	
}

public class PuzzleEx extends JFrame {

    private JPanel panel;
    private BufferedImage source;
    private ArrayList<MyButton> buttons;

    ArrayList<Point> solution = new ArrayList();

    private Image image;
    private MyButton lastButton;
    private int width, height;
    private final int DESIRED_WIDTH = 300;
    private BufferedImage resized;
	
	public PuzzleEx(){
		initBoard();
	}

	private void initBoard() {
        solution.add(new Point(0, 0));
        solution.add(new Point(0, 1));
        solution.add(new Point(0, 2));
        solution.add(new Point(1, 0));
        solution.add(new Point(1, 1));
        solution.add(new Point(1, 2));
        solution.add(new Point(2, 0));
        solution.add(new Point(2, 1));
        solution.add(new Point(2, 2));
        solution.add(new Point(3, 0));
        solution.add(new Point(3, 1));
        solution.add(new Point(3, 2));
        
        buttons = new ArrayList<MyButton>();
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setLayout(new GridLayout(4, 3, 0, 0));
        
        try{
        	source = loadImage();
        	int h = getNewHeight(source.getWidth(), source.getHeight());
        	//resized = (BufferedImage) source.getScaledInstance(DESIRED_WIDTH, h, Image.SCALE_SMOOTH);
        	resized = resizeImage(source, DESIRED_WIDTH, h, BufferedImage.TYPE_INT_ARGB);
        	
        } catch(IOException e){
        	Logger.getLogger(PuzzleEx.class.getName()).log(Level.SEVERE, null, e);
        }
        
        height = resized.getHeight(null);
        width = resized.getWidth(null);
        
        add(panel, BorderLayout.CENTER);
        
        for(int j = 0; j < 3; j++){
        	for(int i = 0; i < 4; i++){
        		image = createImage(new FilteredImageSource(resized.getSource(), new CropImageFilter(j * width / 3, i * height / 4,
                        (width / 3), height / 4)));
        		MyButton button = new MyButton(image);
        		button.putClientProperty("position", new Point(i, j));
        		if(i == 2 && j == 3){
        			lastButton = new MyButton();
        			lastButton.isLastButton();
        			lastButton.setBorderPainted(false);
        			lastButton.setContentAreaFilled(false);
        			lastButton.putClientProperty("postion", new Point(i, j));
        		} else {
        			buttons.add(button);
        		}
        	}
        }
        Collections.shuffle(buttons);
        buttons.add(lastButton);
        for(int i = 0; i < 12; i++){
        	MyButton btn = buttons.get(i);
        	panel.add(btn);
        	btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        	btn.addActionListener(new CreateAction());
        }
        
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Puzzle of puppy");
        setLocationRelativeTo(null);
	}

	private int getNewHeight(int width2, int height2) {
		double ratio = DESIRED_WIDTH / (double) width2;
		int newHeight = (int) (height2 * ratio);
		return newHeight;
	}

	private BufferedImage resizeImage(BufferedImage original, int width, int height, int type){
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(original, 0, 0, width, height, null);
		g2d.dispose();
		return resizedImage;
	}
	
	private class CreateAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			checkButton(e);
			checkSolution();
		}
		
		private void checkButton(ActionEvent e){
			int lidx = 0;
			for(MyButton btn: buttons){
				if(btn.isLastButton()){
					lidx = buttons.indexOf(btn);
				}
			}
			JButton button = (JButton) e.getSource();
			int bdix = buttons.indexOf(button);
			if((bdix - 1 == lidx) || bdix + 1 == lidx || bdix + 3 == lidx || bdix -3 == lidx){
				//they are adjacent
				Collections.swap(buttons, bdix, lidx);
				updateButtons();
			}
		}
		private void updateButtons(){
			panel.removeAll();
			for(JComponent btn : buttons){
				panel.add(btn);
			}
			panel.validate();
		}
	}
	
	private void checkSolution(){
		ArrayList<Point> current = new ArrayList<Point>();
		for(JComponent button : buttons){
			current.add((Point) button.getClientProperty("position"));
		}
		if(compareList(solution, current)){
			JOptionPane.showMessageDialog(panel, "Finished", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public static boolean compareList(List lst1, List lst2) {
		return lst1.toString().contentEquals(lst2.toString());
	}

	private BufferedImage loadImage() throws IOException {
		BufferedImage bimg = ImageIO.read(new File("https://s-media-cache-ak0.pinimg.com/236x/e2/7d/c8/e27dc8743e36af1aa0b2021616b0511c.jpg"));
		return bimg;
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				PuzzleEx ex = new PuzzleEx();
				ex.setVisible(true);
			}
		});
	}
    
}
