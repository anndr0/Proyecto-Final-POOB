package domain;

public class PiedraPesada extends Piedra {
    public PiedraPesada(int jugador) {
        super(jugador, 2);
    }
    @Override
    public int getTipoPiedra() {
        return 2;
    }
}



