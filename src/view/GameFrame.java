package view;

import controller.ControllerListener;
import model.Level;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class GameFrame extends JFrame implements ControllerListener {
    public final static char MOVE_RIGHT_KEY = 'd';
    public final static char MOVE_LEFT_KEY = 'a';
    public final static char JUMP_KEY = ' ';
    public final static char RESTART_KEY = 'r';

    private GameEventListener listener;

    private String levelPath;
    private Level level;

    public GameFrame(String levelPath, GameEventListener listener) {
        super();

        this.listener = listener;
        this.levelPath = levelPath;
        restart();

        this.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {

                switch (Character.toLowerCase(e.getKeyChar())) {
                    case MOVE_RIGHT_KEY:
                        listener.onRightPressed();
                        break;
                    case MOVE_LEFT_KEY:
                        listener.onLeftPressed();
                        break;
                    case JUMP_KEY:
                        listener.onJumpPressed();
                        break;
                    case RESTART_KEY:
                        restart();
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyTyped(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (Character.toLowerCase(e.getKeyChar())) {
                    case MOVE_RIGHT_KEY:
                        listener.onRightReleased();
                        break;
                    case MOVE_LEFT_KEY:
                        listener.onLeftReleased();
                        break;
                }
            }
        });
    }

    public Level getLevel() {
        return level;
    }

    public void restart() {
        Level level = Level.buildLevel(new File(levelPath));
        setLevel(level);
    }

    public void setLevel(Level level) {
        this.level = level;
        level.setControllerListener(this);
        this.listener.setLevel(level);
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
        this.add(new GamePanel(level, listener));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void onPlayerDeath() {
        restart();
    }
}
