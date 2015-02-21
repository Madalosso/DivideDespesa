package madalosso.dividedespesa.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.SqlAdapter.BdHelper;
import madalosso.dividedespesa.adapters.AdapterListViewHome;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Viagem;


public class Home extends ActionBarActivity implements AdapterView.OnItemClickListener {

    static final int NOVA_VIAGEM_REQUEST = 1;
    static final int EDIT_VIAGEM_REQUEST = 2;
    static final int HANDLE_VIAGEM_REQUEST = 3;
    private ArrayList<Viagem> viagens;
    private AdapterListViewHome adapterListViewHome;
    private int position_edit;
    private BdHelper bdHelper;
    private ArrayList<Viagem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ListView lista = (ListView) findViewById(R.id.listView_viagens);
        registerForContextMenu(lista);

//        criaDados();
        list = new ArrayList<>();
        bdHelper = new BdHelper(this);
        list = bdHelper.getAllViagens();
        Log.d("leu lista ", "size: " + list.size());

        adapterListViewHome = new AdapterListViewHome(this, list);
//        adapterListViewHome = new AdapterListViewHome(this, viagens);
        lista.setAdapter(adapterListViewHome);
        lista.setOnItemClickListener(this);
        lista.setCacheColorHint(Color.TRANSPARENT);
    }

    private void criaDados() {
        viagens = new ArrayList<>();
        viagens.add(new Viagem("China 2012", "China"));
        viagens.add(new Viagem("Itália 2013", "Itália"));
//        viagens.get(1).addParticipante("Josnei");
//        viagens.get(1).addParticipante("Malandro");
        viagens.get(1).addConta(new Conta("mot1", 1900, 0));
        viagens.get(1).addConta(new Conta("Coc123", 1300, 1));
        viagens.get(1).addConta(new Conta("12311a", 1320, 1));
    }

    public void addViagem(View view) {
        Intent intent = new Intent(this, ViagemData.class);
        startActivityForResult(intent, NOVA_VIAGEM_REQUEST);
    }

    public void remViagem(int index) {
        //viagens.remove(index);
        bdHelper.deleteViagem(list.get(index));
        list.clear();
        // get all books
        list.addAll(bdHelper.getAllViagens());
        Log.d("size", " : " + list.size());
        adapterListViewHome.notifyDataSetChanged();
    }

    public void editViagem(int index) {
        Intent intent = new Intent(this, ViagemData.class);
//        intent.putExtra("viagem", viagens.get(index));
        intent.putExtra("id", list.get(index).getId());
        startActivityForResult(intent, EDIT_VIAGEM_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NOVA_VIAGEM_REQUEST://resultado do add viagem
                if (resultCode == RESULT_OK) {
//                    Bundle bundle = data.getExtras();
//                    Viagem v = (Viagem) bundle.get("viagem");
//                    viagens.add(v);

                    list.clear();
                    list.addAll(bdHelper.getAllViagens());
                    adapterListViewHome.notifyDataSetChanged();
                }
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "deu ruim", Toast.LENGTH_LONG).show();
                }
                break;
            case EDIT_VIAGEM_REQUEST://resultado de um edit da viagem
                if (resultCode == RESULT_OK) {
//                    Bundle bundle = data.getExtras();
//                    Viagem v = (Viagem) bundle.get("viagem");
//                    viagens.remove(position_edit);
//                    viagens.add(position_edit, v);
//                    viagens.add(v);
                    list.clear();
                    list.addAll(bdHelper.getAllViagens());
                    adapterListViewHome.notifyDataSetChanged();
                }
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "deu ruim", Toast.LENGTH_LONG).show();
                }
                break;
            /*case HANDLE_VIAGEM_REQUEST://abre a viagem para editar as contas
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Viagem v = (Viagem) bundle.get("viagem");
                    viagens.remove(position_edit);
                    viagens.add(position_edit, v);
                    adapterListViewHome.notifyDataSetChanged();
                }
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "deu ruim", Toast.LENGTH_LONG).show();
                }
                break;*/
        }

    }

    //CRIA ON CONTEXT MENU PARA ITENS DA LISTVIEW
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_viagem, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                position_edit = info.position;
                editViagem(info.position);
                return true;
            case R.id.delete:
                remViagem(info.position);
                return true;
            case R.id.export:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    //MENU CONTEXT FUNCTIONS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //SELECT DO LISTVIEW
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(this, ViagemHome.class);
        intent.putExtra("viagem", viagens.get(arg2));
        startActivityForResult(intent, HANDLE_VIAGEM_REQUEST);
    }
}