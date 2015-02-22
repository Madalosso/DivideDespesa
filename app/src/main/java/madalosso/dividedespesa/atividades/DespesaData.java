package madalosso.dividedespesa.atividades;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.SqlAdapter.BdHelper;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Participante;

public class DespesaData extends ActionBarActivity {

    private EditText edMotivo, edCusto;
    private Spinner spinner;
    int idConta;
    int idViagem;
    private BdHelper bd;
    private Conta c;
    ArrayList<Participante> participantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.despesa_data);

        edMotivo = (EditText) findViewById(R.id.edMotivo);
        edCusto = (EditText) findViewById(R.id.edCusto);
        spinner = (Spinner) findViewById(R.id.spinnerParticipantes);

        bd = new BdHelper(this);
        ArrayList<String> participanteNomes = new ArrayList<>();

        idViagem = getIntent().getIntExtra("idViagem", -1);
        idConta = getIntent().getIntExtra("idConta", -1);
        participantes = bd.getAllParticipantes(idViagem);
        Log.d("TESTE","size participantes:"+ participantes.size());
        if (participantes != null) {
            for (Participante p : participantes) {
                participanteNomes.add(p.getNome());
            }
        }

        if (participanteNomes.size() == 0) {
            Toast.makeText(this, "SEM PARTICIPANTES CADASTRADOS", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, "size:" + participantes.size(), Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, participanteNomes);
        spinner.setAdapter(spinnerAdapter);
        if (idConta != -1) {
            c = bd.getConta(idConta);
            edMotivo.setText(c.getMotivo());
            edCusto.setText(String.valueOf(c.getCusto()));
            int i=0;
            for(Participante p :participantes){
                if(c.getPagante()==p.getId()){
                    break;
                }
                i++;
            }
            spinner.setSelection(i);
        }
//        if (cEdit != null) {
//            String motivo = cEdit.getMotivo();
//            int custo = cEdit.getCusto();
//            edMotivo.setText(motivo);
//            edCusto.setText(String.valueOf(custo));
//            spinner.setSelection(cEdit.getPagante());
//        }
    }

    public void cancela(View v) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void confirma(View v) {
        String motivo = edMotivo.getText().toString();
        String custo = edCusto.getText().toString();
        int custo_valor = Integer.valueOf(custo);
        int index_part = participantes.get(spinner.getSelectedItemPosition()).getId();
        if (motivo.isEmpty() || custo.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha os campos Nome e Destino", Toast.LENGTH_SHORT).show();
        } else {
            if (idConta != -1) {
                c.setMotivo(motivo);
                c.setIdViagem(idViagem);
                c.setCusto(custo_valor);
                c.setPagante(index_part); //verificar id do pagante
                bd.updateConta(c);
            } else {
                Conta novaConta = new Conta(motivo, custo_valor, index_part, idViagem);
                bd.addConta(novaConta);
            }
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

}
