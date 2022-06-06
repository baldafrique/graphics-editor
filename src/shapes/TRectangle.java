package shapes;

import java.awt.Rectangle;

public class TRectangle extends TShape {
	
	private static final long serialVersionUID = 1L;

	public TRectangle() {
		this.shape = new Rectangle();
	}
	
	@Override
	public TShape clone() {
		return new TRectangle();
	}
	
	public void prepareDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setFrame(x, y, 0, 0);
	}
	
	@Override
	public void keepDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x - rectangle.x, y - rectangle.y);
	}
	
}