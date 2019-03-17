package com.example.erika.lbdapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.v4.app.FragmentActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ElAdaptadorRecycler extends RecyclerView.Adapter<ElViewHolder> {


    private ArrayList<Productoobject> losproductos;
    private Context contexto;

    public void setTallanueva(String tallanueva) {
        this.tallanueva = tallanueva;
    }

    private String tallanueva = "X";

    public ElAdaptadorRecycler(ArrayList<Productoobject> plosproductos, Context pcontexto) {
        losproductos = plosproductos;
        contexto = pcontexto;
    }

    @NonNull
    @Override
    public ElViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ellayoutdelafila = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item, null);
        ElViewHolder evh = new ElViewHolder(ellayoutdelafila);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ElViewHolder elViewHolder, int i) {
        final int e = i;
        elViewHolder.eltexto.setText(losproductos.get(i).getNombre());
        elViewHolder.eltexto1.setText(losproductos.get(i).getTalla());
        elViewHolder.eltexto2.setText(losproductos.get(i).getDescripcion());
        elViewHolder.eltexto3.setText(losproductos.get(i).getPrecio().toString());
        Picasso.with(contexto).load(losproductos.get(i).getImagen()).fit().into(elViewHolder.laimagen);
       // elViewHolder.laimagen.setImageResource(losproductos.get(i).getImagen());
        elViewHolder.btn.setText(R.string.añadir);
        elViewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Productoobject productamandar = losproductos.get(e);
                OpenDialog(productamandar);

            }
        });
    }

    @Override
    public int getItemCount() {
        return losproductos.size();
    }

    public void OpenDialog(Productoobject producto) {

        dialogtalla dialogtalla = new dialogtalla();
        dialogtalla.setPtoducto(producto);
        dialogtalla.show(((FragmentActivity) contexto).getSupportFragmentManager(), "etiqueta");


    }


}
