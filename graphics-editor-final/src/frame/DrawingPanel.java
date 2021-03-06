package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constant.ECursor;
import global.Constant.ETools;
import global.Constant.ETransformationStyle;
import global.Constant.ETransformers;
import shape.TAnchor.EAnchors;
import shape.TSelection;
import shape.TShape;
import transformer.Transformer;

public class DrawingPanel extends JPanel implements Printable {
	
	// attribute
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<TShape> shapes;
	private Stack<TShape> stack;
	private BufferedImage bufferedImage;
	private Graphics2D graphics2DBufferedImage;
	private TShape shapeBuffer;
	
	// associate attribute
	private ETools selectedTool;
	private TShape selectedShape;
	private TShape currentShape;
	private Transformer transformer;
	
	// working variables
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing
	}
	
	private EDrawingState eDrawingState;
	private boolean updated;
	
	public DrawingPanel() {
		// attributes
		this.setBackground(Color.WHITE);
		this.eDrawingState = EDrawingState.eIdle;

		// components
		this.shapes = new Vector<>();
		this.stack = new Stack<>();
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
	}
	
	public void initialize() {
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
		this.graphics2DBufferedImage.setColor(this.getBackground());
		this.graphics2DBufferedImage.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.graphics2DBufferedImage.setColor(this.getForeground());
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
		this.shapes.clear();
		this.repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
		this.drawAll((Graphics2D)this.getGraphics());
	}
	
	public Object getShapes() {
		return this.shapes;
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;
	}
	
	public boolean isUpdated() {
		return this.updated;
	}
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.drawImage(this.bufferedImage, 0, 0, this);
	}
	
	private void prepareTransforming(int x, int y) {
		if (this.selectedTool == ETools.eSelection) {
			this.currentShape = onShape(x, y);
			
			if (this.currentShape == null) {
				this.currentShape = this.selectedTool.newShape();
				this.transformer = ETransformers.eDrawer.getTransformer();
			} 
			else {
				ETransformers eTransformer = currentShape.getETransformer();
				this.transformer = eTransformer.getTransformer();
			}
		} 
		else {
			this.currentShape = this.selectedTool.newShape();
			this.transformer = ETransformers.eDrawer.getTransformer();			
		}
		
		this.transformer.setShape(this.currentShape);
		this.transformer.prepareTransforming(x, y, graphics2DBufferedImage);
		
		if (this.selectedShape != null) {
			this.selectedShape.drawAnchors(graphics2DBufferedImage);
			this.selectedShape.setSelected(false);
		}
		this.repaint();
	}

	private void keepTransforming(int x, int y) {
		this.transformer.keepTransforming(x, y, graphics2DBufferedImage);
		this.repaint();
	}
	
	private void continueTransforming(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finalizeTransforming(int x, int y) {
		this.transformer.finalizeTransforming(x, y, graphics2DBufferedImage);
		
		if (!(this.currentShape instanceof TSelection)) {
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.drawAnchors(graphics2DBufferedImage);
			this.selectedShape.setSelected(true);
		}
		setUpdated(true);
		this.repaint();
	}

	private TShape onShape(int x, int y) {
		for (TShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	
	private void changeSelection(int x, int y) {
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);	
		}
		
		this.repaint();
		
		// draw anchors
		this.selectedShape = this.onShape(x, y);
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(true);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		return 0;
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		
		if (this.selectedTool == ETools.eSelection) {
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			this.currentShape = this.onShape(x, y);
			
			if (currentShape != null) {
				cursor = ECursor.eMove.getCursor();
				if (this.currentShape.isSelected()) {
					EAnchors eAnchor = this.currentShape.getSelectedAnchor();
					cursor = ECursor.valueOf(eAnchor.toString()).getCursor();
				}
			}
		}
		
		this.setCursor(cursor);
		
		this.setCursor(cursor);
	}
	
	public void drawAll(Graphics2D graphics2D) {
		this.clearPanel(graphics2D);
		this.drawShapes(graphics2D);
		if (this.selectedShape != null) {
			this.selectedShape.drawAnchors(graphics2D);
		}
	}
	
	private void clearPanel(Graphics2D graphics2D) {
		Color foreGround = graphics2D.getColor();
		graphics2D.setColor(this.getBackground());
		graphics2D.fill(this.getVisibleRect());
		graphics2D.setColor(foreGround);	
	}
	
	private void drawShapes(Graphics2D graphics2D) {
		this.clearPanel(graphics2D);
		for (TShape shape : this.shapes) {
			shape.draw(graphics2D);
		}
	}

	public void undo() {
		if (!shapes.isEmpty()) {
			TShape removedShape = this.shapes.lastElement();
			stack.push(removedShape);
			this.shapes.remove(removedShape);
		}
		this.drawAll((Graphics2D)this.getGraphics());
	}
	
	public void redo() {
		this.shapes.add(stack.pop());
		this.drawAll((Graphics2D)this.getGraphics());
	}
	
	public void cut() {
		this.shapeBuffer = this.selectedShape;
		this.shapes.remove(this.selectedShape);
		this.selectedShape = null;
		this.drawAll((Graphics2D)this.getGraphics());
	}
	
	public void copy() {
		this.shapeBuffer = this.selectedShape;
	}
	
	public void paste() {
		this.shapes.add(this.shapeBuffer.clone());
		this.drawAll((Graphics2D)this.getGraphics());
	}
	
	public void group() {
		
	}
	
	public void ungroup() {
		
	}
	
	public void delete() {
		this.shapes.remove(this.selectedShape);
		this.drawAll((Graphics2D)this.getGraphics());
	}
	
	class MouseHandler implements MouseInputListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // left button
				if (e.getClickCount() == 1) {
					this.lButtonClicked(e);
				}
				else if (e.getClickCount() == 2) {
					this.lButtonDoubleClicked(e);
				}
			}
		}
		
		private void lButtonClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeSelection(e.getX(), e.getY());
				if (selectedTool.getTransformationStyle() == ETransformationStyle.eNPTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			}
			else if (eDrawingState == EDrawingState.eNPointDrawing) {
				continueTransforming(e.getX(), e.getY());	
			} 
		}
		
		private void lButtonDoubleClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
			else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getTransformationStyle() == ETransformationStyle.e2PTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				finalizeTransforming(e.getX(), e.getY());
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
