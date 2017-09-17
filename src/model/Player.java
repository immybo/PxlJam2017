package model;

import controller.SoundPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player extends Character {
	public static final int JUMP_FORCE = 100;
	private static final int MOVEMENT_SPEED = 500;
	private boolean onGround;
	private boolean jumpNextTick;
	private List<Image> walkTextures;
	private List<Image> crawlTextures;
	private Image currentTexture;
	private long lastShotTime;
	private double shootRate = 0.01;
	private double manaPool;

	private double textureChangeRate = 0.005;
	private double textureChangeTime;
	private boolean isCrawling;
	private boolean isFacingRight;

	private AABB defaultAABB;
	private AABB crawlingAABB;

	public Player(AABB aabb, int depth, Level level) {
		super(aabb, depth, 10, level);
		this.walkTextures = new ArrayList<Image>();
		walkTextures.add(Textures.PLAYER_WALK_1); walkTextures.add(Textures.PLAYER_WALK_2);
		this.crawlTextures = new ArrayList<Image>();
		crawlTextures.add(Textures.PLAYER_CRAWL_1); crawlTextures.add(Textures.PLAYER_CRAWL_2);
		this.currentTexture = walkTextures.get(0);
		this.onGround = true;
		this.jumpNextTick = false;
		this.lastShotTime = 0;
		this.textureChangeTime = System.currentTimeMillis();
		this.defaultAABB = aabb;
		this.manaPool = 100;

		Vector crawlingCenter = new Vector(defaultAABB.center.x, defaultAABB.center.y + (defaultAABB.get_size().y*0.25));
		Vector crawlingSize = new Vector(defaultAABB.get_size().x, defaultAABB.get_size().y*0.25);
		this.crawlingAABB = new AABB(crawlingCenter, crawlingSize, defaultAABB.velocity, defaultAABB.acceleration);
		this.isFacingRight = true;
	}

	public void setOnGround(boolean value) {
		this.onGround = value;
	}

	public void shoot(boolean isRight){
		if(System.currentTimeMillis() - lastShotTime >= 1.0/shootRate) {
			StatusEffect statusEffect = StatusEffect.NONE;
			if(this.getEffects().contains(StatusEffect.POISONED)) statusEffect = StatusEffect.DOT;
			Vector vector = isRight ? new Vector(15,0) : new Vector(-15,0);
			AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().extents.mult(0.1), this.getVelocity(), null);
			Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 10, vector, true, statusEffect);
			this.getLevel().addEntity(bullet);
			SoundPlayer.playSound("resources/gun.wav");
		}
	}

	public double getMana(){ return this.manaPool; }
	public void takeMana(double cost) {	this.manaPool = cost > this.manaPool ? 0 : this.manaPool - cost; }
	private void addMana(double replenish) {this.manaPool = replenish+this.manaPool > this.getMaxMana() ? this.getMaxMana() : this.manaPool + replenish; }
	public double getMaxMana(){ return 100; }

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

		if (isFacingRight) {
			g.drawImage(currentTexture, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
		} else {
			g.drawImage(currentTexture, (int) min.x + currentTexture.getWidth(null), (int) min.y, (int) (-2 * ext.x), (int) (2 * ext.y), null);
		}
	}

	@Override
	public void tick(double dt) {
		isCrawling = this.getEffects().contains(StatusEffect.BROKEN_LEG);

		AABB currentAABB = this.getAABB();
		if (isCrawling) {
			if (currentAABB.get_size().y != crawlingAABB.get_size().y) {
				Vector newCenter = new Vector(currentAABB.center.x, currentAABB.center.y + crawlingAABB.get_size().y/2);
				this.setAABB(new AABB(newCenter, crawlingAABB.extents, currentAABB.velocity, currentAABB.acceleration));
			}
		} else {
			if (currentAABB.get_size().y != defaultAABB.get_size().y) {
				Vector newCenter = new Vector(currentAABB.center.x, currentAABB.center.y - crawlingAABB.get_size().y/2);
				this.setAABB(new AABB(newCenter, defaultAABB.extents, currentAABB.velocity, currentAABB.acceleration));
			}
		}
		this.addMana(dt * 2);
		if (System.currentTimeMillis()-textureChangeTime > (1.0/textureChangeRate)) {
			swapTexture();
			textureChangeTime = System.currentTimeMillis();
		}
		if(this.getEffects().contains(StatusEffect.BUBBLE)) this.heal(30 * dt);
		super.tick(dt);
	}

	private void swapTexture() {
		if (isCrawling) {
			int i = crawlTextures.indexOf(currentTexture) + 1;
			if (i == crawlTextures.size())
				i = 0;
			currentTexture = crawlTextures.get(i);
		} else {
			int i = walkTextures.indexOf(currentTexture) + 1;
			if (i == walkTextures.size())
				i = 0;
			currentTexture = walkTextures.get(i);
		}
	}

	@Override
	public int getMovementSpeed() {
		return 500;
	}



	public void turn(boolean isRight) {
		this.isFacingRight = isRight;
	}

	public void activatePower1() {
		if(this.getMana() >= 100) {
			if (this.getEffects().contains(StatusEffect.POISONED)) {
				activateRandomPower();
			} else {
				this.addStatusEffect(StatusEffect.POISONED);
				this.takeMana(100);
			}
		}
	}

	public void activatePower2() {
		if(this.getMana() >= 100) {
			if (this.getEffects().contains(StatusEffect.POISONED)) {
				activateRandomPower();
			} else {
				SoundPlayer.playSound("resources/breakleg.wav");
				this.addStatusEffect(StatusEffect.BROKEN_LEG);
				this.takeMana(100);
			}
		}
	}

	public void activatePower3() {
		if(this.getMana() >= 100) {
			if (this.getEffects().contains(StatusEffect.POISONED)) {
				activateRandomPower();
			} else {
				this.addStatusEffect(StatusEffect.ON_FIRE);
				this.takeMana(100);
			}
		}
	}

	public void activatePower4() {
		if(this.getMana() >= 100) {
			if (this.getEffects().contains(StatusEffect.POISONED)) {
				activateRandomPower();
			} else {
				this.addStatusEffect(StatusEffect.SPIKY);
				this.takeMana(100);
			}
		}
	}

	public void activatePower5() {
		if(this.getMana() >= 100) {
			if (this.getEffects().contains(StatusEffect.POISONED)) {
				activateRandomPower();
			} else {
				this.addStatusEffect(StatusEffect.FROZEN);
				this.takeMana(100);
			}
		}
	}

	private void activateRandomPower() {
		this.addStatusEffect(StatusEffect.values()[(int)(Math.random()*StatusEffect.values().length)]);
	}
	
	public void collide(Entity o) {
		if(o instanceof SquishBoy) {
			System.out.println("squish");
		}
		else if(o instanceof SpikeBoy) {
			this.addStatusEffect(StatusEffect.SPIKY);
			this.damage(20);
		}
	}

	public void activatePower6() {
		if(this.getMana() >= 100) {
			this.addStatusEffect(StatusEffect.BUBBLE);
			this.takeMana(100);
		}
	}


}
