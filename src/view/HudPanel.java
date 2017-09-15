package view;

import model.StatusEffect;
import sun.plugin.dom.exception.InvalidStateException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.Map;

public class HudPanel extends JPanel {
    private final static int MARGIN = 20;

    private double playerHealth;
    private double playerMana;
    private List<StatusEffect> playerStatuses;

    private Map<StatusEffect, Image> statusImageOn;
    private Map<StatusEffect, Image> statusImageOff;

    private Color healthPoolColor;
    private Color manaPoolColor;
    private Color hudBackgroundColor;

    public HudPanel(){
        this.playerHealth = 0.7;
        this.playerMana = 0.7;
        this.playerStatuses = new ArrayList<StatusEffect>();
        playerStatuses.add(StatusEffect.POISONED);
        loadStatusImages();
        this.healthPoolColor = Color.RED;
        this.manaPoolColor = Color.BLUE;
        this.hudBackgroundColor = Color.GRAY;
    }

    private void loadStatusImages() {
        try {
            statusImageOn.put(StatusEffect.POISONED, ImageIO.read(new File("resources/statusPoisonedOn.png")));
            statusImageOff.put(StatusEffect.POISONED, ImageIO.read(new File("resources/statusPoisonedOff.png")));
        } catch (IOException e) {
            throw new InvalidStateException("Unable to load status image " + e);
        }
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

        // Icons for current blessings
        int numBlessings = 1;
        drawBlessing(g, width, height, numBlessings, 0, StatusEffect.POISONED);

        // What is this, Diablo?
        g.setColor(manaPoolColor);
        g.fillOval(width-MARGIN-healthPoolSize, MARGIN, healthPoolSize, healthPoolSize);

        g.setColor(this.hudBackgroundColor);
        int lostManaSize = (int)(healthPoolSize * (1-playerMana));
        g.fillRect(width-MARGIN-healthPoolSize, MARGIN, healthPoolSize, lostManaSize);

        g.setColor(Color.BLACK);
        g.drawOval(width-MARGIN-healthPoolSize, MARGIN, healthPoolSize, healthPoolSize);
    }

    private void drawBlessing(Graphics g, int width, int height, int numBlessings, int i, StatusEffect blessing) {
        Image image = playerStatuses.contains(blessing) ? statusImageOn.get(blessing) : statusImageOff.get(blessing);
        int size = height/2;
        int left = (int)(width/2 - (numBlessings+0.0)/2 * size);

        g.drawImage(image, i * size + left, (height-size-2*MARGIN)/2, null);
    }
}
