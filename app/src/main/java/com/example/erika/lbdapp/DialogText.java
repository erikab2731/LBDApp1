package com.example.erika.lbdapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DialogText extends AppCompatDialogFragment {

    private View view;
    private TextView editar;
    public DialogText.dialogtextlistener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listener = (DialogText.dialogtextlistener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog3, null);
        editar = view.findViewById(R.id.textolargo);
        editar.setText(listener.applyText1());
        builder.setView(view)
                .setTitle(R.string.politica)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }


    interface dialogtextlistener {

        String applyText1();

    }


}
