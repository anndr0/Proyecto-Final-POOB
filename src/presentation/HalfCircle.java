package presentation;

import java.awt.*;
import javax.swing.*;

public class HalfCircle extends JPanel {
    private Color color1;
    private Color color2;

    public HalfCircle(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        double circleSizePercentage = 0.93;
        int circleSize = (int) (Math.min(width, height) * circleSizePercentage);

        int xOffset = (width - circleSize) / 2;
        int yOffset = (height - circleSize) / 2;

        // Dibuja la mitad superior del círculo en un color
        g.setColor(color1);
        g.fillArc(xOffset, yOffset, circleSize, circleSize, 0, 180);

        // Dibuja la mitad inferior del círculo en otro color
        g.setColor(color2);
        g.fillArc(xOffset, yOffset, circleSize, circleSize, 180, 180);
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("HalfCircle Test");
//            frame.setSize(400, 400);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            // Crea una instancia de HalfCircle con dos colores
//            Color color1 = Color.BLUE;
//            Color color2 = Color.GREEN;
//            HalfCircle halfCircle = new HalfCircle(color1, color2);
//
//            // Agrega la instancia de HalfCircle al contenido del JFrame
//            frame.getContentPane().add(halfCircle);
//
//            // Muestra el JFrame
//            frame.setVisible(true);
//        });
//    }
}
