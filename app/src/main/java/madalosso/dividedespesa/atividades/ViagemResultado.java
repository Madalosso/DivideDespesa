package madalosso.dividedespesa.atividades;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Participante;
import madalosso.dividedespesa.classes.Viagem;

public class ViagemResultado extends ActionBarActivity {
    private Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio_viagem);
        TextView mediaGasto, totalGasto, nomeViagem;
        mediaGasto = (TextView) findViewById(R.id.textCustoMedio);
        totalGasto = (TextView) findViewById(R.id.textCustoTotal);
        nomeViagem= (TextView) findViewById(R.id.textNomeViagem);
        viagem = (Viagem) getIntent().getSerializableExtra("viagem");
        int valorTotal = 0;
//        int valoresParticipantes[];
        ArrayList<Integer> valoresParticipante = new ArrayList<>();
        for(Participante p : viagem.getParticipantes()){
            valoresParticipante.add(0);
        }
        Log.d("OTAVIO ","numero de participantes: "+valoresParticipante.size());
        for (Conta c : viagem.getContas()) {
            valorTotal+=c.getCusto();
            valoresParticipante.set(c.getPagante(),valoresParticipante.get(c.getPagante())+c.getCusto());
        }
        int valorMedia = valorTotal/valoresParticipante.size();
        Log.d("OTAVIO ","total: "+valorTotal);
        Log.d("OTAVIO ","media: "+valorMedia);
        mediaGasto.setText(String.valueOf(valorMedia));
        totalGasto.setText(String.valueOf(valorTotal));
        nomeViagem.setText(viagem.getNome());
        ArrayList<String> saldos = new ArrayList<>();
        for(int i=0; i<valoresParticipante.size();i++){
            saldos.add(viagem.getNomeParticipanteByIndex(i)+": "+(valoresParticipante.get(i)-valorMedia));
            Log.d("OTAVIO RESULTADO", "Participante "+i+" saldo: "+(valoresParticipante.get(i)-valorMedia));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, saldos);
        ListView lista = (ListView)findViewById(R.id.listaSaldos);
        lista.setAdapter(adapter);
    }
}
