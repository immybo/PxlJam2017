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
	private static final int MOVEMENT_SPEED = 500;
	private boolean onGround;
	private boolean jumpNextTick;
	private Image texture;
	private long lastShotTime;
	private double shootRate = 0.01;


	public Player(AABB aabb, int depth, Level level) {
		super(aabb, depth, 10, level);
		this.texture = Textures.PLAYER;
		this.onGround = true;
		this.jumpNextTick = false;
		this.lastShotTime = 0;
	}

	public void setOnGround(boolean value) {
		this.onGround = value;
	}

	public void shoot(boolean isRight){
		if(System.currentTimeMillis() - lastShotTime >= 1.0/shootRate) {
			//pewpew
			Vector vector = isRight ? new Vector(10,0) : new Vector(-10,0);
			AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().extents.mult(0.1), this.getVelocity(), null);
			Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 50, vector, true, StatusEffect.NONE);
			this.getLevel().addEntity(bullet);
		}
	}

	public void moveLeft() {
		this.setMovement(Movement.MOVE_LEFT);
	}
	public void moveRight() {
		this.setMovement(Movement.MOVE_RIGHT);
	}
	public void stopLeftMovement() {
		if (this.getMovement() == Movement.MOVE_LEFT)
			this.setMovement(Movement.STATIONARY);
	}
	public void stopRightMovement() {
		if (this.getMovement() == Movement.MOVE_RIGHT)
			this.setMovement(Movement.STATIONARY);
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
	public void tick(double dt) {
		super.tick(dt);
	}

	@Override
	public int getMovementSpeed() {
		return 500;
	}
}
