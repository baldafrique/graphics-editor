package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class TRectangle extends TShape {
	private static final long serialVersionUID = 1L;

	public TRectangle() {
		this.shape = new Rectangle();
	}
	
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setFrame(x, y, 0, 0);
	}
	
	@Override
	public void resize(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x - rectangle.x, y - rectangle.y);
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		graphics.draw(this.shape);
	}
	
	public void fill(Graphics2D graphics) {
		graphics.fill(shape);
	}
	
	public boolean contains(int x, int y) {
		return this.shape.contains(x, y);
	}

	@Override
	public TShape clone() {
		return new TRectangle();
	}

	@Override
	public void moveShape(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
	}
}