package domain;

public class Board {
    private int[][] boardState;

    private Piedra[][] piedrasState;  // Nueva matriz para las instancias de las piedras
    private int boardSize;

    public Board(int size) {
        boardSize = size;
        boardState = new int[boardSize][boardSize];
        piedrasState = new Piedra[boardSize][boardSize];  // Inicialización de la nueva matriz

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

    public void makeMove(int row, int col, int piedraType) {
        if (boardState[row][col] == 0) {
            // Crear una nueva piedra según el tipo
            Piedra piedra = crearPiedraSegunTipo(piedraType);

            // Colocar la piedra en la matriz de estados
            boardState[row][col] = piedraType;

            // Colocar la piedra en la matriz de piedras
            piedrasState[row][col] = piedra;

            disminuirTurnoTemporales();

            printBoard();  // Puedes eliminar o ajustar esto según tus necesidades
//            imprimirPiedrasState();
        }
    }

    private void disminuirTurnoTemporales(){
        // Restar un turno a las piedras temporales
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (piedrasState[i][j] instanceof PiedraTemporal) {
                    PiedraTemporal piedraTemporal = (PiedraTemporal) piedrasState[i][j];
                    piedraTemporal.disminuirTurno();
                    if (piedraTemporal.getTurnosRestantes() == 0) {
                        // Si se han agotado los turnos, eliminar la piedra temporal
                        boardState[i][j] = 0;
                        piedrasState[i][j] = null;
                    }
                }
            }
        }

    }

    private Piedra crearPiedraSegunTipo(int piedraType) {
        switch (piedraType) {
            case 1:
            case 2:
                return new PiedraNormal(piedraType);
            case 3:
            case 4:
                return new PiedraPesada(piedraType);
            case 5:
            case 6:
                return new PiedraTemporal(piedraType);
            default:
                throw new IllegalArgumentException("Tipo de piedra no válido: " + piedraType);
        }
    }

    public int checkWinner() {
        // Implementa la lógica para verificar las filas
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize - 4; j++) {
                int player1Count = 0;
                int player2Count = 0;

                for (int k = 0; k < 5; k++) {
                    int currentCell = boardState[i][j + k];

                    // Incrementa el contador del jugador correspondiente
                    if (currentCell == 1 || currentCell == 3 || currentCell == 5) {
                        player1Count++;
                    } else if (currentCell == 2 || currentCell == 4 || currentCell == 6) {
                        player2Count++;
                    }
                }

                // Verifica si hay un ganador en la fila
                if (player1Count == 5) {
                    return 1;  // Jugador 1 gana
                } else if (player2Count == 5) {
                    return 2;  // Jugador 2 gana
                }
            }
        }

        // Implementa la lógica para verificar las columnas
        for (int i = 0; i < boardSize - 4; i++) {
            for (int j = 0; j < boardSize; j++) {
                int player1Count = 0;
                int player2Count = 0;

                for (int k = 0; k < 5; k++) {
                    int currentCell = boardState[i + k][j];

                    // Incrementa el contador del jugador correspondiente
                    if (currentCell == 1 || currentCell == 3 || currentCell == 5) {
                        player1Count++;
                    } else if (currentCell == 2 || currentCell == 4 || currentCell == 6) {
                        player2Count++;
                    }
                }

                // Verifica si hay un ganador en la columna
                if (player1Count == 5) {
                    return 1;  // Jugador 1 gana
                } else if (player2Count == 5) {
                    return 2;  // Jugador 2 gana
                }
            }
        }

        // Implementa la lógica para verificar las diagonales
        for (int i = 0; i < boardSize - 4; i++) {
            for (int j = 0; j < boardSize - 4; j++) {
                int player1Count = 0;
                int player2Count = 0;

                for (int k = 0; k < 5; k++) {
                    int currentCell = boardState[i + k][j + k];

                    // Incrementa el contador del jugador correspondiente
                    if (currentCell == 1 || currentCell == 3 || currentCell == 5) {
                        player1Count++;
                    } else if (currentCell == 2 || currentCell == 4 || currentCell == 6) {
                        player2Count++;
                    }
                }

                // Verifica si hay un ganador en la diagonal
                if (player1Count == 5) {
                    return 1;  // Jugador 1 gana
                } else if (player2Count == 5) {
                    return 2;  // Jugador 2 gana
                }

                player1Count = 0;
                player2Count = 0;

                for (int k = 0; k < 5; k++) {
                    int currentCell = boardState[i + 4 - k][j + k];

                    // Incrementa el contador del jugador correspondiente
                    if (currentCell == 1 || currentCell == 3 || currentCell == 5) {
                        player1Count++;
                    } else if (currentCell == 2 || currentCell == 4 || currentCell == 6) {
                        player2Count++;
                    }
                }

                // Verifica si hay un ganador en la diagonal inversa
                if (player1Count == 5) {
                    return 1;  // Jugador 1 gana
                } else if (player2Count == 5) {
                    return 2;  // Jugador 2 gana
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

    public void imprimirPiedrasState() {
        System.out.println("Matriz de piedrasState:");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (piedrasState[i][j] != null) {
                    System.out.print(piedrasState[i][j].getTipo() + " ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}
