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
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Participante;

public class DespesaData extends ActionBarActivity {

    private EditText edMotivo, edCusto;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.despesa_data);

        edMotivo = (EditText) findViewById(R.id.edMotivo);
        edCusto = (EditText) findViewById(R.id.edCusto);
        spinner = (Spinner) findViewById(R.id.spinnerParticipantes);

        ArrayList<String> participanteNomes = new ArrayList<>();
        Conta cEdit = (Conta) getIntent().getSerializableExtra("conta");
        ArrayList<Participante> participantes = (ArrayList<Participante>) getIntent().getSerializableExtra("participantes");
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
        if (cEdit != null) {
            String motivo = cEdit.getMotivo();
            int custo = cEdit.getCusto();
            edMotivo.setText(motivo);
            edCusto.setText(String.valueOf(custo));
            spinner.setSelection(cEdit.getPagante());
        }
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
        int index_part = spinner.getSelectedItemPosition();
        if (motivo.isEmpty() || custo.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha os campos Nome e Destino", Toast.LENGTH_SHORT).show();
        } else {

            Conta novaConta = new Conta(motivo, custo_valor, index_part);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("conta", novaConta);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

}
