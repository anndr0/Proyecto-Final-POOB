package presentation;

import javax.swing.*;
import java.awt.*;

public class EmpateGUI extends JFrame {
    public EmpateGUI() {
        setTitle("POOB");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel backgroundPanel = new ImagePanel("./images/img.jpg");

        JLabel titleLabel = new JLabel("¡EMPATE!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 40));
        titleLabel.setForeground(Color.BLACK);

        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(titleLabel, gbc);
        add(backgroundPanel);
    }
}
