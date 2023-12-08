package domain;

import java.util.Random;

public class Board {
    private Casilla[][] casillasEspeciales;
    private Piedra[][] piedrasState;
    private int size;

    public Board(int size) {
        this.size = size;
        piedrasState = new Piedra[this.size][this.size];
        casillasEspeciales = new Casilla[this.size][this.size];
        inicializarCasillasEspecialesAleatorias(0.5);
    }

    public Piedra[][] getBoardState() {
        return piedrasState;
    }

    public void setCasillaEspecial(int row, int col, Casilla casilla) {
        casillasEspeciales[row][col] = casilla;
    }
    public Casilla[][] getCasillasEspeciales() {
        return casillasEspeciales;
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
                        case 2:
                            casillasEspeciales[i][j] = new CasillaGolden();
                            break;
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

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (piedrasState[i][j] == null) {
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

    private void resetCasillasState() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                casillasEspeciales[i][j] = null;
            }
        }
    }

    public void resetBoard() {
        resetPiedrasState();
        resetCasillasState();
    }
    public Piedra[][] getPiedrasState() {
        return piedrasState;
    }
    public void reiniciarCasillas(double porcentajeEspeciales) {
        resetCasillasState();
        reiniciarCasillasEspeciales(porcentajeEspeciales);
    }

    public void reiniciarCasillasEspeciales(double porcentaje) {
        inicializarCasillasEspecialesAleatorias(porcentaje);
    }

    public int getSize(){
        return size;
    }

    public void makeMove(int row, int col, Piedra piedra) {

        if (piedrasState[row][col] == null && piedra != null) {
            piedrasState[row][col] = piedra;
            disminuirTurnoTemporales();
            imprimirPiedrasState();
        }
        Casilla casillaEspecial = casillasEspeciales[row][col];
        if (casillaEspecial != null) {
            casillaEspecial.aplicarEfecto(this, row, col);
            casillasEspeciales[row][col] = new CasillaNormal();
        }
        printCasillasEspeciales();
    }
    public boolean isMine(int row, int col) {
        Casilla casilla = casillasEspeciales[row][col];
        return casilla instanceof CasillaMina;
    }
    public boolean isTel(int row, int col) {
        Casilla casilla = casillasEspeciales[row][col];
        return casilla instanceof CasillaTeleport;
    }
    public boolean isGolden(int row, int col) {
        Casilla casilla = casillasEspeciales[row][col];
        return casilla instanceof CasillaGolden;
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
                        piedrasState[i][j] = null;
                    }
                }
            }
        }

    }


    public int checkWinner() {
        // Implementa la lógica para verificar las filas
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 4; j++) {
                int sumaJugador1 = 0;
                int sumaJugador2 = 0;

                for (int k = 0; k < 5; k++) {
                    Piedra piedra = piedrasState[i][j + k];
                    if (piedra != null && piedra.getJugador() == 1) {
                        sumaJugador1 += piedra.getValor();
                    } else if (piedra != null && piedra.getJugador() == 2) {
                        sumaJugador2 += piedra.getValor();
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
                    Piedra piedra = piedrasState[i + k][j];
                    if (piedra != null && piedra.getJugador() == 1) {
                        sumaJugador1 += piedra.getValor();
                    } else if (piedra != null && piedra.getJugador() == 2) {
                        sumaJugador2 += piedra.getValor();
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
                    Piedra piedra = piedrasState[i + k][j + k];
                    if (piedra != null && piedra.getJugador() == 1) {
                        sumaJugador1 += piedra.getValor();
                    } else if (piedra != null && piedra.getJugador() == 2) {
                        sumaJugador2 += piedra.getValor();
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
                    Piedra piedra = piedrasState[i + 4 - k][j + k];
                    if (piedra != null && piedra.getJugador() == 1) {
                        sumaJugador1 += piedra.getValor();
                    } else if (piedra != null && piedra.getJugador() == 2) {
                        sumaJugador2 += piedra.getValor();
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

    public int convertCoordinatesToIndex(int row, int col) {
        // Convertir las coordenadas de fila y columna a un índice único
        return row * 15 + col;
    }

    public void imprimirPiedrasState() {
        System.out.println("Matriz de piedrasState:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (piedrasState[i][j] != null) {
                    System.out.print(piedrasState[i][j].getJugador() + " ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}
