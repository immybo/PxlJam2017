package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ControllerListener;


public class Level {
	protected Player player;
	protected List<Entity> entities;
	protected List<Entity> entitiesToAdd;
	protected List<Entity> entitiesToRemove;
	private ControllerListener controllerListener;
	public File levelFile;
	Vector min;
	Vector max;

	public Level(File levelFile) {
		this.levelFile = levelFile;
		this.entities = new ArrayList<Entity>();
		this.entitiesToAdd = new ArrayList<Entity>();
		this.entitiesToRemove = new ArrayList<Entity>();
		this.min = Vector.zero();
		this.max = Vector.zero();
	}

	public void setControllerListener(ControllerListener listener) {
		this.controllerListener = listener;
	}

	public void addEntity(Entity newEntity) {
		entitiesToAdd.add(newEntity);
		if(newEntity.getAABB().min().x < min.x)
			min = new Vector(newEntity.getAABB().min().x, min.y);
		if(newEntity.getAABB().min().y < min.y)
			min = new Vector(min.x, newEntity.getAABB().min().y);
		if(newEntity.getAABB().max().x > max.x)
			max = new Vector(newEntity.getAABB().max().x, max.y);
		if(newEntity.getAABB().max().y > max.y)
			max = new Vector(max.x, newEntity.getAABB().max().y);
		System.out.println(min.x+" "+min.y+" "+max.x+" "+max.y);
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

		// Did we finish level?
		if (this.getPlayer().getAABB().center.x >= this.max.x - 100) {
			controllerListener.onLevelFinish();
			return;
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
							((Character)ent).damage(((Bullet) e).getDamage());
							((Player)ent).addStatusEffect(((Bullet) e).effect);
							this.removeEntity(e);
						}
						else if(ent instanceof Enemy && ((Bullet) e).friendler){
							((Character)ent).damage(((Bullet) e).getDamage());
							((Character)ent).addStatusEffect(((Bullet) e).effect);
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
					double velY = e instanceof Player ? e.getVelocity().y : Double.MIN_VALUE;
					AABB.doMove(e.getAABB(), f.getAABB(), dt);

					double newY = e.getAABB().center.y;

					if (e instanceof Player) {
						if (velY > 0) {
							// If we're going fast enough, break our legs...
							if (velY > 14) { //Magic number velocity we are have to be going to break leg
								getPlayer().addStatusEffect(StatusEffect.BROKEN_LEG);
							}
							getPlayer().setOnGround(true);
						}

						if (f instanceof PoissonBoy) {
							entitiesToRemove.add(f); // BOOM
							player.addStatusEffect(StatusEffect.POISONED);
						}
					}

					if (e instanceof Character) {
						// If one of the characters is spiky, damage the other one
						if (((Character) e).getEffects().contains(StatusEffect.SPIKY)) {
							if (f instanceof Character) {
								((Character) f).damage(Character.SPIKE_DAMAGE);
							}
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
		Level level = new Level(file);
		try {
			Scanner reader = new Scanner(file);
			String levelName = reader.next();
			String att;
			while (reader.hasNext()) {
				att = reader.next();
				if (att.equals("PLAYER-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					Player player = new Player(new AABB(spawn, new Vector(15,25).mult(1.5), null, null), 0, level);
					level.addEntity((player));
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
						double bounciness = 1.0;
						int depth = 0;
						Wall wall = new Wall(new AABB(point, extents, null, null), Textures.DIRT, bounciness, depth, level);
						level.addEntity(wall);
					}
				}
				else if (att.equals("FREEZEBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					FreezeBoy freezeBoy = new FreezeBoy(new AABB(spawn, new Vector(15,25), null, null), 0, 100, level);
					level.addEntity(freezeBoy);
					reader.nextLine();
				}
				else if (att.equals("BURNBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					BurnBoy burnBoy= new BurnBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					level.addEntity(burnBoy);
					reader.nextLine();
				}
				else if (att.equals("SPIKEBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					SpikeBoy spikeBoy= new SpikeBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					level.addEntity(spikeBoy);
					reader.nextLine();
				}
				else if (att.equals("SQUISHBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					SquishBoy squishBoy= new SquishBoy(new AABB(spawn, new Vector(25,25).mult(10), null, null), 0, 100, level);
					level.addEntity(squishBoy);
					reader.nextLine();
				}
				else if (att.equals("POISSONBOY-SPAWN:")){
					Vector spawn = new Vector(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					PoissonBoy squishBoy= new PoissonBoy(new AABB(spawn, new Vector(25,25), null, null), 0, 100, level);
					level.addEntity(squishBoy);
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

	public Level restart() {
		return Level.buildLevel(levelFile);
	}
}
