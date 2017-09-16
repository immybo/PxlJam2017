package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabelPanel extends JPanel {
    private JLabel jLabel;

    public LabelPanel(String label, Color color) {
        this.setLayout(new BorderLayout());

        Font font = new Font("Helvetica", Font.BOLD, 100);

        this.jLabel = new JLabel(label);
        this.jLabel.setFont(font);
        this.jLabel.setForeground(color);

        this.jLabel.setVerticalTextPosition(JLabel.CENTER);
        this.jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(this.jLabel, BorderLayout.CENTER);

        this.setBackground(Color.BLACK);
        this.setOpaque(true);

        Timer t = new javax.swing.Timer(150, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                jLabel.setForeground(jLabel.getForeground().darker());
            }
        });
        t.start();
    }
}
