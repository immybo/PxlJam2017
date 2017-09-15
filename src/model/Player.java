package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Player extends AbstractEntity implements Character {
	private static final int JUMP_FORCE = 10;
	private static final int MOVEMENT_SPEED = 500;

	private static final int MAX_HORIZONTAL_SPEED = 10;

	private Movement movement;

	private enum Movement {
			MOVE_LEFT,
			MOVE_RIGHT,
			STATIONARY
	};

	private List<StatusEffect> effects;

	public Player(Point2D position, Shape collisionBox, int depth) {
		super(position, collisionBox, depth, 10);
		effects = new ArrayList<StatusEffect>();
		this.movement = Movement.STATIONARY;
	}

	public void addStatusEffect(StatusEffect effect) {
		if (!effects.contains(effect)) {
			effects.add(effect);
		}
	}

	public void removeStatusEffect(StatusEffect effect) {
		if (effects.contains(effect)) {
			effects.remove(effect);
		}
	}

	public List<StatusEffect> getEffects() {
		return effects;
	}

	public void moveLeft() {
		this.movement = Movement.MOVE_LEFT;
	}
	public void moveRight() {
		this.movement = Movement.MOVE_RIGHT;
	}
	public void stopLeftMovement() {
		if (this.movement == Movement.MOVE_LEFT)
			this.movement = Movement.STATIONARY;
	}
	public void stopRightMovement() {
		if (this.movement == Movement.MOVE_RIGHT)
			this.movement = Movement.STATIONARY;
	}
	public void jump() {
		// This is just if they're moving down;
		// TODO FIXME
		if (Math.abs(this.getYSpeed()) == 0) {
			this.applyForce(0, -JUMP_FORCE);
		}
	}

	@Override
	public int getHealth() {
		return 50;
	}

	@Override
	public int getMaxHealth() {
		return 100;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)this.getPosition().getX(), (int)this.getPosition().getY(), 50, 50);
	}

	@Override
	public void tick(double dt){
		double xSpeed = this.getXSpeed();
		if(this.movement == Movement.MOVE_RIGHT)
			xSpeed = this.MOVEMENT_SPEED * dt;
		if(this.movement == Movement.MOVE_LEFT)
			xSpeed = -this.MOVEMENT_SPEED * dt;
		if(this.movement == Movement.STATIONARY)
			xSpeed = 0;
		this.setXSpeed(xSpeed);
		super.tick(dt);
	}
}
