package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;

public class DrawingPanel extends JPanel {
	// attribute
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<TShape> shapes;
	
	public enum ETools {
		eRectangle,
		eOval,
		eLine,
		ePolygon
	}
	
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing
	}
	
	private ETools eSelectedTool;
	private EDrawingState eDrawingState;
	private TShape selectedTool;
	
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
	
	public void setSelectedTool(ETools eSelectedTool) {
		this.eSelectedTool = eSelectedTool;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (TShape shape : this.shapes) {
			shape.draw((Graphics2D) g);
		}
	}
	
	private void prepareDrawing(int x, int y) {
		if (eSelectedTool == ETools.ePolygon) {
			this.selectedTool = new TPolygon(x, y);
		}
		else if (eSelectedTool == ETools.eRectangle) {
			this.selectedTool = new TRectangle(x, y);
		}
		else if (eSelectedTool == ETools.eOval) {
			this.selectedTool = new TOval(x, y);
		}
		else if (eSelectedTool == ETools.eLine) {
			this.selectedTool = new TLine(x, y);	
		}
			
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.selectedTool.draw(graphics2D);
	}
	
	private void keepDrawing(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(getBackground());
		
		// erase
		this.selectedTool.draw(graphics2D);
		
		// draw
		this.selectedTool.resize(x, y);
		this.selectedTool.draw(graphics2D);
	}
	
	private void continueDrawing(int x, int y) {
		this.selectedTool.addPoint(x, y);
	}
	
	private void finishDrawing(int x, int y) {
		this.shapes.add(this.selectedTool);
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
				if (eSelectedTool == ETools.ePolygon) {
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
				if (eSelectedTool != ETools.ePolygon) {
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
