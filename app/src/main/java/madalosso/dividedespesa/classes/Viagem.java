package madalosso.dividedespesa.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Viagem implements Serializable {
    private int id;
    private String nome;
    private String destino;
    private ArrayList<Participante> participantes;
    private ArrayList<Conta> contas;

    public Viagem(){

    }

    public Viagem(String nome, String destino) {
        super();
        this.id=-1;
        this.nome = nome;
        this.destino = destino;
        this.participantes = new ArrayList<>();
        this.contas = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "VIAGEM : [ ID:"+ id + ", NOME: "+ nome +", DESTINO: "+ destino+"]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void addParticipante(Participante p) {
        participantes.add(p);
    }

    public void remParticipante(int index) {
        participantes.remove(index);
    }

    public void addConta(Conta c) {
        contas.add(c);
    }

    public void addConta(int index, Conta c) {
        contas.add(index, c);
    }

    public void remConta(int index) {
        contas.remove(index);
    }

    public String getNomeParticipanteByIndex(int index){
        return participantes.get(index).getNome();
    }
}
