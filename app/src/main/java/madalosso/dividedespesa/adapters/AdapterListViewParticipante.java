package madalosso.dividedespesa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.classes.Participante;

/**
 * Created by madal_000 on 20-Feb-15.
 */
public class AdapterListViewParticipante extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Participante> itens;

    public AdapterListViewParticipante(Context context, ArrayList<Participante> itens) {
        this.layoutInflater = LayoutInflater.from(context);
        this.itens = itens;
    }

    public int getCount() {
        return itens.size();
    }

    public Participante getItem(int position) {
        return itens.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {
            //infla o layout para podermos pegar as views
            view = layoutInflater.inflate(R.layout.listview_participante, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte();
            itemHolder.textNome = ((TextView) view.findViewById(R.id.textNomeParticipante));

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        Participante item = itens.get(position);
        itemHolder.textNome.setText(item.getNome());

        //retorna a view com as informações
        return view;
    }

    private class ItemSuporte {
        TextView textNome;
    }

}
