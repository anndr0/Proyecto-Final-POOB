package domain;

public abstract class Piedra {
    protected int jugador; // Nuevo atributo
    protected int valor; // Nuevo atributo

    public Piedra(int jugador, int valor) {
        this.jugador = jugador;
        this.valor = valor;
    }

    public int getJugador() {
        return jugador;
    }

    public int getValor() {
        return valor;
    }

    public abstract int getTipoPiedra();
}



