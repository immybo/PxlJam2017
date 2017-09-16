package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Character extends AbstractEntity {

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
	
	@Override
	public void tick(double dt) {
		if (effects.contains(StatusEffect.BUBBLE))
			this.setVelocity(new Vector(0, this.getVelocity().y));
		else if (effects.contains(StatusEffect.FROZEN))
			this.setVelocity(new Vector(this.getVelocity().x, this.getVelocity().y));

		if(effects.contains(StatusEffect.ON_FIRE)) {
			Vector vel = this.getVelocity();
			this.setVelocity(new Vector(vel.x*1.5, vel.y));
			this.damage(dt * 10);
		}
		
		for(StatusEffect s : StatusEffect.values()){
			if(this.statusTimeouts.get(s) <= System.currentTimeMillis()) {
				this.removeStatusEffect(s);
			}
		}
		super.tick(dt);
	}

	public double getHealth(){ return health; }
	public int getMaxHealth(){ return 100;}
	public void damage(double damage){ this.health -= damage; }
}
