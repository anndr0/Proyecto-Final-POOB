package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CirculoConContorno extends JFrame {
    private boolean isSelected = false;

    public CirculoConContorno() {
        setTitle("Círculo con Contorno");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                int diameterOuter = 150;
                int borderWidthOuter = 10;
                int xOuter = getWidth() / 2 - diameterOuter / 2;
                int yOuter = getHeight() / 2 - diameterOuter / 2;

                // Dibuja el círculo exterior con contorno blanco
                g2d.setColor(Color.WHITE);
                g2d.fillOval(xOuter, yOuter, diameterOuter, diameterOuter);

                int diameterInner = diameterOuter * 2 / 3; // Ajusta el tamaño del círculo rojo aquí
                int xInner = getWidth() / 2 - diameterInner / 2;
                int yInner = getHeight() / 2 - diameterInner / 2;

                g2d.fillOval(xInner, yInner, diameterInner, diameterInner);

                // Agrega un contorno negro alrededor del círculo rojo (si está seleccionado)
                if (isSelected) {
                    int borderWidthInner = 3; // Ajusta el ancho del contorno negro
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(borderWidthInner));
                    g2d.drawOval(xInner, yInner, diameterInner, diameterInner);
                }
            }
        };

        // Agrega un MouseListener para manejar eventos de clic
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSelected = !isSelected; // Invierte el estado de selección al hacer clic
                panel.repaint(); // Vuelve a pintar el panel para reflejar los cambios
            }
        });

        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CirculoConContorno().setVisible(true);
        });
    }
}
