package madalosso.dividedespesa.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import madalosso.dividedespesa.adapters.AdapterListViewViagem;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Viagem;
import madalosso.dividedespesa.R;

public class ViagemHome extends ActionBarActivity {
    static final int NOVA_DESPESA_REQUEST = 1;
    static final int EDIT_DESPESA_REQUEST = 2;
    private Viagem viagem;
    private AdapterListViewViagem adapter;
    private ArrayList<Conta> contas;
    private int position_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_viagem);
        TextView nomeViagem = (TextView) findViewById(R.id.tituloViagem);
        ListView lista = (ListView) findViewById(R.id.listaDespesas);
        registerForContextMenu(lista);

        viagem = (Viagem) getIntent().getSerializableExtra("viagem");
        contas = viagem.getContas();
        nomeViagem.setText(viagem.getNome());

        adapter= new AdapterListViewViagem(this, contas,viagem.getParticipantes());
        lista.setAdapter(adapter);
        lista.setCacheColorHint(Color.TRANSPARENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_viagem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://resultado do add
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Conta c = (Conta) bundle.get("conta");
                    viagem.addConta(c);
                    adapter.notifyDataSetChanged();
                }
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "deu ruim", Toast.LENGTH_LONG).show();
                }
                break;
            case 2://resultado de um edit
                Toast.makeText(this, "RETURN", Toast.LENGTH_LONG).show();
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Conta c = (Conta) bundle.get("conta");
                    viagem.remConta(position_edit);
                    viagem.addConta(position_edit,c);
                    contas = viagem.getContas();
                    adapter.notifyDataSetChanged();
                }
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "deu ruim", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    //CRIA ON CONTEXT MENU PARA ITENS DA LISTVIEW
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_participantes, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                position_edit = info.position;
                editDespesa(info.position);
                return true;
            case R.id.delete:
                remDespesa(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addDespesa(View v){
        Intent intent = new Intent(this, DespesaData.class);
        intent.putExtra("participantes", viagem.getParticipantes());
        startActivityForResult(intent, NOVA_DESPESA_REQUEST);
    }

    public void editDespesa(int index){
        Intent intent = new Intent(this, DespesaData.class);
        intent.putExtra("conta", contas.get(index));
        intent.putExtra("participantes", viagem.getParticipantes());
        startActivityForResult(intent, EDIT_DESPESA_REQUEST);
    }

    public void remDespesa(int index){
        viagem.remConta(index);
        contas = viagem.getContas();
        adapter.notifyDataSetChanged();
    }

    public void fecha(){

    }
}
