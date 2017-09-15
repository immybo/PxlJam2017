package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class AbstractEntity implements Entity {
	private final static double GRAVITY = 9.81;
	private AABB aabb;
	private int depth;

	private double forceX;
	private double forceY;

	private double speedX;
	private double speedY;

	private double mass;

	public AbstractEntity(AABB aabb, int depth, double mass) {
		this.aabb = aabb;
		this.depth = depth;
		this.speedX = 0;
		this.speedY = 0;
		this.forceX = 0;
		this.forceY = 0;
		this.mass = mass;
	}

	@Override
	public double getXSpeed() { return this.speedX; }

	@Override
	public double getYSpeed() { return this.speedY; }

	@Override
	public double getMass() { return this.mass; }

	@Override
	public void setMass(double mass) { this.mass = mass; }

	@Override
	public int getDepth() {
		return this.depth;
	}

	@Override
	public AABB getAABB() {
		return this.aabb;
	}

	@Override
	public void render(Graphics g) {
		getAABB().draw(g);
		g.drawString(this.getClass().getSimpleName(), (int) getAABB().min().x, (int) getAABB().min().y);
	}

	@Override
	public void setPosition(Vector v) {
		this.getAABB().center = v;
	}

	@Override
	public void applyForce(Vector v) {
		if(mass > 0) {
			getAABB().acceleration = getAABB().acceleration.add(v.mult(1/mass));
		}
	}
	
	@Override
	public void setVelocity(Vector v) {
		getAABB().velocity = v;
	}

	@Override
	public void tick(double dt) {
		getAABB().velocity = getAABB().velocity.add(getAABB().acceleration);
		getAABB().center = getAABB().center.add(getAABB().velocity);
		getAABB().acceleration = Vector.zero();
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
