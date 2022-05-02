package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class TAnchors {
	
	private final static int WIDTH = 15;
	private final static int HEIGHT = 15;
	
	public enum EAnchors {
		eNW,
		eWW,
		eSW,
		eSS,
		eSE,
		eEE,
		eNE,
		eNN,
		eRR;
	}
	
	private Ellipse2D[] anchors;
	
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length];
		for (EAnchors eAnchor : EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
	}
	
	public void draw(Graphics2D graphics2D, Rectangle boundingRectangle) {
		int x = boundingRectangle.x;
		int y = boundingRectangle.y;
		int w = boundingRectangle.width;
		int h = boundingRectangle.height;
		
		for (EAnchors eAnchor : EAnchors.values()) {
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
				y = y + h / 2;
				break;
			default:
				break;
			}
			this.anchors[eAnchor.ordinal()].setFrame(x, y, WIDTH, HEIGHT);
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);
		}
	}
}
