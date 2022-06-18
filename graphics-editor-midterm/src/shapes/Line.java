package shapes;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Line extends Line2D {
	private int x, y, x1, y1;

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX1() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public double getY1() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public Point2D getP1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX2() {
		// TODO Auto-generated method stub
		return this.x1;
	}

	@Override
	public double getY2() {
		// TODO Auto-generated method stub
		return this.y1;
	}

	@Override
	public Point2D getP2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		this.x = (int) x1;
		this.y = (int) y1;
		this.x1 = (int) x2;
		this.y1 = (int) y2;
	}

}
