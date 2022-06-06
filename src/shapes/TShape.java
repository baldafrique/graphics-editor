package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	
	// attributes
	private static final long serialVersionUID = 1L;
	private boolean bSelected;

	// components
	public Shape shape;
	private AffineTransform affineTransform;
	private TAnchors anchors;

	
	// getters and setters
	public EAnchors getSelectedAnchor() {
		return this.anchors.getESelectedAnchor();
	}
	
	public boolean isSelected() {
		return this.bSelected;
	}
	
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	
	public AffineTransform getAffineTransform() {
		return this.affineTransform;
	}

	public TAnchors getAnchors() {
		return this.anchors;
	}
	
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}
	
	// constructors
		public TShape() {
			this.bSelected = false;
			this.affineTransform = new AffineTransform();
			this.affineTransform.setToIdentity();
			this.anchors = new TAnchors();
		}
	
	public abstract TShape clone();

	// methods
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}

	public boolean contains(int x, int y) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if (this.bSelected) {
			if (this.anchors.contains(x,  y)) {
				return true;
			}
		}
		
		if(transformedShape.contains(x, y)) {
			this.anchors.setESelectedAnchor(EAnchors.eMove);
			return true;
		}
		
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		
		graphics2D.draw(transformedShape);
		if (this.bSelected) {
			this.anchors.draw(graphics2D, transformedShape.getBounds());
		}
	}

}