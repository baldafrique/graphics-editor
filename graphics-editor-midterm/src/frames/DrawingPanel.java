package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants.ETools;
import shapes.TShape;

public class DrawingPanel extends JPanel {
	// attribute
	private static final long serialVersionUID = 1L;
	private boolean updated;
	private boolean filled;
	
	// components
	private Vector<TShape> shapes;
	private TShape pasteShape;
	private Stack<TShape> buffer;
	
	public enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing,
		eErasing,
		eMoving,
		eCut,
		ePaste,
		eCopy,
		eResizing;
	}
	
	private ETools selectedTool;
	private EDrawingState eDrawingState;
	private TShape currentShape;
	private Color currentColor;
	
	public DrawingPanel() {
		// attributes
		this.eDrawingState = EDrawingState.eIdle;
		this.setBackground(Color.WHITE);
		this.updated = false;
		this.filled = false;

		// components
		this.shapes = new Vector<>();
		MouseHandler mouseHandler = new MouseHandler();
		// button
		this.addMouseListener(mouseHandler);
		// position
		this.addMouseMotionListener(mouseHandler);
		// wheel
		this.addMouseWheelListener(mouseHandler);
		
		this.buffer = new Stack<>();
	}
	
	public boolean isUpdated() {
		return this.updated;
	}
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public void setDrawingState(EDrawingState eDrawingState) {
		this.eDrawingState = eDrawingState;
	}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
	}
	
	public Object getShapes() {
		return this.shapes;
	}
	
	@SuppressWarnings("rawtypes")
	public Stack getBuffer() {
		return this.buffer;
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;
	}
	
	public void toggle() {
		this.filled = !this.filled;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (TShape shape : this.shapes) {
			shape.draw((Graphics2D) g);
		}
	}
	
	private void prepareDrawing(int x, int y) {
		this.currentShape = this.selectedTool.newShape();
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		graphics2D.setColor(currentColor);
		this.currentShape.setOrigin(x, y);
		
		if (filled) {
			this.currentShape.fill(graphics2D);	
		}
		else {
			this.currentShape.draw(graphics2D);
		}
	}
	
	private void keepDrawing(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(getBackground());
		graphics2D.setColor(currentColor);
		
		if (filled) {
			this.currentShape.fill(graphics2D);
			this.currentShape.resize(x, y);
			this.currentShape.fill(graphics2D);
		}
		else {
			this.currentShape.draw(graphics2D);
			this.currentShape.resize(x, y);
			this.currentShape.draw(graphics2D);
		}
	}
	
	private void continueDrawing(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finishDrawing(int x, int y) {
		this.shapes.add(this.currentShape);
		this.setUpdated(true);
	}
	
	private void moveShape(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(getBackground());
		graphics2D.setColor(currentColor);
		
		this.currentShape.draw(graphics2D);
		this.currentShape.moveShape(x, y);
		this.currentShape.draw(graphics2D);
	}

	private boolean onShape(int x, int y) {
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		if (this.onShape(x, y)) {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}
		this.setCursor(cursor);
	}
	
	private void cut(int x, int y) {
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				int index = this.shapes.indexOf(shape);
				this.buffer.push(this.shapes.get(index));
				this.shapes.remove(index);
				this.repaint();
				break;
			}
		}
	}
	
	private void copy(int x, int y) {
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				int index = this.shapes.indexOf(shape);
				this.buffer.push(this.shapes.get(index));
				this.repaint();
				break;
			}
		}
	}
	
	private void paste(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(getBackground());
		graphics2D.setColor(currentColor);
		
		this.pasteShape = this.buffer.peek();
		this.pasteShape.moveShape(x, y);
		this.pasteShape.draw(graphics2D);
	}
	
	public void setColor(Color color) {
		this.currentColor = color;
	}
	
	public void capture() {
        String fileName = "capture";
        String fileExtension = "png";
        
        try {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(550, 700);
            rectangle.setLocation(445, 82);
            BufferedImage image = robot.createScreenCapture(rectangle);
            image.setRGB(0,0,100);
            
            File file = new File(fileName+"."+fileExtension);
            ImageIO.write(image, fileExtension, file);
        } catch (Exception e){
            e.printStackTrace();
        }
	}
	
	public void erase(int x, int y) {
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				int index = this.shapes.indexOf(shape);
				this.buffer.push(this.shapes.get(index));
				this.shapes.remove(index);
				this.repaint();
				break;
			}
		}
	}
	
	class MouseHandler implements MouseInputListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // left button
				if (e.getClickCount() == 1) {
					lButtonClick(e);
				}
				else if (e.getClickCount() == 2) {
					lButtonDoubleClick(e);
				}
			}
		}
		
		private void lButtonClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool == ETools.ePolygon) {
					prepareDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			}
			else if (eDrawingState == EDrawingState.eNPointDrawing) {
				continueDrawing(e.getX(), e.getY());	
			}
			else if (eDrawingState == EDrawingState.eCut) {
				cut(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState == EDrawingState.eCopy) {
				copy(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState == EDrawingState.ePaste) {
				paste(e.getX(), e.getY());
				finishDrawing(e.getX(), e.getY());
			}
			else if (eDrawingState == EDrawingState.eErasing) {
				erase(e.getX(), e.getY());
			}
		}
		
		private void lButtonDoubleClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState == EDrawingState.ePaste) {
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState == EDrawingState.eErasing) {
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				keepDrawing(e.getX(), e.getY());
			}
			else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool == ETools.eMove) {
					eDrawingState = EDrawingState.eMoving;
				}
				else if (selectedTool == ETools.eResize) {
					eDrawingState = EDrawingState.eResizing;
				}
				else if (selectedTool != ETools.ePolygon) {
					prepareDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				keepDrawing(e.getX(), e.getY());
			}
			else if (eDrawingState == EDrawingState.eMoving) {
				moveShape(e.getX(), e.getY());
			}
			else if (eDrawingState == EDrawingState.eResizing) {
				keepDrawing(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState == EDrawingState.eMoving) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState == EDrawingState.eResizing) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
		}
	}
}
