package domain;

import java.util.Random;

public class ComputerPlayer extends Player {
    public ComputerPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public int makeMove(Board board) {
        imprimirFichas(); // Para verificar las fichas antes de hacer un movimiento

        // Obtener el tipo de la primera ficha en el arreglo
        int tipo = fichas.get(0);

        // Realizar la acción correspondiente según el tipo de piedra
        switch (tipo) {
            case 2:
                fichas.remove(0);
                break;
            case 4:
                fichas.remove(0);
                break;
            case 6:
                fichas.remove(0);
                break;
            // Puedes agregar más casos según sea necesario
        }
        imprimirFichas();

        // Devolver el tipo de piedra utilizado
        return tipo;
    }

}

