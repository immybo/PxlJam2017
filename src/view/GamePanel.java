package view;

import javax.swing.*;
import model.Level;

import java.awt.*;

public class GamePanel extends JPanel {
    private final Level level;
    private final GameEventListener listener;
    private final JPanel hudPanel;

    public GamePanel(Level level, GameEventListener listener) {
        super();

        this.level = level;
        this.listener = listener;

        this.hudPanel = new HudPanel();

        this.setPreferredSize(new Dimension(500, 500));
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
