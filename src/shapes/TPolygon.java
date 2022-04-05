package shapes;

import java.awt.Graphics2D;

public class TPolygon extends TShape {
	private final int MAX_POINTS = 20;
	private int[] xPoints;
	private int[] yPoints;
	private int nPoints;
	
	public TPolygon(int x, int y) {
		this.nPoints = 0;
		this.xPoints = new int[MAX_POINTS];
		this.yPoints = new int[MAX_POINTS];
		this.addPoint(x, y);
	}
	
	public void addPoint(int x, int y) {
		this.xPoints[this.nPoints] = x;
		this.yPoints[this.nPoints] = y;
		nPoints++;
	}
	
	@Override
	public void resize(int x, int y) {
		this.xPoints[this.nPoints] = x;
		this.yPoints[this.nPoints] = y;
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawPolygon(xPoints, yPoints, nPoints + 1);
	}
}