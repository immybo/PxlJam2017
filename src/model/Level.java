package model;

import java.awt.geom.Area;
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
			for(Entity o : entities) {
				if(e == o) {
					continue;
				}
				Area ebb = new Area(e.getCollisionBox());
				Area obb = new Area(o.getCollisionBox());
				ebb.intersect(obb);
				if(!ebb.isEmpty()) {
					e.setVelocity(0, 0);
					o.setVelocity(0, 0);
				}
			}
		}
		this.entities = entities;
	}

	public void tick(float dt) {
		for (Entity e : entities) {
			e.tick(dt);
		}
	}
	public void restart() {
		throw new Error("not implemented");
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	public Player getPlayer() {
		return player;
	}
}
