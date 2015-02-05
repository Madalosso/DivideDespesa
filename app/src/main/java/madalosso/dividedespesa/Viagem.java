package madalosso.dividedespesa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Viagem implements Serializable{
    private int id;
    private String nome;
    private String destino;
    private ArrayList<Participante> participantes;
    private ArrayList<Conta> contas;

    public Viagem(String nome, String destino) {
        this.nome = nome;
        this.destino = destino;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNome() {
        return nome;
    }

    public String getDestino() {
        return destino;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }
}
