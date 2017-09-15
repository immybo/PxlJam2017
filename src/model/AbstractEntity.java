package model;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class AbstractEntity implements Entity {

	@Override
	public int getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape getCollisionBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(double x, double y) {
		// TODO Auto-generated method stub
		
	}

}
