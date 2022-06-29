package shape;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import global.Constant.ECursor;
import global.Constant.ETransformers;
import shape.TAnchor.EAnchors;

abstract public class TShape implements Serializable {
	
	// attributes
	private static final long serialVersionUID = 1L;
	private boolean bSelected;

	// components
	protected Shape shape;
	protected Shape transformedShape;
	protected AffineTransform affineTransform;
	protected TAnchor anchors;

	// constructors
	public TShape() {
		this.bSelected = false;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		this.anchors = new TAnchor();
	}
	
	public abstract TShape clone();
	
	public void initialize() {
		
	}
	
	// setters and getters  
	public EAnchors getSelectedAnchor() {
		return this.anchors.getSelectedAnchor();
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
	
	public ETransformers getETransformer() {
		EAnchors eAnchor = this.anchors.getSelectedAnchor();
		if(eAnchor == EAnchors.eMove) {
			return ETransformers.eMover;
		} else if(eAnchor == EAnchors.eRR) {
			return ETransformers.eRotator;
		} else {							
			return ETransformers.eResizer;
		}		
	}
	
	public Shape getTransformedShape() {
		return this.affineTransform.createTransformedShape(this.shape);
	}

	public TAnchor getAnchors() {
		return this.anchors;
	}
	
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}
	
	// methods
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}
	public void finalizeDrawing(int x, int y) {}

	public boolean contains(int x, int y) {
		if (isSelected()) {
			if (this.anchors.contains(x,  y, this.getBounds(), this.affineTransform)) {
				return true;
			}
		}
		
		if (transformedShape.contains(x, y)) {
			this.anchors.setSelectedAnchor(EAnchors.eMove);
			return true;
		}
		
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		this.transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.draw(this.transformedShape);		
		if (isSelected()) {
			this.anchors.draw(graphics2D, this.shape.getBounds(), this.affineTransform);
		}
	}

	public void drawAnchors(Graphics2D graphics2D) {
		this.anchors.draw(graphics2D, this.shape.getBounds(), this.affineTransform);
	}	
	
	public Cursor getCursor() {
		return this.isSelected() ? ECursor.valueOf(this.anchors.getSelectedAnchor().toString()).getCursor() : ECursor.eMove.getCursor();
	}

}