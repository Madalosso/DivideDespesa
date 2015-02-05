package madalosso.dividedespesa.atividades;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import madalosso.dividedespesa.dialog.DialogNovoParticipante;
import madalosso.dividedespesa.R;
import madalosso.dividedespesa.classes.Viagem;


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

    public void confirma(View view){
        String nome = nomeViagem.getText().toString();
        String dest = destino.getText().toString();
        if(nome.isEmpty() || dest.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Preencha os campos Nome e Destino", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Viagem v = new Viagem(nome, dest);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("viagem", v);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public void cancela(View view){
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void addEFecha(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "TESTE", Toast.LENGTH_SHORT);
        toast.show();
    }

}
