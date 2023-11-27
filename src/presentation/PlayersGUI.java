package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayersGUI extends JFrame{
    public PlayersGUI() {
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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
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

        JLabel player1Label = new JLabel("PLAYER 1");
        JLabel player2Label = new JLabel("PLAYER 2");
        player1Label.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 15));
        player2Label.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 15));

        player1Label.setForeground(Color.BLACK);
        player2Label.setForeground(Color.BLACK);


        RoundTextField p1TextField = new RoundTextField(" ");
        RoundTextField p2TextField = new RoundTextField(" ");

        Dimension textFieldSize = new Dimension(180, 30);
        p1TextField.setPreferredSize(textFieldSize);
        p2TextField.setPreferredSize(textFieldSize);


        RoundButton continueButton = new RoundButton("CONTINUE");
        continueButton.setBackground(Color.BLACK);
        continueButton.setForeground(Color.WHITE);

        Dimension buttonSize = new Dimension(100, 30);
        continueButton.setPreferredSize(buttonSize);


        ColorChooserButton normalColorButtonP1 = new ColorChooserButton(Color.BLACK, 'N');
        ColorChooserButton normalColorButtonP2 = new ColorChooserButton(Color.WHITE,'N');
        ColorChooserButton pesadaColorButtonP1 = new ColorChooserButton(new Color(134, 1, 175, 255),'P');
        ColorChooserButton pesadaColorButtonP2 = new ColorChooserButton(new Color(167, 25, 75,255),'P');
        ColorChooserButton tempColorButtonP1 = new ColorChooserButton(new Color(2, 71, 254,255),'T');
        ColorChooserButton tempColorButtonP2 = new ColorChooserButton(new Color(3, 146, 206, 255),'T');

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String player1Name = p1TextField.getText().trim();
                String player2Name = p2TextField.getText().trim();

                // Verificar que hay contenido en las cajas de texto
                if (player1Name.isEmpty() || player2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese nombres de jugadores en ambas cajas de texto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // No continuar si falta información
                }

                // Verificar que los nombres de los jugadores sean diferentes
                if (player1Name.equals(player2Name)) {
                    JOptionPane.showMessageDialog(null, "Los nombres de los jugadores deben ser diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // No continuar si los nombres son iguales
                }

                // Verificar que los colores no son iguales entre jugadores
                Color normalColorP1 = normalColorButtonP1.getSelectedColor();
                Color normalColorP2 = normalColorButtonP2.getSelectedColor();
                Color pesadaColorP1 = pesadaColorButtonP1.getSelectedColor();
                Color pesadaColorP2 = pesadaColorButtonP2.getSelectedColor();
                Color tempColorP1 = tempColorButtonP1.getSelectedColor();
                Color tempColorP2 = tempColorButtonP2.getSelectedColor();

                if (normalColorP1.equals(normalColorP2) || pesadaColorP1.equals(pesadaColorP2) || tempColorP1.equals(tempColorP2)) {
                    JOptionPane.showMessageDialog(null, "Los colores seleccionados deben ser diferentes para cada jugador.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // No continuar si los colores son iguales
                }

                // Verificar que las fichas de un solo jugador sean diferentes
                if (normalColorP1.equals(pesadaColorP1) || normalColorP1.equals(tempColorP1) || pesadaColorP1.equals(tempColorP1)) {
                    JOptionPane.showMessageDialog(null, "Las fichas seleccionadas para el jugador 1 deben ser diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // No continuar si las fichas son iguales para el jugador 1
                }

                if (normalColorP2.equals(pesadaColorP2) || normalColorP2.equals(tempColorP2) || pesadaColorP2.equals(tempColorP2)) {
                    JOptionPane.showMessageDialog(null, "Las fichas seleccionadas para el jugador 2 deben ser diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // No continuar si las fichas son iguales para el jugador 2
                }

                dispose();
                continuar(player1Name, player2Name, normalColorP1, normalColorP2, pesadaColorP1, pesadaColorP2, tempColorP1, tempColorP2);
            }
        });

        outerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(centerTitleLabel, gbc);

        gbc.insets = new Insets(0, 0, 10, 20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        outerPanel.add(player1Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(p1TextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(normalColorButtonP1, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(pesadaColorButtonP1, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(tempColorButtonP1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        outerPanel.add(player2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(p2TextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(normalColorButtonP2, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(pesadaColorButtonP2, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(tempColorButtonP2, gbc);

        gbc.insets = new Insets(20, 0, 10, -50);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        outerPanel.add(continueButton, gbc);

        return outerPanel;
    }


    private void volverInicio() {
        ChooseGUI inicio = new ChooseGUI();
        inicio.setVisible(true);
    }

    private void continuar(String player1Name, String player2Name, Color normal1Color,Color normal2Color,Color pesada1Color, Color pesada2Color,Color temp1Color,Color temp2Color ) {
        GomokuGUI inicio = new GomokuGUI(player1Name, player2Name, normal1Color, normal2Color, pesada1Color, pesada2Color,temp1Color,temp2Color);
        inicio.setVisible(true);
    }
    private JLabel createCenterTitleLabel() {
        JLabel centerTitleLabel = new JLabel("PLAYERS", SwingConstants.CENTER);
        centerTitleLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 50));
        centerTitleLabel.setForeground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.add(centerTitleLabel);

        return centerTitleLabel;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                PlayersGUI inicio = new PlayersGUI();
//                inicio.setVisible(true);
//            }
//        });
//    }
}
