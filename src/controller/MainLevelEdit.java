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

public class MainLevelEdit {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	Level level = Level.buildLevel(new File("resources/levels/testLevel"));
				GameFrame gf = new GameFrame(level, new GameEventListener());
		    	int delay = 16;
		    	ActionListener taskPerformer = new ActionListener() {
		    		public void actionPerformed(ActionEvent evt) {
						Level level = gf.getLevel();
		    			level.tick(((float)delay)/1000f);
		    			gf.repaint();
		    		}
			  };
			  new Timer(delay, taskPerformer).start();
		    }
		});
	}
}
