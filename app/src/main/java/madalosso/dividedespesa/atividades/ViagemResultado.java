package madalosso.dividedespesa.atividades;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.SqlAdapter.BdHelper;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Participante;
import madalosso.dividedespesa.classes.Viagem;

public class ViagemResultado extends ActionBarActivity {
    private Viagem viagem;

    BdHelper bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio_viagem);

        TextView mediaGasto, totalGasto, nomeViagem;
        mediaGasto = (TextView) findViewById(R.id.textCustoMedio);
        totalGasto = (TextView) findViewById(R.id.textCustoTotal);
        nomeViagem = (TextView) findViewById(R.id.textNomeViagem);

        int idViagem;
        int valorTotal = 0;
        idViagem = getIntent().getIntExtra("id", -1);
        bd = new BdHelper(this);
        ArrayList<Participante> participantes = bd.getAllParticipantes(idViagem);
        ArrayList<Conta> contas = bd.getAllContas(idViagem);
        Map<Integer, Integer> valoresParticipante = new HashMap<Integer, Integer>();
        for (Participante p : participantes) {
            valoresParticipante.put(p.getId(), 0);
        }
        for (Conta c : contas) {
            Log.d("conta", " " + c.getCusto());
            valorTotal += c.getCusto();
            valoresParticipante.put(c.getPagante(), valoresParticipante.get(c.getPagante()) + c.getCusto());
        }
        Log.d("total", " " + valorTotal);
        int valorMedia = valorTotal / valoresParticipante.size();
        Log.d("media ", " " + valorMedia);
        mediaGasto.setText(String.valueOf(valorMedia));
        totalGasto.setText(String.valueOf(valorTotal));
        nomeViagem.setText(bd.getViagem(idViagem).getNome());
        ArrayList<String> saldos = new ArrayList<>();
        for (Participante p : participantes) {
            saldos.add(p.getNome() + ": " + (valoresParticipante.get(p.getId()) - valorMedia));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, saldos);
        ListView lista = (ListView) findViewById(R.id.listaSaldos);
        lista.setAdapter(adapter);
    }
}
