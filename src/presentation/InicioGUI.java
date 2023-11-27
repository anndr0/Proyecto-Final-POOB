package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioGUI extends JFrame {
    public InicioGUI() {
        setTitle("POOB");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImagePanel backgroundPanel = new ImagePanel("./images/img.jpg");

        JLabel titleLabel = new JLabel("GOMOKU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 50));
        titleLabel.setForeground(Color.BLACK);


        RoundButton startButton = new RoundButton("START");
        RoundButton rulesButton = new RoundButton("RULES");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                abrirJuego();
            }
        });

        startButton.setPreferredSize(new Dimension(100, 30));
        rulesButton.setPreferredSize(new Dimension(100, 30));

        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        rulesButton.setBackground(Color.BLACK);
        rulesButton.setForeground(Color.WHITE);

        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        backgroundPanel.add(startButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        backgroundPanel.add(rulesButton, gbc);


        add(backgroundPanel);
    }

    private void abrirJuego() {
        ChooseGUI inicio = new ChooseGUI();
        inicio.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InicioGUI inicio = new InicioGUI();
                inicio.setVisible(true);
            }
        });
    }
}
