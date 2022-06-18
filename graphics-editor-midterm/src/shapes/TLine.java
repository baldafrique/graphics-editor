package shapes;

import java.awt.Graphics2D;

public class TLine extends TShape {
	private static final long serialVersionUID = 1L;
	
	public TLine() {
		this.shape = new Line();
	}
	
	public void setOrigin(int x, int y) {
		Line line = (Line) this.shape;
		line.setLine(x, y, x, y);
	}

	@Override
	public void resize(int x, int y) {
		Line line = (Line) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}

	@Override
	public void draw(Graphics2D graphics) {
		Line line = (Line) this.shape;
		graphics.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
	}

	@Override
	public boolean contains(int x, int y) {
		return this.shape.contains(x, y);
	}

	@Override
	public TShape clone() {
		return new TLine();
	}

	@Override
	public void moveShape(int x, int y) {
		Line line = (Line) this.shape;
		line.setLine(x - line.getX1(), y - line.getY1(), x - line.getX2(), y - line.getY2());
	}

	@Override
	public void fill(Graphics2D graphics) {
		Line line = (Line) this.shape;
		graphics.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
	}
}
