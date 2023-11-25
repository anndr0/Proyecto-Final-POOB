package domain;

public class Board {
    private int[][] boardState;
    private int boardSize;

    public Board(int size) {
        boardSize = size;
        boardState = new int[boardSize][boardSize];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardState[i][j] = 0;
            }
        }
        printBoard();
    }

    public void resetBoard() {
        initializeBoard();
    }
    public int[][] getBoardState() {
        return boardState;
    }

    public void makeMove(int row, int col, int playerNumber) {
        if (boardState[row][col] == 0) {
            boardState[row][col] = playerNumber;
            printBoard();
        }
    }

    public int checkWinner() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize-4; j++) {
                if (boardState[i][j] != 0 &&
                        boardState[i][j] == boardState[i][j + 1] &&
                        boardState[i][j] == boardState[i][j + 2] &&
                        boardState[i][j] == boardState[i][j + 3] &&
                        boardState[i][j] == boardState[i][j + 4]) {
                    return boardState[i][j];
                }
            }
        }

        // Implementa la lógica para verificar las columnas
        for (int i = 0; i < boardSize-4; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (boardState[i][j] != 0 &&
                        boardState[i][j] == boardState[i + 1][j] &&
                        boardState[i][j] == boardState[i + 2][j] &&
                        boardState[i][j] == boardState[i + 3][j] &&
                        boardState[i][j] == boardState[i + 4][j]) {
                    return boardState[i][j];
                }
            }
        }

        // Implementa la lógica para verificar las diagonales
        for (int i = 0; i < boardSize-4; i++) {
            for (int j = 0; j < boardSize-4; j++) {
                if (boardState[i][j] != 0 &&
                        (boardState[i][j] == boardState[i + 1][j + 1] &&
                                boardState[i][j] == boardState[i + 2][j + 2] &&
                                boardState[i][j] == boardState[i + 3][j + 3] &&
                                boardState[i][j] == boardState[i + 4][j + 4])) {
                    return boardState[i][j];
                }

                if (boardState[i + 4][j] != 0 &&
                        (boardState[i + 4][j] == boardState[i + 3][j + 1] &&
                                boardState[i + 4][j] == boardState[i + 2][j + 2] &&
                                boardState[i + 4][j] == boardState[i + 1][j + 3] &&
                                boardState[i + 4][j] == boardState[i][j + 4])) {
                    return boardState[i + 4][j];
                }
            }
        }

        // No hay ganador
        return 0;
    }

    public int convertCoordinatesToIndex(int row, int col) {
        // Convertir las coordenadas de fila y columna a un índice único
        return row * 15 + col;
    }

    public void printBoard() {
        System.out.println("Matriz de estado de juego");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(boardState[i][j] + " ");
            }
            System.out.println();
        }
    }
}
