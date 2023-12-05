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
//        inicializarCasillasEspeciales();
        currentPlayerIndex = 0;
    }

    public Board getBoard() {
        return board;
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

    public void inicializarCasillasEspeciales() {
        // Puedes configurar casillas especiales según tus necesidades
        board.setCasillaEspecial(2, 2, new CasillaMina());
        board.setCasillaEspecial(5, 5, new CasillaTeleport());
//        board.setCasillaEspecial(8, 8, new CasillaGolden());
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

    public void makeMove(int row, int col, int selectedPieceType) {
        Player currentPlayer = players[currentPlayerIndex];

        if (currentPlayer.hasPieceOfType(selectedPieceType)) {
            board.makeMove(row, col, selectedPieceType);
            currentPlayer.removePiece(selectedPieceType);

            int winner = board.checkWinner();
            if (winner != 0) {
                System.out.println("Player " + winner + " wins!");
            } else {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            }
        } else {
            System.out.println("Player does not have the selected piece type.");
        }
    }


}