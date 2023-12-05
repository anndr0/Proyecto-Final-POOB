package domain;

import java.util.Random;

public class CasillaTeleport extends Casilla {
    @Override
    public void aplicarEfecto(Board board, int row, int col) {
        // Teletransporta a la piedra a otro lugar aleatorio en el tablero
        Random random = new Random();
        int newRow, newCol;

        do {
            newRow = random.nextInt(board.getSize());
            newCol = random.nextInt(board.getSize());
        } while (board.getBoardState()[newRow][newCol] != 0);

        // Mueve la piedra a la nueva posición
        board.getBoardState()[newRow][newCol] = board.getBoardState()[row][col];
        board.getPiedrasState()[newRow][newCol] = board.getPiedrasState()[row][col];

        // Elimina la piedra original asignando 0 en su posición original
        board.getBoardState()[row][col] = 0;
        board.getPiedrasState()[row][col] = null;
    }
}
