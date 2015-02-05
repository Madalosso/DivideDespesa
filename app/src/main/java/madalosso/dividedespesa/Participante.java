package madalosso.dividedespesa;

import java.io.Serializable;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Participante implements Serializable{
    private String nome;
    private int id;

    public Participante(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
