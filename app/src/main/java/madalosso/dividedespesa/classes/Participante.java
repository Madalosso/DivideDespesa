package madalosso.dividedespesa.classes;

import java.io.Serializable;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Participante implements Serializable{
    private String nome;
    private int id;
    private int idViagem;

    public Participante(){

    }

    public Participante(String nome, int idv) {
        super();
        this.id=-1;
        this.nome = nome;
        this.idViagem=idv;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
