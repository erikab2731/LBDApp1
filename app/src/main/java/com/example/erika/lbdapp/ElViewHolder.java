package com.example.erika.lbdapp;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ElViewHolder extends RecyclerView.ViewHolder {
    public TextView eltexto;
    public TextView eltexto1;
    public TextView eltexto2;
    public TextView eltexto3;
    public ImageView laimagen;
    public Button btn;

    public ElViewHolder(View v) {
        super(v);
        eltexto = v.findViewById(R.id.textView3);
        eltexto1 = v.findViewById(R.id.textView8);
        eltexto2 = v.findViewById(R.id.textView6);
        eltexto3 = v.findViewById(R.id.textView10);
        laimagen = v.findViewById(R.id.imageView4);
        btn = v.findViewById(R.id.button5);
    }
}