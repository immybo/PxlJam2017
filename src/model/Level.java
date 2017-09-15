package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Level {
	private Player player;
	private List<Entity> entities;

	public Level(List<Entity> entities) {
		for (Entity e : entities) {
			if (e instanceof Player) {
				this.player = (Player)e;
				break;
			}
		}
		this.entities = entities;
	}

	public void tick(float dt) {
		throw new NotImplementedException();
	}
	public void restart() {
		throw new NotImplementedException();
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	public Player getPlayer() {
		return player;
	}
}
