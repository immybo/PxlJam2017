package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.geom.Point2D;

public class Player extends AbstractEntity implements Character {
	public Player(Point2D position, Shape collisionBox, int depth) {
		super(position, collisionBox, depth);
	}

	public void moveLeft() {
		throw new NotImplementedException();
	}
	public void moveRight() {
		throw new NotImplementedException();
	}
	public void jump() {
		throw new NotImplementedException();
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public int getMaxHealth() {
		return 0;
	}

	@Override
	public int applyForce(double x, double y) {
		return 0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)this.getPosition().getX(), (int)this.getPosition().getY(), 50, 50);
	}
}
