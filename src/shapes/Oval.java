package shapes;

import java.awt.Graphics2D;

public class Oval extends Shape {
	private int x, y, width, height;
	
	public Oval(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
	}

	@Override
	public void resize(int x, int y) {
		this.width = x - this.x;
		this.height = y - this.y;
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawOval(this.x, this.y, this.width, this.height);
		
	}
}
