package domain;

import java.util.Random;

public class Board {
    private int[][] boardState;
    private Casilla[][] casillasEspeciales;
    private Piedra[][] piedrasState;
    private int size;

    public Board(int size) {
        this.size = size;
        boardState = new int[this.size][this.size];
        piedrasState = new Piedra[this.size][this.size];
        casillasEspeciales = new Casilla[this.size][this.size];
        initializeBoard();
        inicializarCasillasEspecialesAleatorias(0.5);

    }

    public void setCasillaEspecial(int row, int col, Casilla casilla) {
        casillasEspeciales[row][col] = casilla;
    }
    public void inicializarCasillasEspecialesAleatorias(double porcentajeCasillasEspeciales) {
        Random random = new Random();
        int cantidadCasillasEspeciales = (int) (size * size * porcentajeCasillasEspeciales);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cantidadCasillasEspeciales > 0 && random.nextInt(size * size) < cantidadCasillasEspeciales) {
                    // 0 para Mina, 1 para Teleport, 2 para Golden
                    int tipoEspecial = random.nextInt(3);
                    switch (tipoEspecial) {
                        case 0:
                            casillasEspeciales[i][j] = new CasillaMina();
                            break;
                        case 1:
                            casillasEspeciales[i][j] = new CasillaTeleport();
                            break;
//                        case 2:
//                            casillasEspeciales[i][j] = new CasillaGolden();
//                            break;
                    }
                    cantidadCasillasEspeciales--;
                } else {
                    // normal
                    casillasEspeciales[i][j] = new CasillaNormal();
                }
            }
        }
        printCasillasEspeciales();
    }


    public boolean isMine(int row, int col) {
        Casilla casilla = casillasEspeciales[row][col];
        return casilla instanceof CasillaMina;
    }


    public void printCasillasEspeciales() {
        System.out.println("Matriz de Casillas Especiales:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (casillasEspeciales[i][j] != null) {
                    System.out.print(casillasEspeciales[i][j].getClass().getSimpleName() + " ");
                } else {
                    System.out.print("null ");
                }
            }
            System.out.println();
        }
    }

    public void aplicarEfectoCasillaEspecial(int row, int col) {
        Casilla casilla = casillasEspeciales[row][col];
        if (casilla != null) {
            casilla.aplicarEfecto(this, row, col);
        }
    }
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardState[i][j] = 0;
            }
        }
        printBoard();
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
    public Piedra[][] getPiedrasState() {
        return piedrasState;
    }
    public Casilla[][] getCasillasEspeciales() {
        return casillasEspeciales;
    }

    public int getSize(){
        return size;
    }

    public void makeMove(int row, int col, int piedraType) {
        if (boardState[row][col] == 0) {
            Piedra piedra = crearPiedraSegunTipo(piedraType);
            boardState[row][col] = piedraType;
            piedrasState[row][col] = piedra;
            disminuirTurnoTemporales();

            printBoard();
            imprimirPiedrasState();
            printCasillasEspeciales();
        }
        Casilla casillaEspecial = casillasEspeciales[row][col];
        if (casillaEspecial != null) {
            casillaEspecial.aplicarEfecto(this, row, col);
            casillasEspeciales[row][col] = new CasillaNormal();
        }
    }


    private void disminuirTurnoTemporales(){
        // Restar un turno a las piedras temporales
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 4; j++) {
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
        for (int i = 0; i < size - 4; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int i = 0; i < size - 4; i++) {
            for (int j = 0; j < size - 4; j++) {
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(boardState[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void imprimirPiedrasState() {
        System.out.println("Matriz de piedrasState:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
