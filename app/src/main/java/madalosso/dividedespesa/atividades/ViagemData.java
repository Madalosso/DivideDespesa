package madalosso.dividedespesa.atividades;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.classes.Participante;
import madalosso.dividedespesa.classes.Viagem;


/**
 * Created by madal_000 on 04-Feb-15.
 */
public class ViagemData extends ActionBarActivity {
    Button btAddParticipante;
    ListView listaParticipantes;
    EditText nomeViagem;
    EditText destino;
    ArrayList<String> participantes;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem_data);
        btAddParticipante = (Button) findViewById(R.id.btCriaParticipante);
        listaParticipantes = (ListView) findViewById(R.id.listaPartNovaViagem);
        participantes = new ArrayList<>();
        participantes.add("FULANO1");
        participantes.add("FULANO2");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, participantes);
        listaParticipantes.setAdapter(adapter);
        nomeViagem = (EditText) findViewById(R.id.nomeNovaViagem);
        destino = (EditText) findViewById(R.id.destinoNovaViagem);
    }

    public void novoParticipante(final View view) {
        final Context context = view.getContext();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Novo Participante");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if (!value.isEmpty()) {
                    adicionaNome(value);
                } else {
                    Toast.makeText(context, R.string.valor_invalido, Toast.LENGTH_LONG).show();
                    novoParticipante(view);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        }).setNeutralButton("Add e +", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                if (!value.isEmpty()) {
                    adicionaNome(value);
                } else {
                    Toast.makeText(context, R.string.valor_invalido, Toast.LENGTH_LONG).show();
                }
                novoParticipante(view);
            }
        });

        alert.show();
    }

    public void adicionaNome(String s){
        participantes.add(s);
    }

    public void confirma(View view) {
        String nome = nomeViagem.getText().toString();
        String dest = destino.getText().toString();
        if (nome.isEmpty() || dest.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Preencha os campos Nome e Destino", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Viagem v = new Viagem(nome, dest);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("viagem", v);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public void cancela(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void addEFecha(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "TESTE", Toast.LENGTH_SHORT);
        toast.show();
    }


}
