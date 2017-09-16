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
	public void onPower1() { level.getPlayer().activatePower1(); } //do the thing
	public void onPower2() { level.getPlayer().activatePower2(); } //do the thing
	public void onPower3() { level.getPlayer().activatePower3(); } //do the thing
	public void onPower4() { level.getPlayer().activatePower4(); } //do the thing
	public void onPower5() { level.getPlayer().activatePower5(); } //do the thing

	public void setLevel(Level level) {
		this.level = level;
	}

    public void onMouseMove(boolean isRight) {
		level.getPlayer().turn(isRight);
    }
}
