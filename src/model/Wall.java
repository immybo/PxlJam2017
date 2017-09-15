package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Wall extends AbstractEntity implements Block {
	private double bounciness;
	private Image texture;

	public Wall(Point2D position, Shape collisionBox, Image texture, double bounciness, int depth) {
		super(position, collisionBox, depth, 100);
		this.bounciness = bounciness;
		this.texture = texture;
	}
	
	@Override
	public void render(Graphics g) {
		Rectangle2D cb = this.getCollisionBox().getBounds2D();
		//TODO tiling
		g.drawImage(texture, (int) cb.getMinX(), (int) cb.getMinY(), 
				(int) cb.getWidth(), (int) cb.getHeight(), null);
	}
	
	@Override
	public double getBounciness() {
		return bounciness;
	}
	
	@Override
	public double getMass() {
		return 0;
	}
	
}
