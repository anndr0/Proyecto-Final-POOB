package presentation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PieceClickListener extends MouseAdapter {
    private GomokuGUI gomokuGUI;
    private int row;
    private int col;
    private boolean isClickInProgress = false;



    public PieceClickListener(GomokuGUI gomokuGUI, int row, int col) {
        this.gomokuGUI = gomokuGUI;
        this.row = row;
        this.col = col;
    }
    public void setIsClickInProgress(boolean value) {
        isClickInProgress = value;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (!isClickInProgress) {
            isClickInProgress = true;
            int selectedPieceType = gomokuGUI.getSelectedPieceType();
            gomokuGUI.handlePieceClick(row, col, selectedPieceType);
        }
    }

}
