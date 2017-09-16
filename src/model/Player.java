package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player extends Character {
	public static final int JUMP_FORCE = 100;
	public static final int SPIKE_DAMAGE = 100000;
	private static final int MOVEMENT_SPEED = 500;
	private boolean onGround;
	private boolean jumpNextTick;
	private Image texture;
	private HashMap<StatusEffect, Long> statusTimeouts;
	private long lastShotTime;
	private double shootRate = 0.001;
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
		this.statusTimeouts = new HashMap<StatusEffect, Long>();
		this.lastShotTime = 0;
		for(StatusEffect s : StatusEffect.values()){
			this.statusTimeouts.put(s, new Long(0));
		}
	}

	public void setOnGround(boolean value) {
		this.onGround = value;
	}

	public void addStatusEffect(StatusEffect effect) {
		if (!effects.contains(effect)) {
			effects.add(effect);
		}
		this.statusTimeouts.put(effect, System.currentTimeMillis() + 3000);
	}

	public void removeStatusEffect(StatusEffect effect) {
		if (effects.contains(effect)) {
			effects.remove(effect);
		}
	}

	public List<StatusEffect> getEffects() {
		return effects;
	}

	public void shoot(){
		if(System.currentTimeMillis() - lastShotTime >= 1.0/shootRate) {
			//pewpew
			AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().extents.mult(0.1), this.getVelocity(), null);

				Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 50, new Vector(10, 0), true, StatusEffect.NONE);
				this.getLevel().addEntity(bullet);
		}
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
		if(effects.contains(StatusEffect.ON_FIRE)) {
			xSpeed *= 1.5;
			this.damage(dt * 10);
		}

		this.setVelocity(new Vector(xSpeed, ySpeed));
		for(StatusEffect s : StatusEffect.values()){
			if(this.statusTimeouts.get(s) <= System.currentTimeMillis()) {
				this.removeStatusEffect(s);
			}
		}
		super.tick(dt);
	}
}
