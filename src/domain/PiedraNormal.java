package domain;

public class PiedraNormal extends Piedra {
    public PiedraNormal(int jugador) {
        super(jugador, 1);
    }
    @Override
    public int getTipoPiedra() {
        return 1;
    }
}


