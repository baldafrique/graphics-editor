package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Rotator extends Transformer {

	public Rotator(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2D) {
		this.cx = (int) this.selectedShape.getBounds().getCenterX();
		this.cy = (int) this.selectedShape.getBounds().getCenterY();	
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		double startAngle = Math.toDegrees(Math.atan2(cx-px, cy-py));
		double endAngle = Math.toDegrees(Math.atan2(cx-x, cy-y));
		
		double rotationAngle = startAngle-endAngle;
		if (rotationAngle < 0) {
			rotationAngle += 360;
		}
		this.affineTransform.rotate(Math.toRadians(rotationAngle), cx, cy);
	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2D) {
		
	}

}
