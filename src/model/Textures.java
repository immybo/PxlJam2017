package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {
	public static final Image PLAYER_WALK_1 = read("characterWalk1.png");
	public static final Image PLAYER_WALK_2 = read("characterWalk2.png");
	public static final Image PLAYER_CRAWL_1 = read("characterCrawl1.png");
	public static final Image PLAYER_CRAWL_2 = read("characterCrawl2.png");
	public static final Image DIRT = read("dirt.png");
	public static final Image FREEZE_BOY = read("freezeBoy.png");
	public static final Image SQUISH_BOY = read("squishBoy.png");
	public static final Image BULLET = read("statusBubbleOn.png");
	
	private static Image read(String name) {
		Image i;
		try {
			i = ImageIO.read(new File("resources/" + name));
		} catch (IOException e) {
			throw new Error("Resource not found: " + name);
		}
		return i;
	}
}
