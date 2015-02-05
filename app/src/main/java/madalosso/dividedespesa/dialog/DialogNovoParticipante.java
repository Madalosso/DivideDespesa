package madalosso.dividedespesa.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.logging.Logger;

import madalosso.dividedespesa.R;

/**
 * Created by madal_000 on 04-Feb-15.
 */
public class DialogNovoParticipante extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Novo Integrante");
        builder.setView(inflater.inflate(R.layout.dialog_novo_participante,null));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("X", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("add outro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    return builder.create();
    }
}
