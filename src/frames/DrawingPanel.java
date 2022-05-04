package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants.ETools;
import shapes.TShape;

public class DrawingPanel extends JPanel {
	
	// attribute
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<TShape> shapes;
	
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing
	}
	
	private ETools selectedTool;
	private EDrawingState eDrawingState;
	private TShape currentShape;
	private TShape selectedShape;
	
	public DrawingPanel() {
		// attributes
		this.eDrawingState = EDrawingState.eIdle;
		this.setBackground(Color.WHITE);

		// components
		this.shapes = new Vector<>();
		MouseHandler mouseHandler = new MouseHandler();
		
		// button
		this.addMouseListener(mouseHandler);
		
		// position
		this.addMouseMotionListener(mouseHandler);
		
		// wheel
		this.addMouseWheelListener(mouseHandler);
	}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
	}
	
	public Object getShapes() {
		return this.shapes;
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (TShape shape : this.shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}
	
	private void prepareDrawing(int x, int y) {
		this.currentShape = this.selectedTool.newShape();
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.currentShape.setOrigin(x, y);
		this.currentShape.draw(graphics2D);
	}
	
	private void keepDrawing(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(getBackground());
		
		// erase
		this.currentShape.draw(graphics2D);
		
		// draw
		this.currentShape.resize(x, y);
		this.currentShape.draw(graphics2D);
	}
	
	private void continueDrawing(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finishDrawing(int x, int y) {
		this.shapes.add(this.currentShape);
		this.currentShape.drawAnchors((Graphics2D) this.getGraphics());
		this.selectedShape = this.currentShape;
	}
	
	private TShape onShape(int x, int y) {
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		if (this.onShape(x, y) != null) {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}
		this.setCursor(cursor);
	}
	
	private void changeSelection(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.repaint();
		
		// draw anchors
		this.selectedShape = this.onShape(x, y);
		if (this.selectedShape != null) {
			this.selectedShape.drawAnchors(graphics2D);
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
				changeSelection(e.getX(), e.getY());
				if (selectedTool == ETools.ePolygon) {
					prepareDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			}
			else if (eDrawingState == EDrawingState.eNPointDrawing) {
				continueDrawing(e.getX(), e.getY());	
			} 
		}
		
		private void lButtonDoubleClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				keepDrawing(e.getX(), e.getY());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool != ETools.ePolygon) {
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
			else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
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
