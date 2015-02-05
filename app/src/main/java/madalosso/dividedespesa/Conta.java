package madalosso.dividedespesa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class Conta implements Serializable{
    private String motivo;
    private int Custo;
    private int moeda;//criar padrão para representar cada moeda
    private ArrayList<Contibuinte> pagantes;

}
