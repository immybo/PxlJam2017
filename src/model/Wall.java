package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Wall extends Block {
	private double bounciness;
	private Image texture;

	public Wall(AABB aabb, Image texture, double bounciness, int depth) {
		super(aabb, depth, 100);
		this.bounciness = bounciness;
		this.texture = texture;
	}
	
	@Override
	public void render(Graphics g) {
		Vector min = getAABB().min();
		Vector ext = getAABB().extents;
		g.drawImage(texture, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
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
