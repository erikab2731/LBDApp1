package com.example.erika.lbdapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ElAdaptadorRecycler1 extends RecyclerView.Adapter<ElViewHolder1> {


    private ArrayList<Productoobject> losproductos;
    private Context contexto;
    private OnItemClickListener itemClickListener;


    public ElAdaptadorRecycler1(ArrayList<Productoobject> plosproductos, Context pcontexto, OnItemClickListener listener) {
        this.losproductos = plosproductos;
        this.contexto = pcontexto;
        this.itemClickListener = listener;
    }


    public ElViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ellayoutdelafila = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productocompra, null);
        ElViewHolder1 evh = new ElViewHolder1(ellayoutdelafila);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ElViewHolder1 elViewHolder1, int i) {
        elViewHolder1.eltexto.setText(losproductos.get(i).getNombre());
        elViewHolder1.eltexto1.setText(losproductos.get(i).getTalla());
        elViewHolder1.eltexto2.setText(losproductos.get(i).getDescripcion());
        elViewHolder1.eltexto3.setText(losproductos.get(i).getPrecio().toString());
        elViewHolder1.bind(losproductos.get(i), itemClickListener);

    }

    @Override
    public int getItemCount() {
        return losproductos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Productoobject product, int position);
    }


}
