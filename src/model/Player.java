package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Player extends AbstractEntity implements Character {
	private static final int JUMP_FORCE = 10;
	private static final int MOVE_FORCE = 10;

	private static final int MAX_HORIZONTAL_SPEED = 10;

	private List<StatusEffect> effects;

	public Player(Point2D position, Shape collisionBox, int depth) {
		super(position, collisionBox, depth, 10);
		effects = new ArrayList<StatusEffect>();
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
		if (this.getXSpeed() > -MAX_HORIZONTAL_SPEED) {
			this.applyForce(-MOVE_FORCE, 0);
		}
	}
	public void moveRight() {
		if (this.getXSpeed() < MAX_HORIZONTAL_SPEED) {
			this.applyForce(MOVE_FORCE, 0);
		}
	}
	public void jump() {
		// This is just if they're moving down;
		// TODO FIXME
		if (Math.abs(this.getYSpeed()) >= 0) {
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
}
