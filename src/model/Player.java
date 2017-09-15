package model;

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

	public Player(AABB aabb, int depth) {
		super(aabb, depth, 10);
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
		if (getVelocity().y >= 0) {
			this.applyForce(new Vector(0, -JUMP_FORCE));
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
		super.render(g);
	}

	@Override
	public void tick(double dt){
		double xSpeed = this.getVelocity().x;
		if(this.movement == Movement.MOVE_RIGHT)
			xSpeed = this.MOVEMENT_SPEED * dt;
		if(this.movement == Movement.MOVE_LEFT)
			xSpeed = -this.MOVEMENT_SPEED * dt;
		if(this.movement == Movement.STATIONARY)
			xSpeed = 0;
		this.applyForce(new Vector(xSpeed, 0));
		super.tick(dt);
	}
}
