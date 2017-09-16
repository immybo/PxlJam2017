package model;

public abstract class Character extends AbstractEntity {

	private int health;

	public Character(AABB aabb, int depth, double mass, Level level) {
		super(aabb, depth, mass, level);
		this.health = this.getMaxHealth();
	}

	public int getHealth(){ return health; }
	public int getMaxHealth(){ return 100;}
	public void damage(int damage){ this.health -= damage; }
}
