package madalosso.dividedespesa;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


/**
 * Created by madal_000 on 04-Feb-15.
 */
public class ViagemData extends ActionBarActivity {
    Button btAddParticipante;
    ListView listaParticipantes;
    EditText nomeViagem;
    EditText destino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem_data);
        btAddParticipante = (Button)findViewById(R.id.btCriaParticipante);
        listaParticipantes = (ListView)findViewById(R.id.listaPartNovaViagem);
        nomeViagem = (EditText)findViewById(R.id.nomeNovaViagem);
        destino = (EditText)findViewById(R.id.destinoNovaViagem);

    }


    public void novoParticipante(View view){
        DialogFragment newFragment = new DialogNovoParticipante();
        newFragment.show(getFragmentManager(),"addParticipante");
    }

    public void addEFecha(View view){

    }

}
