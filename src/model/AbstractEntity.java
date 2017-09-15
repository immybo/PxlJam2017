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

	public AbstractEntity(Point2D position, Shape collisionBox, int depth) {
		this.collisionBox = collisionBoxOriginal;
		this.setPosition(position.getX(), position.getY());
		this.depth = depth;
	}

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

}
