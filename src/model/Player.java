package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Player extends AbstractEntity implements Character {
	public static final int JUMP_FORCE = 50;
	private static final int MOVEMENT_SPEED = 500;
	private boolean onGround;
	private boolean jumpNextTick;

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
		this.onGround = true;
		this.jumpNextTick = false;
	}

	public void setOnGround(boolean value) {
		this.onGround = value;
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
		if (this.onGround) {
			this.jumpNextTick = true;
		}
	}

	public boolean getJumpNextTick() {
		return this.jumpNextTick;
	}

	public void setJumpNextTick(boolean value) {
		this.jumpNextTick = value;
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
		double ySpeed = this.getVelocity().y;
		double xSpeed;
		if (effects.contains(StatusEffect.BUBBLE))
			xSpeed = 0;
		else if (effects.contains(StatusEffect.FROZEN))
			// Retain the same speed
			xSpeed = this.getVelocity().x;
		else if(this.movement == Movement.MOVE_RIGHT)
			xSpeed = this.MOVEMENT_SPEED * dt;
		else if(this.movement == Movement.MOVE_LEFT)
			xSpeed = -this.MOVEMENT_SPEED * dt;
		else
			xSpeed = 0;
		this.setVelocity(new Vector(xSpeed, ySpeed));
		super.tick(dt);
	}
}
