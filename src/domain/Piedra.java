package domain;

public abstract class Piedra {
    protected int tipo;

    public Piedra(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
}


