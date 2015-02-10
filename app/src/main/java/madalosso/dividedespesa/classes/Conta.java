package madalosso.dividedespesa.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Conta implements Serializable{
    private String motivo;
    private int custo;
    private int moeda;//criar padrão para representar cada moeda
    private int pagante;//index

    public Conta(String motivo, int custo, int pagante) {
        this.motivo = motivo;
        this.custo = custo;
        this.pagante = pagante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getMoeda() {
        return moeda;
    }

    public void setMoeda(int moeda) {
        this.moeda = moeda;
    }

    public int getPagante() {
        return pagante;
    }

    public void setPagante(int pagante) {
        this.pagante = pagante;
    }
}
