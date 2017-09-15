package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class LevelPanel extends JPanel {
    private static final Color backgroundColor = Color.BLUE;

    private final Level level;

    public LevelPanel(Level level) {
        this.level = level;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, width, height);

        for (Entity e : this.level.getEntities()) {
            e.render(g);
        }

        // Translate to get the effect of moving
        Point2D playerPosition = level.getPlayer().getPosition();
        int playerTranslateX = (int)(width/2 - playerPosition.getX());
        int playerTranslateY = (int)(height/2 - playerPosition.getY());
        g.translate(playerTranslateX, playerTranslateY);
    }
}
