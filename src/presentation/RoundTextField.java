package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;

public class RoundTextField extends JTextField {
    private Color roundBackgroundColor = Color.BLACK;
    private int cornerRadius = 15;

    public RoundTextField(String text) {
        super(text);
        setOpaque(false);
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE);
        setBorder(null); // Deshabilitar el borde predeterminado
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear una forma redondeada
        Shape roundRect = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        g2d.setColor(roundBackgroundColor);
        g2d.fill(roundRect);

        super.paintComponent(g2d);

        g2d.dispose();
    }
}
