package madalosso.dividedespesa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.classes.Viagem;

/**
 * Created by madal_000 on 05-Feb-15.
 */
public class AdapterListViewHome extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private ArrayList<Viagem> itens;

    public AdapterListViewHome(Context context, ArrayList<Viagem> itens) {
        this.itens = itens;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itens.size();
    }

    public Viagem getItem(int position) {
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
            view = layoutInflater.inflate(R.layout.listview_home, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte();
            itemHolder.textDesc = ((TextView) view.findViewById(R.id.textViagem));
            itemHolder.textName = ((TextView) view.findViewById(R.id.textDescricao));

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        Viagem item = itens.get(position);
        itemHolder.textName.setText(item.getNome());
        itemHolder.textDesc.setText(item.getDestino());

        //retorna a view com as informações
        return view;
    }

    private class ItemSuporte {
        TextView textDesc;
        TextView textName;
    }

}
