package view;

import javax.swing.*;
import model.Level;

import java.awt.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final double HUD_HEIGHT = 0.2;

    private final Level level;
    private final GameEventListener listener;
    private final JPanel hudPanel;
    private final JPanel levelPanel;

    public GamePanel(Level level, GameEventListener listener) {
        super();

        this.level = level;
        this.listener = listener;

        this.setLayout(new BorderLayout());

        this.hudPanel = new HudPanel(level.getPlayer());
        hudPanel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT*HUD_HEIGHT)));
        this.add(hudPanel, BorderLayout.SOUTH);

        this.levelPanel = new LevelPanel(level);
        levelPanel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT*(1-HUD_HEIGHT))));
        this.add(levelPanel, BorderLayout.NORTH);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.repaint();
    }
}
