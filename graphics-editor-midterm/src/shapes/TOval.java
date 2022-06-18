package shapes;

import java.awt.Graphics2D;

public class TOval extends TShape {
	private static final long serialVersionUID = 1L;
	
	public TOval() {
		this.shape = new Oval();
	}
	
	public void setOrigin(int x, int y) {
		Oval oval = (Oval) this.shape;
		oval.setFrame(x, y, 0, 0);
	}

	@Override
	public void resize(int x, int y) {
		Oval oval = (Oval) this.shape;
		oval.setFrame(oval.getX(), oval.getY(), x - oval.getX(), y - oval.getY());
	}

	@Override
	public void draw(Graphics2D graphics) {
		Oval oval = (Oval) this.shape;
		graphics.drawOval((int) oval.getX(), (int) oval.getY(), (int) oval.getWidth(), (int) oval.getHeight());
	}
	
	public void fill(Graphics2D graphics) {
		Oval oval = (Oval) this.shape;
		graphics.fillOval((int) oval.getX(), (int) oval.getY(), (int) oval.getWidth(), (int) oval.getHeight());
	}

	@Override
	public TShape clone() {
		return new TOval();
	}

	@Override
	public boolean contains(int x, int y) {
		return this.shape.contains(x, y);
	}

	@Override
	public void moveShape(int x, int y) {
		Oval oval = (Oval) this.shape;
//		oval.setFrame(x - oval.getX(), y - oval.getY(), oval.getWidth(), oval.getHeight());
		oval.setFrame(x, y, oval.getWidth(), oval.getHeight());
	}
}
