package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.geom.Point2D;

public class Player implements Character{
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
	public int getDepth() {
		return 0;
	}

	@Override
	public Point2D getPosition() {
		return new Point2D.Double(50, 50);
	}

	@Override
	public Shape getCollisionBox() {
		return null;
	}

	@Override
	public void render(Graphics graphics) {

	}

	@Override
	public void setPosition(double x, double y) {

	}
}
