package com.example.erika.lbdapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class dialogeditar extends DialogFragment {

    private View view;
    private EditText editar;
    public dialogeditar.dialogeditarlistener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listener = (dialogeditar.dialogeditarlistener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialogo2, null);
        editar = (EditText) view.findViewById(R.id.edit);
        builder.setView(view)
                .setTitle(R.string.editinfous)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String valor = editar.getText().toString();
                        listener.applyTexts(valor);
                    }
                });


        return builder.create();
    }


    interface dialogeditarlistener {

        void applyTexts(String lseleccion);

    }

}