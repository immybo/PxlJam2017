package model;

import controller.ControllerListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Level {
	private Player player;
	private List<Entity> entities;
	private List<Entity> entitiesToAdd;
	private List<Entity> entitiesToRemove;
	private ControllerListener controllerListener;

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

	public void setControllerListener(ControllerListener listener) {
		this.controllerListener = listener;
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
		// Did we ded?
		if (this.getPlayer().getHealth() <= 0) {
			controllerListener.onPlayerDeath();
		}

		// Did enemies ded?
		for (Entity e : entities) {
			if (e instanceof Enemy) {
				if (((Enemy)e).getHealth() <= 0) {
					entitiesToRemove.add(e);
				}
			}
		}

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
			e.applyForce(new Vector(0, e.getMass()*0.7)); //0.7 magic number to reduce gravity
			e.tick(dt);
			if(e instanceof Bullet){
				for(Entity ent : entities){
					double fraction = AABB.getIntersectionFraction(ent.getAABB(), e.getAABB(), dt);
					if(Double.isFinite(fraction)){
						if(ent instanceof Block && ent.isSolid())
							this.removeEntity(e);
						else if(ent instanceof Player && !((Bullet) e).friendler){
							((Player)ent).addStatusEffect(((Bullet) e).effect);
							((Character)ent).damage(((Bullet) e).getDamage());
							this.removeEntity(e);
						}
						else if(ent instanceof Enemy && ((Bullet) e).friendler){
							((Character)ent).damage(((Bullet) e).getDamage());
							this.removeEntity(e);
						}

					}
				}
			}

			if(e.isSolid()) {
				Entity f = null;
				double d = Double.POSITIVE_INFINITY;
				for (Entity o : entities) {
					if (e == o || !o.isSolid()) {
						continue;
					}
					double intF = AABB.getIntersectionFraction(e.getAABB(), o.getAABB(), dt);
					if (intF < d) {
						f = o;
						d = intF;
					}
				}
				if (f != null) {
					// If we're the player, and we're colliding with something below us,
					// we must be on the ground.
					double currentY = e.getAABB().center.y;

					AABB.doMove(e.getAABB(), f.getAABB(), dt);

					double newY = e.getAABB().center.y;

					if (e instanceof Player) {
						if (newY <= currentY) {
							// If we're going fast enough, break our legs...
							if (currentY - newY >= 3) {
								getPlayer().addStatusEffect(StatusEffect.BROKEN_LEG);
							}
							getPlayer().setOnGround(true);
						}

						// If we're spiky and it's an enemy, damage it
						if (((Player)e).getEffects().contains(StatusEffect.SPIKY)) {
							if (f instanceof Enemy) {
								((Enemy)f).damage(Player.SPIKE_DAMAGE);
							}
						}

						if (f instanceof PoissonBoy) {
							entitiesToRemove.add(f); // BOOM
							player.addStatusEffect(StatusEffect.POISONED);
						}
					}
				}
			}
		}
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
					Player player = new Player(new AABB(spawn, new Vector(15,25), null, null), 0, level, ImageIO.read(new File("resources/characterWalk1.png")));
					entities.add((player));
					level.setPlayer(player);
					reader.nextLine();
				}
				else if (att.equals("WALL:")){
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
				else if (att.equals("FREEZEBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					FreezeBoy freezeBoy = new FreezeBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					entities.add(freezeBoy);
					reader.nextLine();
				}
				else if (att.equals("BURNBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					BurnBoy burnBoy= new BurnBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					entities.add(burnBoy);
					reader.nextLine();
				}
				else if (att.equals("SPIKEBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					SpikeBoy spikeBoy= new SpikeBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					entities.add(spikeBoy);
					reader.nextLine();
				}
				else if (att.equals("SQUISHBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					SquishBoy squishBoy= new SquishBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					entities.add(squishBoy);
					reader.nextLine();
				}
				else if (att.equals("POISSONBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					PoissonBoy squishBoy= new PoissonBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					entities.add(squishBoy);
					reader.nextLine();
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
