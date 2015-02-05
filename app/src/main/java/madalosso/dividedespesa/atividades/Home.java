package madalosso.dividedespesa.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.adapters.AdapterListViewHome;
import madalosso.dividedespesa.classes.Viagem;


public class Home extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private Button btAddViagem;
    private ListView lista;
    private ArrayList<Viagem> viagens;
    static final int NOVA_VIAGEM_REQUEST = 1;
    private AdapterListViewHome adapterListViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btAddViagem = (Button)findViewById(R.id.btAddViagem);
        lista = (ListView)findViewById(R.id.listView_viagens);

        viagens = new ArrayList<>();
        //remover depois
        viagens.add(new Viagem("China 2012","China"));
        viagens.add(new Viagem("Itália 2013","Itália"));


        adapterListViewHome = new AdapterListViewHome(this, viagens);
        lista.setAdapter(adapterListViewHome);
        lista.setOnItemClickListener(this);
        lista.setCacheColorHint(Color.TRANSPARENT);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //Pega o item que foi selecionado.
        Viagem item = adapterListViewHome.getItem(arg2);
        //Demostração
        Toast.makeText(this, "Você Clicou em: " + item.getNome(), Toast.LENGTH_LONG).show();
    }

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

    public void novaViagem(View view){
        Intent intent = new Intent(this, ViagemData.class);
        startActivityForResult(intent, NOVA_VIAGEM_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                Viagem v = (Viagem) bundle.get("viagem");
                viagens.add(v);
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

}
