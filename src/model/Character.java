package model;

public interface Character extends Entity {
	public int getHealth();
	public int getMaxHealth();
	
	public int applyForce(double x, double y);
}
