package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class AbstractEntity implements Entity {
	private Point2D position;
	private Shape collisionBox;
	private Shape collisionBoxOriginal;
	private int depth;

	private double forceX;
	private double forceY;

	private double speedX;
	private double speedY;

	private double mass;

	public AbstractEntity(Point2D position, Shape collisionBoxOriginal, int depth, double mass) {
		this.collisionBoxOriginal = collisionBoxOriginal;
		this.setPosition(position.getX(), position.getY());
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
	public Point2D getPosition() {
		return this.position;
	}

	@Override
	public Shape getCollisionBox() {
		return this.collisionBox;
	}

	@Override
	public void render(Graphics graphics) {
		Rectangle2D bounds = collisionBox.getBounds2D();
		graphics.setColor(Color.YELLOW);
		graphics.drawRect((int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(),
				(int) bounds.getMaxY());
		graphics.drawString(this.getClass().getSimpleName(), (int) bounds.getMinX(), (int) bounds.getMinY());
	}

	@Override
	public void setPosition(double x, double y) {
		this.position = new Point2D.Double(x, y);
		this.collisionBox = createTranslatedShape(this.collisionBoxOriginal, x, y);
	}
	
	private static Shape createTranslatedShape(Shape shape, double transX, double transY) {
		AffineTransform transform = AffineTransform.getTranslateInstance(transX, transY);
		return transform.createTransformedShape(shape);
	}

	@Override
	public void applyForce(double x, double y) {
		forceX += x;
		forceY += y;
	}

	@Override
	public void tick(double dt) {
		// Apply the forces to change the velocity of this entity
		speedX += forceX / mass;
		speedY += forceY / mass;

		this.setPosition(this.getPosition().getX()+speedX, this.getPosition().getY()+speedY);

		forceX = 0;
		forceY = 0;
	}
}
