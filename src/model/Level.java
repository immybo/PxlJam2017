package model;

import org.omg.PortableInterceptor.INACTIVE;
import sun.management.counter.perf.PerfLongArrayCounter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

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
		for (Entity e : entities) {
			e.tick(dt);
		}
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

	public static Level buildLevel(File file){
		ArrayList entities = new ArrayList();
		try {
			Scanner reader = new Scanner(file);
			String levelName = reader.next();
			String att;
			while (reader.hasNext()) {
				att = reader.next();
				if (att.equals("PLAYER-SPAWN:")){
					Point2D spawn = new Point2D.Double(Double.parseDouble(reader.next()), Double.parseDouble(reader.next()));
					Player player = new Player(spawn, new Rectangle(0, 0, 50, 50), 0);
					entities.add((player));
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

						Point2D point = new Point2D.Double((double) x, (double) y);
						Shape hitbox = new Rectangle(0,0,width,height);
						BufferedImage texture = ImageIO.read(new File("resources/dirt.png"));
						double bounciness = 1.0;
						int depth = 0;
						Wall wall = new Wall(point, hitbox, texture, bounciness, depth);
						entities.add(wall);
					}
				}
			}
			reader.close();
			return new Level(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
