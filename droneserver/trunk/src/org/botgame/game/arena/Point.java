package org.botgame.game.arena;

//app engine doesn't allow you to use awt.point
public class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		setLocation(p);
	}

	public void setLocation(Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public double distance(Point p) {
		return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(")");
		return sb.toString();
	}
}
