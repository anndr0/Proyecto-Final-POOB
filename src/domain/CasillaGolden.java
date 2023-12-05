package domain;

import java.util.Random;

public class CasillaGolden extends Casilla {

    public CasillaGolden() {
        // Constructor
    }

    @Override
    public void aplicarEfecto(Board board, int row, int col) {
        int jugadorActual = 1; // Asumiendo que hay un método para obtener el jugador actual
        Piedra piedraAleatoria = obtenerPiedraAleatoria(board, jugadorActual);
        board.makeMove(row, col, piedraAleatoria.getTipo());

        // Si la piedra aleatoria es normal, el jugador debe usar dos piedras normales en el siguiente turno
//        if (piedraAleatoria instanceof PiedraNormal) {
//            board.incrementarPiedrasNormales();
//        }
    }

    private Piedra obtenerPiedraAleatoria(Board board, int jugador) {
        // Obtener el conjunto de piedras del jugador actual
        Piedra[] piedrasJugador = board.getPiedrasDelJugador(jugador); // Asumiendo que hay un método para obtenerlas

        // Obtener una piedra aleatoria del conjunto
        Random random = new Random();
        int indiceAleatorio = random.nextInt(piedrasJugador.length);
        return piedrasJugador[indiceAleatorio];
    }
}
