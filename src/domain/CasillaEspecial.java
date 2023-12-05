package domain;

public class CasillaEspecial {
    private TipoCasilla tipo;

    public CasillaEspecial(TipoCasilla tipo) {
        this.tipo = tipo;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }

    public void aplicarEfecto(Board board, int row, int col) {
        switch (tipo) {
            case MINE:
                // Lógica para la casilla Mine
                break;
            case TELEPORT:
                // Lógica para la casilla Teleport
                break;
            case GOLDEN:
                // Lógica para la casilla Golden
                break;
        }
    }
}

enum TipoCasilla {
    MINE, TELEPORT, GOLDEN
}
