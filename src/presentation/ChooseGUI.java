package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseGUI extends JFrame {
    public ChooseGUI() {
        setTitle("POOB");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // FONDO
        ImagePanel backgroundPanel = new ImagePanel("./images/img.jpg");

        JPanel outerPanel = createOuterPanel();

        RoundButton backButton = new RoundButton("<");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                volverInicio();
            }
        });
        // BOTON IZQUIERDA
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panel.add(backButton);
        panel.setOpaque(false);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(panel, BorderLayout.WEST);
        backgroundPanel.add(outerPanel);
        add(backgroundPanel);
    }

    private JPanel createOuterPanel() {
        JPanel outerPanel = new JPanel(); // panel
        outerPanel.setOpaque(false);

        JLabel centerTitleLabel = createCenterTitleLabel();

        RoundButton newGame = new RoundButton("NEW GAME");
        RoundButton savedGames = new RoundButton("SAVED GAMES");
        // Establecer el tamaño deseado para los botones
        Dimension buttonSize = new Dimension(150, 30);
        newGame.setPreferredSize(buttonSize);
        savedGames.setPreferredSize(buttonSize);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                abrirJuego();
            }
        });

        newGame.setBackground(Color.BLACK);
        newGame.setForeground(Color.WHITE);
        savedGames.setBackground(Color.BLACK);
        savedGames.setForeground(Color.WHITE);

        outerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 70);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        outerPanel.add(centerTitleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0; // Evitar que el botón se expanda horizontalmente
        outerPanel.add(newGame, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0; // Evitar que el botón se expanda horizontalmente
        outerPanel.add(savedGames, gbc);

        return outerPanel;
    }

    private JLabel createCenterTitleLabel() {
        JLabel centerTitleLabel = new JLabel("CHOOSE", SwingConstants.CENTER);
        centerTitleLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 50));
        centerTitleLabel.setForeground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.add(centerTitleLabel);

        return centerTitleLabel;
    }

    private void abrirJuego() {
        PlayersGUI players = new PlayersGUI();
        players.setVisible(true);
    }

    private void volverInicio() {
        InicioGUI inicio = new InicioGUI();
        inicio.setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ChooseGUI inicio = new ChooseGUI();
//                inicio.setVisible(true);
//            }
//        });
//    }

}
