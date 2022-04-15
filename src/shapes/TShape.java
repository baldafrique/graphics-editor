package shapes;

import java.awt.Graphics2D;

abstract public class TShape {
	abstract public void setOrigin(int x, int y);
	abstract public void resize(int x, int y);
	abstract public void draw(Graphics2D graphics);
	abstract public TShape clone();
	public void addPoint(int x, int y) {}
}