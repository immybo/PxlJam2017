package model;

import java.util.List;

public interface Level {
	public void tick(float dt);
	public void restart();
	
	public List<Entity> getEntities();
	public Player getPlayer();
}
