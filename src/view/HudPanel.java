package view;

import javax.swing.*;
import java.awt.*;

public class HudPanel extends JPanel {
    private final static int MARGIN = 20;

    private double playerHealth;
    private Color healthPoolColor;
    private Color hudBackgroundColor;

    public HudPanel(){
        this.playerHealth = 0.7;
        this.healthPoolColor = Color.RED;
        this.hudBackgroundColor = Color.GRAY;
    }

    public void setPlayerHealth(int currentHealth, int maxHealth) {
        if (maxHealth == 0) this.playerHealth = 1.0;
        else this.playerHealth = (currentHealth+0.0)/maxHealth;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(this.hudBackgroundColor);
        g.fillRect(0, 0, width, height);

        int healthPoolSize = height - MARGIN*2;
        g.setColor(healthPoolColor);
        g.fillOval(MARGIN, MARGIN, healthPoolSize, healthPoolSize);

        // Draw a rectangle with the background color over the top bit of it...
        g.setColor(this.hudBackgroundColor);
        int lostHealthSize = (int)(healthPoolSize * (1-playerHealth));
        g.fillRect(MARGIN, MARGIN, healthPoolSize, lostHealthSize);

        // Outline
        g.setColor(Color.BLACK);
        g.drawOval(MARGIN, MARGIN, healthPoolSize, healthPoolSize);
    }
}
