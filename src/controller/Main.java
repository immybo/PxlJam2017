package controller;

import model.Entity;
import model.Level;
import model.Player;
import view.GameEventListener;
import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main {
	public static final String[] LEVELS = {

			"resources/levels/aLevelOfSorts",
			"resources/levels/breakLeg.txt",
			"resources/levels/squish.txt",
			"resources/levels/fire.txt", 
			"resources/levels/bubble.txt",
			"resources/levels/freeze.txt",
			"resources/level/poison.txt",
	};

	public static void main(String[] args) {
		SoundPlayer.playMusic("resources/backgroundMusic.wav");
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				Level[] levels = new Level[LEVELS.length];
				for (int i = 0; i < LEVELS.length; i++) {
					levels[i] = Level.buildLevel(new File(LEVELS[i]));
				}
				GameFrame gf = new GameFrame(levels, new GameEventListener());
		    	int delay = 16;
		    	ActionListener taskPerformer = new ActionListener() {
		    		public void actionPerformed(ActionEvent evt) {
		    			if (gf.isRestarting) return;
						Level level = gf.getLevel();
						level.tick(((float)delay)/1000f);
		    			gf.repaint();

		    			if (level.getPlayer().getAABB().center.y > 5000) {
		    				gf.onPlayerDeath();
						}
		    		}
			  };
			  new Timer(delay, taskPerformer).start();
		    }
		});
	}
}
