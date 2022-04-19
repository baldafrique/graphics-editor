package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;

abstract public class TShape {
	protected Shape shape;
	public abstract void setOrigin(int x, int y);
	public abstract void resize(int x, int y);
	public abstract void draw(Graphics2D graphics);
	public abstract boolean contains(int x, int y);
	public abstract TShape clone();
	public void addPoint(int x, int y) {}
}