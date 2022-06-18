package shapes;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Oval extends Ellipse2D {
	private int x, y, width, height;

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFrame(double x, double y, double w, double h) {
		this.x = (int) x;
		this.y = (int) y;
		this.width = (int) w;
		this.height = (int) h;
	}

}
