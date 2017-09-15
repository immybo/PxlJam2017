package view;

import javax.swing.*;

public class HudPanel extends JPanel {
    private double playerHealth;

    public HudPanel(){
        this.playerHealth = 1.0;
    }

    public void setPlayerHealth(int currentHealth, int maxHealth) {
        if (maxHealth == 0) this.playerHealth = 1.0;
        else this.playerHealth = (currentHealth+0.0)/maxHealth;
    }
}
