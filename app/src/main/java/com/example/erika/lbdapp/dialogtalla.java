package com.example.erika.lbdapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class dialogtalla extends AppCompatDialogFragment {
    View view;
    final CharSequence[] opciones = {"S", "M", "L"};
    final ArrayList<Integer> loselegidos = new ArrayList<>();
    public dialogtallalistener listener;
    public Productoobject ptoducto;
    public int wich1;
    public boolean haelegido = false;

    public void setPtoducto(Productoobject ptoducto) {
        this.ptoducto = ptoducto;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }

    private String seleccion = "X";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Si restauran los valores de la seleccion y del boolean haelegido
        if (savedInstanceState != null) {
            ptoducto = savedInstanceState.getParcelable("producto");
            seleccion = savedInstanceState.getString("lseleccion");
            haelegido = savedInstanceState.getBoolean("haelegido");
            wich1 = savedInstanceState.getInt("posicion");
            loselegidos.add(wich1);
        }

        super.onCreateDialog(savedInstanceState);
        listener = (dialogtallalistener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog1, null);

        builder.setMultiChoiceItems(opciones, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // si esta marcado se añade a la lista de elegidos, si ya estaba en la lista se remueve
                if (isChecked) {
                    Log.d("este es el wich ", "onClick: "+ which);
                    wich1 = which;
                    loselegidos.add(which);
                    Log.d("este es loselegidos", "loselegidos: "+ loselegidos.size());
                } else if (loselegidos.contains(which)) {
                    loselegidos.remove(Integer.valueOf(which));
                }
            }
        });
        builder.setView(view)
                .setTitle(R.string.electalla)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            //Si cancela la actividad no hace nada
                            }
                        }
                )
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //si la lista de elegidos no esta vacia se pone haelegido a true
                        if (!loselegidos.isEmpty()) {
                            haelegido = true;
                        }
                        if (haelegido == true) {
                            //si ha elegido se cambia la talla al producto y se llama al listener
                            setSeleccion(opciones[loselegidos.get(0)].toString());
                            Log.d("este es loselegidos", "loselegidos: "+ opciones[loselegidos.get(0)].toString());
                            ptoducto.setTalla(seleccion);
                            listener.applyTexts(ptoducto);
                        } else {
                            //si no ha elegido, no hace nada
                        }

                    }
                });


        return builder.create();
    }


    public interface dialogtallalistener {
        //listener al que se le pasa el producto elegido
        void applyTexts(Productoobject product);

    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // se guardan la seleccion y el boolean que indica si ha elegido

            savedInstanceState.putInt("posicion",wich1);
            savedInstanceState.putString("lseleccion",seleccion);
            savedInstanceState.putBoolean("haelegido",true);
            savedInstanceState.putParcelable("producto",ptoducto);



    }

}
