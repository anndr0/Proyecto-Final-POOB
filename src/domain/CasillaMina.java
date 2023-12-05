package domain;

public class CasillaMina extends Casilla {
    @Override
    public void aplicarEfecto(Board board, int row, int col) {
        // explota en un espacio 3x3 eliminando todas las piedras en este espacio >.<
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < board.getSize() && j >= 0 && j < board.getSize()) {
                    // elimina la piedra en la posición (i, j)
                    board.getBoardState()[i][j] = 0;  // O simplemente asigna 0 para indicar la ausencia de piedra
                    board.getPiedrasState()[i][j] = null;  // O asigna null para indicar la ausencia de piedra
                }
            }
        }
    }
}
