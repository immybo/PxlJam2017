package model;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Point2D;

public interface Entity {
	public int getDepth();
	public AABB getAABB();
	public void render(Graphics graphics);
	public void setPosition(Vector v);
	public void applyForce(Vector v);
	public void tick(double dt);
	public double getMass();
	public void setMass(double mass);
	public double getXSpeed();
	public double getYSpeed();
	public void setVelocity(Vector v);
	public boolean isSolid();
}
