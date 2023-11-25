package domain;

public abstract class Player {
    protected int playerNumber;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public abstract int makeMoveP(Board board);
    // Otros métodos según sea necesario
}
