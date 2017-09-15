package view;

import model.Level;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(Level level, GameEventListener listener) {
        super();
        this.add(new GamePanel(level, listener));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
