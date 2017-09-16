package model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Block extends AbstractEntity {

	public Block(AABB aabb, int depth, double mass, Level level){
		super(aabb, depth, mass, level);
	}

	public abstract double getBounciness();

	@Override
	public void tick(double dt){
		
	}
}
