// En la clase PiedraTemporal
package domain;

public class PiedraTemporal extends Piedra {
    private int turnosRestantes;

    public PiedraTemporal(int tipo) {
        super(tipo, 1);
        this.turnosRestantes = 7;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    public void disminuirTurno() {
        turnosRestantes--;
    }
}
