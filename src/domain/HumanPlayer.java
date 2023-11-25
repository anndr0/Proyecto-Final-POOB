package domain;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer() {
        super(1);
    }

    @Override
    public int makeMoveP(Board board) {
        // No necesitas la entrada por consola aquí, ya que el movimiento se gestionará desde la interfaz gráfica
        // Puedes devolver cualquier valor, ya que el clic en la interfaz gráfica manejará el movimiento
        return -1;
    }
    // Otros métodos según sea necesario
}
