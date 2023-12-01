package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public abstract class Player {
    protected int playerNumber;
    protected List<Integer> fichas;
    protected long tiempoInvertido;
    protected int puntaje;
    protected int row; // Nueva variable
    protected int col; // Nueva variable

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.fichas = new ArrayList<>();
        this.tiempoInvertido = 0;
        this.puntaje = 0;
        inicializarFichas();
    }

    public void setMove(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public List<Integer> getFichas() {
        return fichas;
    }

    public long getTiempoInvertido() {
        return tiempoInvertido;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void inicializarFichas() {
        List<Integer> tiposPosibles = new ArrayList<>();
        if (playerNumber == 1) {
            Collections.addAll(tiposPosibles, 1, 3, 5);
        } else if (playerNumber == 2) {
            Collections.addAll(tiposPosibles, 2, 4, 6);
        }

        for (int i = 0; i < 50; i++) {
            Collections.shuffle(tiposPosibles);
            int tipo = tiposPosibles.get(0);
            fichas.add(tipo);
        }

//        imprimirFichas();
    }
    public void reiniciarFichas() {
        fichas.clear();  // Limpiar la lista de fichas
        inicializarFichas();  // Volver a inicializar las fichas
    }
    public abstract int makeMove(Board board);

    // Método para contar el número de fichas de cada tipo
    public List<Integer> contarFichas() {
        List<Integer> contadorFichas = new ArrayList<>(Collections.nCopies(3, 0));

        for (int ficha : fichas) {
            if (ficha == 1 || ficha == 2) {  // Ficha normal
                contadorFichas.set(0, contadorFichas.get(0) + 1);
            } else if (ficha == 3 || ficha == 4) {  // Ficha pesada
                contadorFichas.set(1, contadorFichas.get(1) + 1);
            } else if (ficha == 5 || ficha == 6) {  // Ficha temporal
                contadorFichas.set(2, contadorFichas.get(2) + 1);
            }
        }

        return contadorFichas;
    }
    public int getContadorFichasNormales() {
        return contarFichasNormales();
    }

    public int getContadorFichasPesadas() {
        return contarFichasPesadas();
    }

    public int getContadorFichasTemporales() {
        return contarFichasTemporales();
    }

    public int contarFichasNormales() {
        int contadorFichasNormales = 0;

        for (int ficha : fichas) {
            if (ficha == 1 || ficha == 2) {  // Ficha normal
                contadorFichasNormales++;
            }
        }

        return contadorFichasNormales;
    }

    public int contarFichasPesadas() {
        int contadorFichasPesadas = 0;

        for (int ficha : fichas) {
            if (ficha == 3 || ficha == 4) {  // Ficha pesada
                contadorFichasPesadas++;
            }
        }

        return contadorFichasPesadas;
    }

    public int contarFichasTemporales() {
        int contadorFichasTemporales = 0;

        for (int ficha : fichas) {
            if (ficha == 5 || ficha == 6) {  // Ficha temporal
                contadorFichasTemporales++;
            }
        }

        return contadorFichasTemporales;
    }
    public int getPlayerNumber() {
        return playerNumber;
    }



    public void imprimirFichas() {
        System.out.println("Fichas del Jugador " + playerNumber + ": ");
        for (int ficha : fichas) {
            System.out.print(ficha + " ");
        }
        System.out.println();
    }
}
