package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DialogBoy extends Character {

	private String text;
	private double trigger;
	private double timer = 0;
	private double timestart = 0;

	public DialogBoy(double trigger, String string, Level level) {
		super(new AABB(new Vector(0,0), new Vector(0,0), null, null), -1, 0, level);
		this.text = string;
		this.trigger = trigger;
		
	}
	
	public void tick(double dt) {
		//super.tick(dt);
		timer += dt;
		if(timestart == 0 && getLevel().getPlayer().getAABB().center.x > trigger) {
			timestart = timer;
		}
		if(timestart + 3 < timer) {
			getLevel().removeEntity(this);
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(timestart == 0) {
			return;
		}
		
		double x = getLevel().getPlayer().getAABB().center.x;
		double y = getLevel().getPlayer().getAABB().min().y;
		g.setColor(Color.white);
		g.setFont(new Font("Helvetica", Font.BOLD, 18));
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
