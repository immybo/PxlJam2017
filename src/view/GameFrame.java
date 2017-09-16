package view;

import controller.ControllerListener;
import model.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GameFrame extends JFrame implements ControllerListener {
    public final static char MOVE_RIGHT_KEY = 'd';
    public final static char MOVE_LEFT_KEY = 'a';
    public final static char JUMP_KEY = ' ';
    public final static char RESTART_KEY = 'r';

    private GameEventListener listener;

    private Level[] levels;
    private int levelIndex;

    public GameFrame(Level[] levels, GameEventListener listener) {
        super();
        this.levels = levels;
        this.levelIndex = 0;

        this.listener = listener;
        this.listener.setLevel(levels[levelIndex]);

        restart();


        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Turn the player around based on where the mouse is
                boolean isRight = e.getX() > getWidth()/4;
                listener.onMouseMove(isRight);
            }
        });

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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public Level getLevel() {
        return levels[levelIndex];
    }

    public void restart() {
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();

        ActionListener startLevelAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLevel(levelIndex);
                pack();
            }
        };
        showOverlayMessage("Level " + levelIndex, Color.WHITE, 2000, startLevelAction);
    }

    public void setLevel(int newIndex) {
        levels[levelIndex] = levels[levelIndex].restart();
        add(new GamePanel(levels[levelIndex], listener));
        this.levelIndex = newIndex;
        levels[levelIndex].setControllerListener(this);
        this.listener.setLevel(levels[levelIndex]);
    }

    @Override
    public void onPlayerDeath() {
        JOptionPane.showMessageDialog(this, "YOU DED BOI");
        this.getLevel().getPlayer().damage(-100); // HACKETY HACK :)
        restart();
    }

    @Override
    public void onLevelFinish() {
        levelIndex++;

        if (levelIndex == levels.length) {
            JOptionPane.showMessageDialog(this, "You've finished all the levels. Click OK to start again.");
            levelIndex = 0;
        }

        restart();
    }

    private void showOverlayMessage(String message, Color color, int time, ActionListener listener) {
        JPanel labelPanel = new LabelPanel(message, color);
        labelPanel.setPreferredSize(new Dimension(1000, 800));
        add(labelPanel);
        revalidate();
        Timer t = new javax.swing.Timer(time, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                remove(labelPanel);
                revalidate();
                listener.actionPerformed(e);
            }
        });
        t.setRepeats(false);
        t.start();
    }
}
