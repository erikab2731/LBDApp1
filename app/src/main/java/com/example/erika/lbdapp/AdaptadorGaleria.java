package com.example.erika.lbdapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorGaleria extends RecyclerView.Adapter<ElViewHolder2> {


    private ArrayList<Bitmap> lostitulos;
    private Context contexto;
    private OnItemClickListener itemClickListener;


    public AdaptadorGaleria(ArrayList<Bitmap> titulos, Context elcontexto) {
        this.lostitulos = titulos;
        this.contexto = elcontexto;
    }


    public ElViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ellayoutdelafila = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imagengaleria, null);
        ElViewHolder2 evh = new ElViewHolder2(ellayoutdelafila);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ElViewHolder2 elViewHolder2, int i) {

        elViewHolder2.laimagen.setImageBitmap(lostitulos.get(i));

    }

    @Override
    public int getItemCount() {
        return lostitulos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Productoobject product, int position);
    }


}