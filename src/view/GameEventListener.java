package view;

import model.Level;
import model.Vector;

import java.io.File;

public class GameEventListener {
	private Level level;
	
	public GameEventListener() {
	}

	public boolean isLevelNull(){return level==null;}

	public void onLeftPressed() {
		if(isLevelNull())return;
		level.getPlayer().moveLeft();
	}
	public void onRightPressed() {
		if(isLevelNull())return;
		level.getPlayer().moveRight();
	}
	public void onJumpPressed() {
		if(isLevelNull())return;
		level.getPlayer().jump();
	}
	public void onLeftReleased() {
		if(isLevelNull())return;
		level.getPlayer().stopLeftMovement();
	}
	public void onRightReleased() {
		if(isLevelNull())return;
		level.getPlayer().stopRightMovement();}
	public void onMousePress(boolean isRight) {
		if(isLevelNull())return;
		level.getPlayer().shoot(isRight);
	}
	public void onPower1() {
		if(isLevelNull())return;
		level.getPlayer().activatePower1();
	} //do the thing
	public void onPower2() {
		if(isLevelNull())return;
		level.getPlayer().activatePower2();
	} //do the thing
	public void onPower3() {
		if(isLevelNull())return;
		level.getPlayer().activatePower3();
	} //do the thing
	public void onPower4() {
		if(isLevelNull())return;
		level.getPlayer().activatePower4();
	} //do the thing
	public void onPower5() {
		if(isLevelNull())return;
		level.getPlayer().activatePower5();
	} //do the thing
	public void onPower6() {
		if(isLevelNull())return;
		level.getPlayer().activatePower6();
	} //do the thing

	public void setLevel(Level level) {
		this.level = level;
	}

    public void onMouseMove(boolean isRight) {
		if(isLevelNull())return;
		level.getPlayer().turn(isRight);
    }
}
