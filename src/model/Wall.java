package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class Wall extends AbstractEntity implements Block {
	private double bounciness;
	private Image texture;

	public Wall(Point2D position, Shape collisionBox, Image texture, double bounciness, int depth) {
		super(position, collisionBox, depth);
		this.bounciness = bounciness;
		this.texture = texture;
	}
	
	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public double getBounciness() {
		return bounciness;
	}
	
}
