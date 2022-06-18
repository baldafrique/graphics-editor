package shapes;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class TPolygon extends TShape {
	private static final long serialVersionUID = 1L;
	private final int MAX_POINTS = 20;
	
	public TPolygon() {
		this.shape = new Polygon();
	}
	
	public void setOrigin(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints = new int[MAX_POINTS];
		polygon.ypoints = new int[MAX_POINTS];
		polygon.npoints = 0;
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
	}
	
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints] = x;
		polygon.ypoints[polygon.npoints] = y;
		polygon.npoints++;
	}
	
	@Override
	public void resize(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints - 1] = x;
		polygon.ypoints[polygon.npoints - 1] = y;
	}

	@Override
	public void draw(Graphics2D graphics) {
		Polygon polygon = (Polygon) this.shape;
		graphics.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
	}

	@Override
	public TShape clone() {
		return new TPolygon();
	}

	@Override
	public boolean contains(int x, int y) {
		return this.shape.contains(x, y);
	}

	@Override
	public void moveShape(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = x - polygon.xpoints[i];
			polygon.ypoints[i] = y - polygon.ypoints[i];
		}
	}

	@Override
	public void fill(Graphics2D graphics) {
		Polygon polygon = (Polygon) this.shape;
		graphics.fill(polygon);
	}
}
