package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DialogBoy extends Character {
	private static DialogBoy currentBoy = null;
	
	private String text;
	private double trigger;
	private double timer = 0;
	private double timestart = 0;
	private double length;

	public DialogBoy(double trigger, double length, String string, Level level) {
		super(new AABB(new Vector(0,0), new Vector(0,0), null, null), -1, 0, level);
		this.text = string;
		this.trigger = trigger;
		this.length = length;
		
	}
	
	public void tick(double dt) {
		//super.tick(dt);
		timer += dt;
		if(timestart == 0 && getLevel().getPlayer().getAABB().center.x > trigger) {
			timestart = timer;
			if(currentBoy != null) {
				getLevel().removeEntity(currentBoy);
			}
			currentBoy = this;
		}
		if(timestart + length < timer) {
			getLevel().removeEntity(this);
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(timestart == 0) {
			return;
		}
		
		int sheight = 18;
		
		g.setFont(new Font("Helvetica", Font.BOLD, sheight));
		
		double swidth = g.getFontMetrics().stringWidth(text);
		double cx =getLevel().getPlayer().getAABB().center.x;
		double x = cx - swidth/2;
		double y = getLevel().getPlayer().getAABB().min().y - 30;

		g.setColor(Color.black);
		g.fillRect((int) cx, (int) y + 20, 4, 4);
		g.fillRect((int) cx + 10, (int) y + 10, 6, 6);
		g.fillRect((int) x, (int) y - sheight, (int) swidth + 4, (int) sheight + 6);
		g.setColor(Color.white);
		g.drawString(text, (int) x, (int) y);
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public int getMovementSpeed() {
		return 0;
	}

}
