import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public DrawingPanel() {
		this.setBackground(Color.white);
		
		MouseHandler mouseHandler = new MouseHandler();
		// button
		this.addMouseListener(mouseHandler);
		// position
		this.addMouseMotionListener(mouseHandler);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
	}
	
	private class MouseHandler implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked (X, Y): (" + e.getX() + ", " + e.getY() + ")");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed (X, Y): (" + e.getX() + ", " + e.getY() + ")");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased (X, Y): (" + e.getX() + ", " + e.getY() + ")"); 
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered (X, Y): (" + e.getX() + ", " + e.getY() + ")"); 
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited (X, Y): (" + e.getX() + ", " + e.getY() + ")"); 
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("mouseDragged (X, Y): (" + e.getX() + ", " + e.getY() + ")"); 
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouseMoved (X, Y): (" + e.getX() + ", " + e.getY() + ")");
		}
		
	}
}
