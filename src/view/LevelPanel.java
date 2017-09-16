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

        // Translate to get the effect of moving
        Vector playerPosition = level.getPlayer().getAABB().center;
        int playerTranslateX = (int)(width/4 - playerPosition.x);
        int playerTranslateY = (int)(height/2 - playerPosition.y);
        g.translate(playerTranslateX, playerTranslateY);

        for (Entity e : this.level.getEntities()) {
            e.render(g);
        }
    }
}
