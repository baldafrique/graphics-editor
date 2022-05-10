package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	public Shape shape;
	public TAnchors anchors;
	
	// working
	private boolean bSelected;
	private EAnchors eSelectedAnchor;
	
	
	// constructors
	public TShape() {
		this.anchors = new TAnchors();
		this.bSelected = false;
	}
	
	// getters and setters
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	
	public boolean isSelected() {
		return this.bSelected;
	}
	
	
	public EAnchors getSelectedAnchor() {
		return eSelectedAnchor;
	}

	public void seteSelectedAnchor(EAnchors eAnchor) {
		this.eSelectedAnchor = eAnchor;
	}
	
	public abstract TShape clone();

	// methods
	public abstract void setOrigin(int x, int y);
	public abstract void resize(int x, int y);
	public void addPoint(int x, int y) {}

	public boolean contains(int x, int y) {
		if (this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y);
			if (this.eSelectedAnchor != null) {
				return true;
			}
		}
		
		if(this.shape.contains(x, y)) {
			this.eSelectedAnchor = EAnchors.eMove;
		}
		
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(shape);
		if (this.bSelected) {
			this.anchors.draw(graphics2D, this.shape.getBounds());
		}
	}

}