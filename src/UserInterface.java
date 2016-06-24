import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	static int x = 0 , y = 0;
	
	public void paintComponent(Graphics g){

		this.setBackground(Color.GREEN);
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 20, 20);
		g.setColor(Color.RED);
		g.fillRect(20, 20, 200, 200);
		g.drawString("Vikrant Goel", 250, 250);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		Image chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		g.drawImage(chessPieceImage, x, y, x+64, y+64, 0+64, 0, 64+64, 64, this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
