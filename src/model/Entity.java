package model;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Point2D;

public interface Entity {
	public int getDepth();
	public Point2D getPosition();
	public Shape getCollisionBox();
	public void render(Graphics graphics);
	public void setPosition(double x, double y);
	public void applyForce(double x, double y);
	public void tick(double dt);
	public double getMass();
	public void setMass(double mass);
	public double getXSpeed();
	public double getYSpeed();
	public void setXSpeed(double x);
	public void setYSpeed(double y);
}
