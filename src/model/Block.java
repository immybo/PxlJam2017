package model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Block extends AbstractEntity {

	public Block(AABB aabb, int depth, double mass){
		super(aabb, depth, mass);
	}

	public abstract double getBounciness();

	@Override
	public void tick(double dt){
		
	}
}
