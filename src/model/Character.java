package model;

public abstract class Character extends AbstractEntity {

	private double health;

	public Character(AABB aabb, int depth, double mass, Level level) {
		super(aabb, depth, mass, level);
		this.health = this.getMaxHealth();
	}

	public double getHealth(){ return health; }
	public int getMaxHealth(){ return 100;}
	public void damage(double damage){ this.health -= damage; }
}
