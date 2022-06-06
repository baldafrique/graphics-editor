package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Mover extends Transformer {
	public Mover(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.px = x;
		this.py = y;			
	}
	
	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		this.affineTransform.translate(x - this.px, y - this.py);
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
//		this.shape = this.affineTransform.createTransformedShape(this.shape);
//		this.affineTransform.setToIdentity();
	}
}
