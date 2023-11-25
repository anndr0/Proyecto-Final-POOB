package domain;

import java.util.Random;

public class ComputerPlayer extends Player {
    public ComputerPlayer() {
        super(2);
    }

    @Override
    public int makeMoveP(Board board) {
        // Implementar la lógica para que la máquina realice un movimiento
        Random random = new Random();
        return random.nextInt(225); // Supongamos que devuelve la posición del movimiento
    }
    // Otros métodos según sea necesario
}
