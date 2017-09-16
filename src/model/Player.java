package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
	public static final int JUMP_FORCE = 100;
	private static final int MOVEMENT_SPEED = 500;
	private boolean onGround;
	private boolean jumpNextTick;
	private Image texture;

	private Movement movement;

	private enum Movement {
			MOVE_LEFT,
			MOVE_RIGHT,
			STATIONARY
	};

	private List<StatusEffect> effects;

	public Player(AABB aabb, int depth, Level level, Image texture) {
		super(aabb, depth, 10, level);
		effects = new ArrayList<StatusEffect>();
		this.movement = Movement.STATIONARY;
		this.onGround = true;
		this.jumpNextTick = false;
		this.texture = texture;
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
	public void render(Graphics g) {

		Vector min = getAABB().min();
		Vector ext = getAABB().extents;
		g.drawImage(texture, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
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
