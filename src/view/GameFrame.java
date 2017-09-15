package view;

import model.Level;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    public final static char MOVE_RIGHT_KEY = 'd';
    public final static char MOVE_LEFT_KEY = 'a';
    public final static char JUMP_KEY = ' ';

    private GameEventListener listener;

    public GameFrame(Level level, GameEventListener listener) {
        super();
        this.add(new GamePanel(level, listener));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.listener = listener;

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
}
