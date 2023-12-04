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

        g.setColor(color1);
        g.fillArc(xOffset, yOffset, circleSize, circleSize, 0, 180);

        g.setColor(color2);
        g.fillArc(xOffset, yOffset, circleSize, circleSize, 180, 180);
    }
}
