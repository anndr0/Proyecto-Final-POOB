package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChooserButton extends JButton {
    private Color selectedColor;
    private char buttonText; // Letra en el centro del círculo

    public ColorChooserButton(Color initialColor, char buttonText) {
        this.selectedColor = initialColor;
        this.buttonText = buttonText;
        setPreferredSize(new Dimension(30, 30));
        setBorderPainted(false);
        setContentAreaFilled(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Color", selectedColor);
                if (newColor != null) {
                    setSelectedColor(newColor);
                }
            }
        });
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(selectedColor);
        g2d.fillOval(0, 0, getWidth(), getHeight());

        // Cambiar el color del texto en función del color del círculo
        if (selectedColor.equals(Color.BLACK)) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(Color.BLACK);
        }

        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(String.valueOf(buttonText))) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(String.valueOf(buttonText), x, y);
        g2d.setColor(selectedColor);
        g2d.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.dispose();
    }


    // Ejemplo de uso
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Color Chooser Button");
                frame.setSize(300, 200);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel panel = new JPanel();
                ColorChooserButton colorChooserButton = new ColorChooserButton(Color.RED, 'A');
                panel.add(colorChooserButton);

                frame.add(panel);
                frame.setVisible(true);
            }
        });
    }
}
