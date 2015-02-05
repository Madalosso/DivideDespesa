package madalosso.dividedespesa.classes;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Contibuinte {
    private Participante pagante;
    private int valor;

    public Contibuinte(Participante pagante, int valor) {
        this.pagante = pagante;
        this.valor = valor;
    }

    public Participante getPagante() {
        return pagante;
    }

    public void setPagante(Participante pagante) {
        this.pagante = pagante;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
