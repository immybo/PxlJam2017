package view;

import model.Level;

import java.io.File;

public class GameEventListener {
	private Level level;
	
	public GameEventListener() {
	}
	
	public void onLeftPressed() {
		level.getPlayer().moveLeft();
	}
	public void onRightPressed() {
		level.getPlayer().moveRight();
	}
	public void onJumpPressed() {
		level.getPlayer().jump();
	}
	public void onLeftReleased() { level.getPlayer().stopLeftMovement();}
	public void onRightReleased() { level.getPlayer().stopRightMovement();}

	public void setLevel(Level level) {
		this.level = level;
	}
}
