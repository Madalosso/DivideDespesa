package madalosso.dividedespesa.atividades;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import madalosso.dividedespesa.R;
import madalosso.dividedespesa.SqlAdapter.BdHelper;
import madalosso.dividedespesa.adapters.AdapterListViewParticipante;
import madalosso.dividedespesa.classes.Conta;
import madalosso.dividedespesa.classes.Participante;
import madalosso.dividedespesa.classes.Viagem;


/**
 * Created by madal_000 on 04-Feb-15.
 */
public class ViagemData extends ActionBarActivity {
    Button btAddParticipante;
    ListView listViewParticipantes;
    EditText nomeViagem;
    EditText destino;
    //    ArrayList<String> participantes;
    AdapterListViewParticipante adapterListViewParticipante;
    boolean edit;
    int edit_id;
    BdHelper bd;
    Viagem vEdit;
    ArrayList<Participante> participantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem_data);

        btAddParticipante = (Button) findViewById(R.id.btCriaParticipante);
        listViewParticipantes = (ListView) findViewById(R.id.listaPartNovaViagem);
        nomeViagem = (EditText) findViewById(R.id.despesaTitle);
        destino = (EditText) findViewById(R.id.destinoNovaViagem);

        //cria menu context para a view ListView
        registerForContextMenu(listViewParticipantes);

        bd = new BdHelper(this);
        participantes = new ArrayList<>();
        edit = false;

        edit_id = getIntent().getIntExtra("id", -1);
//        edit_id = (int) getIntent().getSerializableExtra("id");
        if (edit_id != -1) {
            vEdit = bd.getViagem(edit_id);
            edit = true;
            String nome = vEdit.getNome();
            String desc = vEdit.getDestino();
            if (!nome.isEmpty()) {
                nomeViagem.setText(nome);
            }
            if (!desc.isEmpty()) {
                destino.setText(desc);
            }
            participantes.addAll(bd.getAllParticipantes(vEdit.getId()));


        }

        adapterListViewParticipante = new AdapterListViewParticipante(this, participantes);
        listViewParticipantes.setAdapter(adapterListViewParticipante);
        adapterListViewParticipante.notifyDataSetChanged();
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
                    addParticipante(value);
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
                    addParticipante(value);
                } else {
                    Toast.makeText(context, R.string.valor_invalido, Toast.LENGTH_LONG).show();
                }
                novoParticipante(view);
            }
        });
        AlertDialog alertaFinal = alert.create();
        alertaFinal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertaFinal.show();
    }

    public void confirma(View view) {
        String nome = nomeViagem.getText().toString();
        String dest = destino.getText().toString();
        if (nome.isEmpty() || dest.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Preencha os campos Nome e Destino", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (edit) {
                vEdit.setNome(nome);
                vEdit.setDestino(dest);
                bd.updateViagem(vEdit);
                ArrayList<Participante> comp = new ArrayList<>();
                comp.addAll(bd.getAllParticipantes(vEdit.getId()));

                //adiciona novos participantes
                for(int i =0; i<participantes.size();i++){
                    if(participantes.get(i).getId()==-1){
                        bd.addParticipante(participantes.get(i).getNome(),participantes.get(i).getIdViagem());
                        participantes.remove(i);
                        i--;
                    }
                }
                //remove do banco os excluidos da lista.
                for(Participante p : comp){
                    boolean flag=false;
                    for(Participante p2:participantes){
                        if(p2.getId()==p.getId()){
                            flag=true;
                            break;
                        }
                    }
                    if(!flag){
//                        bd.rem
                        //remove participante
                        Toast.makeText(getApplicationContext(),"REMOVER "+p.getNome(),Toast.LENGTH_LONG).show();
                    }
                }
                //tratar edits
                for(Participante p : participantes){
                    Log.d("UPDATE PART:",p.getNome()+p.getIdViagem());
                    bd.updateParticipante(p);
                }
            } else {
                long idNovaViagem = bd.addViagem(new Viagem(nome, dest));
                for (Participante p : participantes) {
                    bd.addParticipante(p.getNome(), (int) idNovaViagem);
                }
            }
//            Viagem v = new Viagem(nome, dest);
//            for (String s : participantes) {
//                v.addParticipante(s);
//            }
            Intent returnIntent = new Intent();
//            returnIntent.putExtra("viagem", v);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public void cancela(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    //MENU CONTEXT - LISTVIEW PARTICIPANTES

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_participantes, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                editParticipante(info.position);
                return true;
            case R.id.delete:
                remParticipante(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addParticipante(String s) {
        boolean flag = false;
        for (Participante p : participantes) {
            if (p.getNome().equals(s)) {
                Toast.makeText(getApplicationContext(), "Nome ja existe", Toast.LENGTH_LONG).show();
                flag = true;
                break;
            }
        }
        if (!flag) {
            if (edit) {
                participantes.add(new Participante(s, edit_id));
            } else {
                participantes.add(new Participante(s, -1));
            }
            adapterListViewParticipante.notifyDataSetChanged();
        }
    }


    public void editParticipante(final int index) {
//        participantes.get(index).setNome();
        //criar novo context menu para capturar o nome que ira substituir o nome do participante

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Editar Nome Participante");

        final EditText input = new EditText(this);
        alert.setView(input);
        AlertDialog.Builder builder = alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if (!value.isEmpty()) {
                    participantes.get(index).setNome(value);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.valor_invalido, Toast.LENGTH_LONG).show();
                    editParticipante(index);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        AlertDialog alertaFinal = alert.create();
        alertaFinal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertaFinal.show();

    }

    public void remParticipante(int index) {
        ArrayList<Conta> contas = new ArrayList<>();
//        contas.addAll(bd.getContas())
//        for(Conta c :contas){
//            if(c.getPagante())
//        }
        participantes.remove(index);
        adapterListViewParticipante.notifyDataSetChanged();
    }

}
