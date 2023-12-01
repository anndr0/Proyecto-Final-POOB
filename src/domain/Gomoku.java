package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Gomoku {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private int size;

    public Gomoku(int size) {
        this.size = size;
        board = new Board(size);
        players = new Player[]{new HumanPlayer(1), new HumanPlayer(2)};
        currentPlayerIndex = 0;
    }

    public int getSize() {
        return size;
    }
    public void reiniciarFichas() {
        for (Player player : players) {
            player.reiniciarFichas();
        }
    }
    public void resetGame() {
        board.resetBoard();
//        currentPlayerIndex = 0;
    }

    public int checkWinner() {
        return board.checkWinner();
    }

    public int[][] getBoardState() {
        return board.getBoardState();
    }
    public boolean isBoardFull() {
        return board.isBoardFull();
    }

    public void handleTie() {
        if (isBoardFull()) {
            int middleRow = size / 2;

            for (int i = 0; i < middleRow; i++) {
                for (int j = 0; j < size; j++) {
                    board.makeMove(i, j, 1);
                }
            }
            for (int i = middleRow; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board.makeMove(i, j, 2);
                }
            }
            ;
        }
    }
    // Métodos para contar las fichas de cada tipo para el jugador específico (por número de jugador)
    public int contarFichasNormalesPorJugador(int playerNumber) {
        return players[playerNumber - 1].contarFichasNormales();
    }

    public int contarFichasPesadasPorJugador(int playerNumber) {
        return players[playerNumber - 1].contarFichasPesadas();
    }

    public int contarFichasTemporalesPorJugador(int playerNumber) {
        return players[playerNumber - 1].contarFichasTemporales();
    }
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int makeMove(int row, int col) {
        Player currentPlayer = players[currentPlayerIndex];

        // Obtener el tipo de piedra que el jugador está utilizando
        int tipoPiedra = currentPlayer.makeMove(board);

        // Realizar el movimiento en el tablero con el tipo de piedra obtenido
        board.makeMove(row, col, tipoPiedra);

//        // Contar las fichas después de cada movimiento
//        List<Integer> contadorFichas = currentPlayer.contarFichas();
//        int fichasNormales = currentPlayer.contarFichasNormales();
//        int fichasPesadas = currentPlayer.contarFichasPesadas();
//        int fichasTemporales = currentPlayer.contarFichasTemporales();
////
//        System.out.println("Jugador " + currentPlayer.getPlayerNumber());
//        System.out.println("Fichas Normales: " + fichasNormales);
//        System.out.println("Fichas Pesadas: " + fichasPesadas);
//        System.out.println("Fichas Temporales: " + fichasTemporales);

//        System.out.println("Fichas del Jugador " + currentPlayer.getPlayerNumber() + " después del movimiento: ");
//        System.out.println("Normales: " + contadorFichas.get(0) + ", Pesadas: " + contadorFichas.get(1) + ", Temporales: " + contadorFichas.get(2));


        // Verificar si hay un ganador después de cada movimiento
        int winner = board.checkWinner();
        if (winner != 0) {
            System.out.println("Player " + winner + " wins!");
        } else {
            // Cambiar al siguiente jugador
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        }
        return tipoPiedra;
    }

}