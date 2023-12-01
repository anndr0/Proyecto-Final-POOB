package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (boardState[i][j] == 0) {
                    // Si alguna casilla está vacía, el tablero no está lleno
                    return false;
                }
            }
        }
        // Si ninguna casilla está vacía, el tablero está lleno
        return true;
    }

    private void resetPiedrasState() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                piedrasState[i][j] = null;
            }
        }
    }

    public void resetBoard() {
        initializeBoard();
        resetPiedrasState();
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

            printBoard();
            imprimirPiedrasState();

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
                int sumaJugador1 = 0;
                int sumaJugador2 = 0;

                for (int k = 0; k < 5; k++) {
                    int tipo = boardState[i][j + k];
                    if (tipo == 1 || tipo == 3 || tipo == 5) {
                        sumaJugador1 += piedrasState[i][j + k].getValor();
                    } else if (tipo == 2 || tipo == 4 || tipo == 6) {
                        sumaJugador2 += piedrasState[i][j + k].getValor();
                    }
                }

                if (sumaJugador1 == 5) {
                    return 1;  // Jugador 1 gana
                } else if (sumaJugador2 == 5) {
                    return 2;  // Jugador 2 gana
                }
            }
        }

        // Implementa la lógica para verificar las columnas
        for (int i = 0; i < boardSize - 4; i++) {
            for (int j = 0; j < boardSize; j++) {
                int sumaJugador1 = 0;
                int sumaJugador2 = 0;

                for (int k = 0; k < 5; k++) {
                    int tipo = boardState[i + k][j];
                    if (tipo == 1 || tipo == 3 || tipo == 5) {
                        sumaJugador1 += piedrasState[i + k][j].getValor();
                    } else if (tipo == 2 || tipo == 4 || tipo == 6) {
                        sumaJugador2 += piedrasState[i + k][j].getValor();
                    }
                }

                if (sumaJugador1 == 5) {
                    return 1;  // Jugador 1 gana
                } else if (sumaJugador2 == 5) {
                    return 2;  // Jugador 2 gana
                }
            }
        }

        // Implementa la lógica para verificar las diagonales
        for (int i = 0; i < boardSize - 4; i++) {
            for (int j = 0; j < boardSize - 4; j++) {
                int sumaJugador1 = 0;
                int sumaJugador2 = 0;

                for (int k = 0; k < 5; k++) {
                    int tipo = boardState[i + k][j + k];
                    if (tipo == 1 || tipo == 3 || tipo == 5) {
                        sumaJugador1 += piedrasState[i + k][j + k].getValor();
                    } else if (tipo == 2 || tipo == 4 || tipo == 6) {
                        sumaJugador2 += piedrasState[i + k][j + k].getValor();
                    }
                }

                if (sumaJugador1 == 5) {
                    return 1;  // Jugador 1 gana
                } else if (sumaJugador2 == 5) {
                    return 2;  // Jugador 2 gana
                }

                sumaJugador1 = 0;
                sumaJugador2 = 0;

                for (int k = 0; k < 5; k++) {
                    int tipo = boardState[i + 4 - k][j + k];
                    if (tipo == 1 || tipo == 3 || tipo == 5) {
                        sumaJugador1 += piedrasState[i + 4 - k][j + k].getValor();
                    } else if (tipo == 2 || tipo == 4 || tipo == 6) {
                        sumaJugador2 += piedrasState[i + 4 - k][j + k].getValor();
                    }
                }

                if (sumaJugador1 == 5) {
                    return 1;  // Jugador 1 gana
                } else if (sumaJugador2 == 5) {
                    return 2;  // Jugador 2 gana
                }
            }
        }
        // No hay ganador
        return 0;
    }


    private int getPlayerNumberFromPiedra(Piedra piedra) {
        return piedra != null ? piedra.getTipo() : 0;
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
