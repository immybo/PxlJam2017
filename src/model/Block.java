package model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Block extends AbstractEntity {

	public Block(Point2D position, Shape collisionBoxOriginal, int depth, double mass){
		super(position, collisionBoxOriginal, depth, mass);
	}

	public abstract double getBounciness();

	@Override
	public void tick(double dt){
		
	}
}
