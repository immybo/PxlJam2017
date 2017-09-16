package view;

import controller.ControllerListener;
import model.Level;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GameFrame extends JFrame implements ControllerListener {
    public final static char MOVE_RIGHT_KEY = 'd';
    public final static char MOVE_LEFT_KEY = 'a';
    public final static char JUMP_KEY = ' ';
    public final static char RESTART_KEY = 'r';

    private GameEventListener listener;

    private Level level;

    public GameFrame(Level level, GameEventListener listener) {
        super();
        this.level = level;

        this.listener = listener;
        restart();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                boolean isRight = e.getX() > getWidth()/4;
                listener.onMousePress(isRight);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

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
        Level level = Level.buildLevel(this.level.levelFile);
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
        JOptionPane.showMessageDialog(this, "YOU DED BOI");
        restart();
    }
}
