package domain;


public class Gomoku {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private int size;
    private int turnoPiedraNormal = -1;

    public Gomoku(int size) {
        this.size = size;
        board = new Board(size);
        players = new Player[]{new HumanPlayer(1, size), new HumanPlayer(2, size)};
        currentPlayerIndex = 0;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }
    public int getSize() {
        return size;
    }
    public void reiniciarFichas(int size, double porcentajeEspeciales) {
        for (Player player : players) {
            player.reiniciarPiedras(size, porcentajeEspeciales);
        }
    }
    public void reiniciarCasillas(double porcentajeEspeciales){
        board.reiniciarCasillas(porcentajeEspeciales);
    }
    public Piedra[][] getBoardState() {
        return board.getBoardState();
    }
    public void resetGame() {
        board.resetBoard();
        currentPlayerIndex = 0;
        setTurnoPiedraNormal(-1);
    }

    public Player getPlayerByNumber(int playerNumber) {
        for (Player player : players) {
            if (player.getPlayerNumber() == playerNumber) {
                return player;
            }
        }
        return null;  // Retorna null si no se encuentra el jugador
    }

    public int checkWinner() {
        return board.checkWinner();
    }

    public boolean isBoardFull() {
        return board.isBoardFull();
    }

    public void handleTie() {
        if (isBoardFull()) {
            int middleRow = size / 2;

            Player player1 = players[0];
            Player player2 = players[1];

            for (int i = 0; i < middleRow; i++) {
                for (int j = 0; j < size; j++) {
                    Piedra piedra = player1.getPiedras().get(0); // Obtén la primera piedra del jugador 1
                    board.makeMove(i, j, piedra);
                    player1.getPiedras().remove(piedra);
                }
            }

            for (int i = middleRow; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Piedra piedra = player2.getPiedras().get(0); // Obtén la primera piedra del jugador 2
                    board.makeMove(i, j, piedra);
                    player2.getPiedras().remove(piedra);
                }
            }
        }
    }
    public boolean isSpecialCell(int row, int col, String cellType) {
        switch (cellType) {
            case "Mine":
                return board.isMine(row, col);
            case "Tel":
                return board.isTel(row, col);
            case "Golden":
                return board.isGolden(row, col);
            default:
                return false;
        }
    }
    private boolean golden;

    public int getTurnoPiedraNormal(){
        return turnoPiedraNormal;
    }
    public void setTurnoPiedraNormal(int value){
        turnoPiedraNormal =value;
    }
    public void handleGoldenCell(int row, int col) {
        if (board.isGolden(row, col)) {
            Player currentPlayer = players[currentPlayerIndex];
            Piedra piedraObtenida = currentPlayer.sumarFichaAleatoria();
            if (piedraObtenida instanceof PiedraNormal) {
                wasGolden(true);
                turnoPiedraNormal = currentPlayerIndex;
            }
        }
    }


    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public boolean wasGolden(boolean was){
        golden = was;
        return golden;
    }
    public void makeMove(int row, int col, Piedra selectedPiedra) {
        Player currentPlayer = players[currentPlayerIndex];

        if (currentPlayer.getPiedras().contains(selectedPiedra)) {
            board.makeMove(row, col, selectedPiedra);
            currentPlayer.removePiece(selectedPiedra);

            System.out.println(currentPlayer.contarFichasPorTipo());

            int winner = board.checkWinner();

            System.out.println("current player index "+currentPlayerIndex);
            System.out.println("turno piedra normal "+turnoPiedraNormal);
            if (golden && currentPlayerIndex == turnoPiedraNormal){
                System.out.println(currentPlayerIndex);
                System.out.println(turnoPiedraNormal);
                currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
                golden = false;
            } else {
                if (winner == 0 && currentPlayerIndex != turnoPiedraNormal) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
                } else {
                    turnoPiedraNormal = -1;
                }
            }
        } else {
            System.out.println("Player does not have the selected piece.");
        }
    }
}