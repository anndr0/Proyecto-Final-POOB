package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.HashMap;

public abstract class Player {
    protected int playerNumber;
    protected ArrayList<Piedra> piedras;
    protected long tiempoInvertido;
    private boolean nextTurnNormal = false;

    protected int puntaje;
    protected int row;
    protected int col;

    public Player(int playerNumber, int boardSize) {
        this.playerNumber = playerNumber;
        this.piedras = new ArrayList<>();
        this.tiempoInvertido = 0;
        this.puntaje = 0;
        this.nextTurnNormal = false;

        inicializarPiedras(boardSize, 0.25);
    }
    public void setNextTurnNormal(boolean value) {
        nextTurnNormal = value;
    }

    public boolean shouldPlaceNormalInNextTurn() {
        return nextTurnNormal;
    }
    public void setMove(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public ArrayList<Piedra> getPiedras() {
        return piedras;
    }

    public long getTiempoInvertido() {
        return tiempoInvertido;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void inicializarPiedras(int boardSize, double porcentajeEspeciales) {
        int totalStones = boardSize * boardSize;
        int cantidadEspeciales = (int) (totalStones * porcentajeEspeciales);

        ArrayList<Piedra> tiposPosibles = new ArrayList<>();

        // Add normal stones (PiedraNormal) based on the player number
        for (int i = 0; i < totalStones - cantidadEspeciales; i++) {
            tiposPosibles.add(new PiedraNormal(playerNumber));
        }

        // Distribuir aleatoriamente las piedras especiales entre PiedraTemporal y PiedraPesada
        Random random = new Random();
        int totalSpecialStones = cantidadEspeciales;

        while (totalSpecialStones > 0) {
            int randomType = random.nextInt(2); // 0 or 1
            if (randomType == 0 && totalSpecialStones > 0) {
                tiposPosibles.add(new PiedraPesada(playerNumber));
                totalSpecialStones--;
            } else if (totalSpecialStones > 0) {
                tiposPosibles.add(new PiedraTemporal(playerNumber));
                totalSpecialStones--;
            }
        }

        // Shuffle the list to randomize stone selection
        Collections.shuffle(tiposPosibles);

        // Add stones to the player's collection
        for (int i = 0; i < totalStones; i++) {
            Piedra piedra = tiposPosibles.get(i);
            piedras.add(piedra);
        }

        // Print the types of stones the player has after initialization
//        imprimirPiedras();
        System.out.println(contarFichasPorTipo());
    }

    public HashMap<String, Integer> contarFichasPorTipo() {
        // Inicializar el contador
        HashMap<String, Integer> contadorFichas = new HashMap<>();
        contadorFichas.put("Normal", 0);
        contadorFichas.put("Pesada", 0);
        contadorFichas.put("Temporal", 0);

        // Contar las fichas por tipo
        for (Piedra piedra : piedras) {
            if (piedra instanceof PiedraNormal) {
                contadorFichas.put("Normal", contadorFichas.get("Normal") + 1);
            } else if (piedra instanceof PiedraPesada) {
                contadorFichas.put("Pesada", contadorFichas.get("Pesada") + 1);
            } else if (piedra instanceof PiedraTemporal) {
                contadorFichas.put("Temporal", contadorFichas.get("Temporal") + 1);
            }
        }
        return contadorFichas;
    }


    public Piedra sumarFichaAleatoria() {
        Random random = new Random();
        int randomType = random.nextInt(1); // 0, 1 o 2

        Piedra nuevaPiedra = null;

        switch (randomType) {
            case 0:
                nuevaPiedra = new PiedraNormal(playerNumber);
                break;
            case 1:
                nuevaPiedra = new PiedraPesada(playerNumber);
                break;
            case 2:
                nuevaPiedra = new PiedraTemporal(playerNumber);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + randomType);
        }
        piedras.add(nuevaPiedra);
        return nuevaPiedra;
    }



    public void reiniciarPiedras(int boardSize, double porcentajeEspeciales) {
        piedras.clear();
        inicializarPiedras(boardSize, porcentajeEspeciales);
    }

//    public abstract int makeMove(Board board, int type);

    public abstract boolean hasPieceOfType(Class<?> clasePiedra);

    public abstract void removePiece(Piedra piedraToRemove);


    public int getPlayerNumber() {
        return playerNumber;
    }

    public Piedra ObtenerPiedra(Class<?> pieceType) {
        for (Piedra piedra : piedras) {
            if (pieceType.isInstance(piedra) && piedra.getClass().equals(pieceType)) {
                return piedra;
            }
        }
        return null;
    }

    public void imprimirPiedras() {
        System.out.println("Piedras del Jugador " + playerNumber + ": ");
        for (Piedra piedra : piedras) {
            System.out.print(piedra.getTipoPiedra() + " ");

        }
        System.out.println();
    }

}
