import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class DrawingPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public enum EShapes {
		eRectangle,
		eOval,
		eLine,
		ePolygon
	}
	
	EShapes eShape;
	
	public DrawingPanel() {
		
		this.setBackground(Color.WHITE);
		
		MouseHandler mouseHandler = new MouseHandler();
		
		// button
		this.addMouseListener(mouseHandler);
		
		// position
		this.addMouseMotionListener(mouseHandler);
		
		// wheel
//		this.addMouseWheelListener(mouseHanlder);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	Shape shape;
	
	private void prepareDrawing(int x, int y) {
		
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		eShape = EShapes.eOval;
		
		if (eShape == EShapes.eRectangle) {
			this.shape = new Rectangle(x, y);
		}
		else if (eShape == EShapes.eOval) {
			this.shape = new Oval(x, y);
		}
		
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
	
	abstract class Shape {
		abstract public void resize(int x, int y);
		abstract public void draw(Graphics2D graphics);
	}
	
	class Rectangle extends Shape {
		
		private int x, y, width, height;
		
		public Rectangle(int x, int y) {
			this.x = x;
			this.y = y;
			this.width = 0;
			this.height = 0;
		}
		
		@Override
		public void resize(int x, int y) {
			this.width = x - this.x;
			this.height = y - this.y;
		}
		
		@Override
		public void draw(Graphics2D graphics) {
			graphics.drawRect(this.x, this.y, this.width, this.height);
		}
		
	}
	
	class Oval extends Shape {
		
		private int x, y, width, height;
		
		public Oval(int x, int y) {
			this.x = x;
			this.y = y;
			this.width = 0;
			this.height = 0;
		}

		@Override
		public void resize(int x, int y) {
			this.width = x - this.x;
			this.height = y - this.y;
		}

		@Override
		public void draw(Graphics2D graphics) {
			graphics.drawOval(this.x, this.y, this.width, this.height);
			
		}
		
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
