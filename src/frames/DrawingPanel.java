package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import shapes.Line;
import shapes.Oval;
import shapes.Polygon;
import shapes.Rectangle;
import shapes.Shape;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public enum ETools {
		eRectangle,
		eOval,
		eLine,
		ePolygon
	}
	
	private ETools eSelectedTool;
	
	public DrawingPanel() {
		this.setBackground(Color.WHITE);
		
		MouseHandler mouseHandler = new MouseHandler();
		
		// button
		this.addMouseListener(mouseHandler);
		// position
		this.addMouseMotionListener(mouseHandler);
		// wheel
//		this.addMouseWheelListener(mouseHandler);
	}
	
	public void setSelectedTool(ETools eSelectedTool) {
		this.eSelectedTool = eSelectedTool;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	Shape shape;
	
	private void prepareDrawing(int x, int y) {
		if (this.eSelectedTool == ETools.eRectangle) {
			this.shape = new Rectangle(x, y);
		}
		else if (this.eSelectedTool == ETools.eOval) {
			this.shape = new Oval(x, y);
		}
		else if (this.eSelectedTool == ETools.eLine) {
			this.shape = new Line(x, y);	
		}
		else if (this.eSelectedTool == ETools.ePolygon) {
			this.shape = new Polygon(x, y);
		}
		
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		
		this.shape.draw(graphics2D);
	}
	
	private void keepDrawing(int x, int y) {
		
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(getBackground());
		
		// erase
		this.shape.draw(graphics2D);
		
		// draw
		this.shape.resize(x, y);
		this.shape.draw(graphics2D);
		
	}
	
	private void finishDrawing(int x, int y) {
		
	}
	
	class MouseHandler implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			keepDrawing(e.getX(), e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			prepareDrawing(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			finishDrawing(e.getX(), e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
		
	}

}
