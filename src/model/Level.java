package model;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;

public class Level {
	private Player player;
	private List<Entity> entities;
	private List<Entity> entitiesToAdd;
	private List<Entity> entitiesToRemove;

	public Level(List<Entity> entities) {
		for (Entity e : entities) {
			if (e instanceof Player) {
				this.player = (Player)e;
				break;
			}
		}
		this.entities = entities;
		this.entitiesToAdd = new ArrayList<Entity>();
		this.entitiesToRemove = new ArrayList<Entity>();
	}

	public void addEntity(Entity newEntity) {
		entitiesToAdd.add(newEntity);
	}

	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}

	private void setPlayer(Player p) {
		this.player = p;
	}


	public void tick(float dt) {
		for (Entity e : entitiesToAdd) {
			entities.add(e);
		}
		entitiesToAdd.clear();
		for (Entity e : entitiesToRemove) {
			entities.remove(e);
		}
		entitiesToRemove.clear();

		this.getPlayer().setOnGround(false);

		if (this.getPlayer().getJumpNextTick()) {
			this.getPlayer().applyForce(new Vector(0, -Player.JUMP_FORCE));
			this.getPlayer().setJumpNextTick(false);
		}

		for (Entity e : entities) {
			double currentY = e.getAABB().center.y;
			e.applyForce(new Vector(0, 1));
			e.tick(dt);
			Entity f = null;
			double d = Double.POSITIVE_INFINITY;
			for(Entity o : entities) {
				if(e == o) {
					continue;
				}
				double intF = AABB.getIntersectionFraction(e.getAABB(), o.getAABB(), dt);
				if(intF < d) {
					f = o;
					d = intF;
				}
			}
			if(f != null) {
				// If we're the player, and we're colliding with something below us,
				// we must be on the ground.

				AABB.doMove(e.getAABB(), f.getAABB(), dt);

				double newY = e.getAABB().center.y;

				if (e instanceof Player) {
					if (newY <= currentY) {
						getPlayer().setOnGround(true);

					}
				}
			}
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

	public static Level buildLevel(File file){
		ArrayList entities = new ArrayList();
		Level level = new Level(entities);
		try {
			Scanner reader = new Scanner(file);
			String levelName = reader.next();
			String att;
			while (reader.hasNext()) {
				att = reader.next();
				if (att.equals("PLAYER-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					Player player = new Player(new AABB(spawn, new Vector(25,25), null, null), 0, level);
					entities.add((player));
					level.setPlayer(player);
					reader.nextLine();
				}
				if (att.equals("WALL:")){
					String next = reader.next();
					while(!next.equals(";")){
						//Read wall entity dimensions
						int x1 = Integer.parseInt(next);
						int y1 = reader.nextInt();
						reader.next();
						int x2 = reader.nextInt();
						int y2 = reader.nextInt();
						next = reader.next();
						//Create wall entity
						int x = Math.min(x1, x2);
						int y = Math.min(y1, y2);
						int width = Math.max(x1, x2) - x;
						int height = Math.max(y1, y2) - y;

						Vector point = new Vector((double) x, (double) y);
						Vector extents = new Vector(width/2,height/2);
						BufferedImage texture = ImageIO.read(new File("resources/dirt.png"));
						double bounciness = 1.0;
						int depth = 0;
						Wall wall = new Wall(new AABB(point, extents, null, null), texture, bounciness, depth, level);
						entities.add(wall);
					}
				}
			}
			reader.close();
			return level;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
