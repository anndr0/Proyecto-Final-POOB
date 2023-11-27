package domain;

public class Gomoku {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private int size;
    public Gomoku(int size) {
        this.size = size;
        board = new Board(size);
        players = new Player[]{new HumanPlayer(), new ComputerPlayer()};
        currentPlayerIndex = 0;
    }

    public int getSize(){
        return size;
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
            // Lógica para manejar el empate

            // Obtener la mitad del tablero
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
    public void makeMove(int row, int col) {
        int currentPlayerNumber = currentPlayerIndex + 1;
        board.makeMove(row, col, currentPlayerNumber);

        // Verificar si hay un ganador después de cada movimiento
        int winner = board.checkWinner();
        if (winner != 0) {
            System.out.println("Player " + winner + " wins!");
            // Puedes realizar acciones adicionales después de que alguien gane
        } else {
            // Cambiar al siguiente jugador
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        }
    }
}
