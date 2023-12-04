package presentation;

import javax.swing.*;
import java.awt.*;

public class PiecePanel extends JPanel {
    private Color pieceColor;
    public PiecePanel(Color color) {
        this.pieceColor = color;
        setOpaque(false);
    }

    public void setColor(Color color) {
        this.pieceColor = color;
        repaint();
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

        g2d.setColor(pieceColor);
        g2d.fillOval(xOffset, yOffset, circleSize, circleSize);

//        int diameterInner = circleSize * 2 / 3; // Ajusta el tamaño del círculo rojo aquí
//        int xInner = getWidth() / 2 - diameterInner / 2;
//        int yInner = getHeight() / 2 - diameterInner / 2;
//
//        g2d.fillOval(xInner, yInner, diameterInner, diameterInner);
    }

}
