package view;

import model.Level;

public class GameEventListener {
	private Level level;
	
	public GameEventListener(Level level) {
		this.level = level;
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
}
