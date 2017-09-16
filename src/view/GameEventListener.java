package view;

import model.Level;
import model.Vector;

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
	public void onMousePress(boolean isRight) { level.getPlayer().shoot(isRight); }

	public void setLevel(Level level) {
		this.level = level;
	}

    public void onMouseMove(boolean isRight) {
		level.getPlayer().turn(isRight);
    }
}
