package shape;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class TAnchor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final static int WIDTH = 10;
	private final static int HEIGHT = 10;
	
	public enum EAnchors {
		eNW,
		eWW,
		eSW,
		eSS,
		eSE,
		eEE,
		eNE,
		eNN,
		eRR,
		eMove;
	}
	
	private Point2D anchors[];
	private Point2D transformedAnchors[];
	private Dimension dimension;
	private EAnchors eSelectedAnchor;
	private EAnchors eResizeAnchor;
	
	public EAnchors getSelectedAnchor() {
		return this.eSelectedAnchor;
	}
	
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}
	
	public EAnchors getResizeAnchor() {
		return this.eResizeAnchor;
	}
	
	public void setResizeAnchor(EAnchors eResizeAnchor) {
		this.eResizeAnchor = eResizeAnchor;
	}
	
	public TAnchor() {
		this.anchors = new Point2D[EAnchors.values().length - 1];
		
		for (int i = 0; i < EAnchors.values().length - 1; i++) {
			this.anchors[i] = new Point2D.Double();
		}
		
		this.transformedAnchors = new Point2D[EAnchors.values().length - 1];
		
		for (int i = 0; i < EAnchors.values().length - 1; i++) {
			this.transformedAnchors[i] = new Point2D.Double();
		}
		
		this.dimension = new Dimension(WIDTH, HEIGHT);
	}
	
	public boolean contains(int x, int y, Rectangle boundingRectangle, AffineTransform affineTransform) {
		Ellipse2D ellipse = new Ellipse2D.Double();
		
		for (int i = 0; i < EAnchors.values().length - 1; i++) {
			ellipse.setFrame(this.transformedAnchors[i], dimension);
			
			if (ellipse.contains(x, y)) {
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		
		return false;
	}
	
	public void draw(Graphics2D graphics2D, Rectangle boundingRectangle, AffineTransform affineTransform) {
		for (int i = 0; i < EAnchors.values().length - 1; i++) {
			int x = boundingRectangle.x - WIDTH / 2;
			int y = boundingRectangle.y - HEIGHT / 2;
			int w = boundingRectangle.width;
			int h = boundingRectangle.height;
			
			EAnchors eAnchor = EAnchors.values()[i];			
			
			switch (eAnchor) {
			case eNW:
				break;
			case eWW:
				y = y + h / 2;
				break;
			case eSW:
				y = y + h;
				break;
			case eSS:
				x = x + w / 2;
				y = y + h;
				break;
			case eSE:
				x = x + w;
				y = y + h;
				break;
			case eEE:
				x = x + w;
				y = y + h / 2;
				break;
			case eNE:
				x = x + w;
				break;
			case eNN:
				x = x + w / 2;
				break;
			case eRR:
				x = x + w / 2;
				y = y - h / 2;
				break;
			default:
				break;
			}
			

			this.anchors[i].setLocation(x, y);
			affineTransform.transform(this.anchors[i], this.transformedAnchors[i]);
			Ellipse2D ellipse = new Ellipse2D.Double();
			ellipse.setFrame(this.transformedAnchors[i], dimension);
			graphics2D.draw(ellipse);
			
		}
	}
	
	public Point getResizeAnchorPoint(int x, int y, Rectangle r) {
		int cx = 0;
		int cy = 0;
		this.eResizeAnchor = null;
		
		switch (this.eSelectedAnchor) {
			case eNW: 
				eResizeAnchor = EAnchors.eSE;
				cx = r.x + r.width;
				cy = r.y + r.height;
				break;
			case eWW:
				eResizeAnchor = EAnchors.eEE;
				cx = r.x + r.width;
				cy = r.y + r.height / 2;
				break;				
			case eSW:
				eResizeAnchor = EAnchors.eNE;
				cx = r.x + r.width;
				cy = r.y;
				break;				
			case eSS:
				eResizeAnchor = EAnchors.eNN;
				cx = r.x + r.width / 2;
				cy = r.y;
				break;				
			case eSE:
				eResizeAnchor = EAnchors.eNW;
				cx = r.x;
				cy = r.y;
				break;
			case eEE:
				eResizeAnchor = EAnchors.eWW;
				cx = r.x;
				cy = r.y + r.height / 2;
				break;				
			case eNE:
				eResizeAnchor = EAnchors.eSW;
				cx = r.x;
				cy = r.y + r.height;
				break;				
			case eNN:
				eResizeAnchor = EAnchors.eSS;
				cx = r.x + r.width / 2;
				cy = r.y + r.height;
				break;				
			default:
				break;
		}
		
		return new Point(cx, cy);
	}

}
