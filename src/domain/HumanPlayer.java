package domain;


public class HumanPlayer extends Player {
    public HumanPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public int makeMove(Board board) {
//        imprimirFichas(); // Para verificar las fichas antes de hacer un movimiento

        // Obtener el tipo de la ficha actual en la posición actual
        int tipo = fichas.get(0);

        // Realizar la acción correspondiente según el tipo de piedra
        switch (tipo) {
            case 1:
                fichas.remove(0);
                break;
            case 2:
                fichas.remove(0);
                break;
            case 3:
                fichas.remove(0);
                break;
            case 4:
                fichas.remove(0);
                break;
            case 5:
                fichas.remove(0);
                break;
            case 6:
                fichas.remove(0);
                break;
            // Puedes agregar más casos según sea necesario
        }
//        imprimirFichas();

        // Devolver el tipo de piedra utilizado
        return tipo;
    }

}
