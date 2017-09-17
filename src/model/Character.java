package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Character extends AbstractEntity {
	public static final int SPIKE_DAMAGE = 100000;

	private Movement movement;

    protected enum Movement {
		MOVE_LEFT,
		MOVE_RIGHT,
		STATIONARY
	};

	private double health;
	private HashMap<StatusEffect, Long> statusTimeouts;
	private List<StatusEffect> effects;

	public Character(AABB aabb, int depth, double mass, Level level) {
		super(aabb, depth, mass, level);
		this.health = this.getMaxHealth();
		effects = new ArrayList<StatusEffect>();
		this.statusTimeouts = new HashMap<StatusEffect, Long>();
		for(StatusEffect s : StatusEffect.values()){
			this.statusTimeouts.put(s, new Long(0));
		}
		this.movement = Movement.STATIONARY;
	}

	protected void setMovement(Movement newMovement) {
		this.movement = newMovement;
	}

	protected Movement getMovement() {
		return this.movement;
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
	
	private boolean flat = false;

	public void setAABB(AABB newAABB) {
		this.flat = false;
		super.setAABB(newAABB);
	}
	
	@Override
	public void tick(double dt) {
		double ySpeed = this.getVelocity().y;
		double xSpeed = this.getVelocity().x;

		if (effects.contains(StatusEffect.BUBBLE))
			xSpeed = 0;
		else if (effects.contains(StatusEffect.FROZEN))
		{} // Do not change velocity when frozen
		else if(this.getMovement() == Movement.MOVE_RIGHT)
			xSpeed = this.effects.contains(StatusEffect.POISONED) ? -getMovementSpeed() * dt : getMovementSpeed() * dt;
		else if(this.getMovement() == Movement.MOVE_LEFT)
			xSpeed = this.effects.contains(StatusEffect.POISONED) ? getMovementSpeed() * dt : -getMovementSpeed() * dt;
		else
			xSpeed = 0;

		this.setVelocity(new Vector(xSpeed, ySpeed));

		if(effects.contains(StatusEffect.ON_FIRE)) {
			Vector vel = this.getVelocity();
			this.setVelocity(new Vector(vel.x*1.5, vel.y));
			this.damage(dt * 10);
		}
		if(effects.contains(StatusEffect.BROKEN_LEG)) {
			Vector vel = this.getVelocity();
			this.setVelocity(new Vector(vel.x*0.5, vel.y));
		}
		if(effects.contains(StatusEffect.DOT)) {
			this.damage(5 * dt);
		}
		for(StatusEffect s : StatusEffect.values()){
			if(this.statusTimeouts.get(s) <= System.currentTimeMillis()) {
				this.removeStatusEffect(s);
			}
		}
		
		if(this.getEffects().contains(StatusEffect.FLATTENED) && !this.flat) {
			this.flat = true;
			Vector ext = this.getAABB().extents;
			this.getAABB().extents = new Vector(ext.x, ext.y/2);
		} else if(!this.getEffects().contains(StatusEffect.FLATTENED) && this.flat) {
			this.flat = false;
			Vector ext = this.getAABB().extents;
			this.getAABB().extents = new Vector(ext.x, ext.y*2);
		}
		
		super.tick(dt);
	}

	public double getHealth(){ return health; }
	public int getMaxHealth(){ return 100;}
	public void heal(double healing) {
		this.health = this.getMaxHealth() <= this.getHealth() + healing ? this.getMaxHealth() : this.getHealth()+healing;
	}
	public boolean legBroken(){ return effects.contains(StatusEffect.BROKEN_LEG); }

	public void damage(double damage){
		if(!effects.contains(StatusEffect.FROZEN))
			this.health -= damage;
		if(this.health <0) this.health = 0;
	}

	public abstract int getMovementSpeed();
}
