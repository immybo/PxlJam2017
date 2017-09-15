package controller;

import model.Entity;
import model.Level;
import model.Player;
import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main {
	public static void main(String[] args) {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(new Player(new Point2D.Double(50, 50), new Rectangle(0, 0, 50, 50), 0));
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	Level level = new Level(entities);
		    	GameFrame gf = new GameFrame(level, null);
				
		    	int delay = 16;
		    	ActionListener taskPerformer = new ActionListener() {
		    		public void actionPerformed(ActionEvent evt) {
		    			level.tick(((float)delay)/1000f);
		    			gf.repaint();
		    		}
			  };
			  new Timer(delay, taskPerformer).start();
		    }
		});
	}
}
