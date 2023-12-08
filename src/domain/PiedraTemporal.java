package domain;

public class PiedraTemporal extends Piedra {
    private int turnosRestantes;

    public PiedraTemporal(int jugador) {
        super(jugador, 1);
        this.turnosRestantes = 7;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    public void disminuirTurno() {
        turnosRestantes--;
    }

    @Override
    public int getTipoPiedra() {
        return 3;
    }
}

