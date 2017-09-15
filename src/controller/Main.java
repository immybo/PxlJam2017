package controller;

import model.Entity;
import model.Level;
import model.Player;
import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(new Player(new Point2D.Double(50, 50), new Rectangle(0, 0, 50, 50), 0));

		new GameFrame(new Level(entities), null);
	}
}
