package view;

import model.*;

import javax.swing.*;
import java.awt.*;

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
    }
}
