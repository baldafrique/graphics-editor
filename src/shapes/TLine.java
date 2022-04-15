package shapes;

import java.awt.Graphics2D;

public class TLine extends TShape {
	private int x, y, x1, y1;
	
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
		this.x1 = x;
		this.y1 = y;
	}

	@Override
	public void resize(int x, int y) {
		this.x1 = x;
		this.y1 = y;
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawLine(this.x, this.y, this.x1, this.y1);
	}

	@Override
	public TShape clone() {
		return new TLine();
	}

}
