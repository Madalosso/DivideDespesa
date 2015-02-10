package madalosso.dividedespesa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Participante;

public class AdapterListViewViagem extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Conta> contas;
    private ArrayList<Participante> participantes;

    public AdapterListViewViagem(Context context, ArrayList<Conta> itens,ArrayList<Participante> p) {
        this.contas = itens;
        this.layoutInflater = LayoutInflater.from(context);
        this.participantes=p;
    }

    public int getCount() {
        return contas.size();
    }

    public Conta getItem(int position) {
        return contas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {
            //infla o layout para podermos pegar as views
            view = layoutInflater.inflate(R.layout.listview_viagem, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte();
            itemHolder.textMotivo = ((TextView) view.findViewById(R.id.textMotivoGasto));
            itemHolder.textPagante = ((TextView) view.findViewById(R.id.textPagante));
            itemHolder.textValor = ((TextView) view.findViewById(R.id.textCusto));

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        Conta item = contas.get(position);
        itemHolder.textMotivo.setText(item.getMotivo());
//        itemHolder.textPagante.setText("0");
        itemHolder.textPagante.setText(participantes.get(item.getPagante()).getNome());
        itemHolder.textValor.setText(String.valueOf(item.getCusto()));

        //retorna a view com as informações
        return view;
    }

    private class ItemSuporte {
        TextView textMotivo;
        TextView textValor;
        TextView textPagante;
    }



}
