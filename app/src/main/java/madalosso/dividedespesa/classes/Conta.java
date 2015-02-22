package madalosso.dividedespesa.classes;

import java.io.Serializable;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Conta implements Serializable {
    private String motivo;
    private int id;
    private int custo;
    private int moeda;//criar padrão para representar cada moeda
    private int idPagante;//index
    private int idViagem;

    public Conta() {

    }

    public Conta(String motivo, int custo, int pagante, int idViagem) {
        super();
        this.id = -1;
        this.motivo = motivo;
        this.custo = custo;
        this.idPagante = pagante;
        this.idViagem = idViagem;
    }

    @Override
    public String toString() {
        return "[motivo: "+motivo+", id: "+id+", custo: "+custo+", idPagante: "+idPagante+", idviagem: "+idViagem+" ]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
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
        return idPagante;
    }

    public void setPagante(int pagante) {
        this.idPagante = pagante;
    }
}
