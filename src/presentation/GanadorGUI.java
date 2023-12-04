package presentation;

import javax.swing.*;
import java.awt.*;

public class GanadorGUI extends JFrame{
    public GanadorGUI(String player) {
        setTitle("Ganador :3");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel backgroundPanel = new ImagePanel("./images/img.jpg");

        JLabel titleLabel = new JLabel("WINNER!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 40));
        titleLabel.setForeground(Color.BLACK);

        JLabel winnerLabel = new JLabel(player, SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 20));
        winnerLabel.setForeground(Color.BLACK);

        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 5, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(titleLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(winnerLabel, gbc);


        add(backgroundPanel);
    }
}
