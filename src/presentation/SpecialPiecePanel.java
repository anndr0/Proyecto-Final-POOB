package presentation;
import java.awt.*;
import javax.swing.JPanel;

public class SpecialPiecePanel extends JPanel {
    public SpecialPiecePanel(Color color) {
        Color backgroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 180);
        setBackground(backgroundColor);
    }

        private float opacity = 0.1f; // Opacidad predeterminada

        // Otros métodos y constructores...

        public void setOpacity(float opacity) {
            this.opacity = opacity;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

            // Dibuja el resto de los elementos en tu SpecialPiecePanel aquí...

            g2d.dispose();
        }
    }


