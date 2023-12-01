package domain;

public abstract class Piedra {
    protected int tipo;
    protected int valor; // Nuevo atributo

    public Piedra(int tipo, int valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }
}


