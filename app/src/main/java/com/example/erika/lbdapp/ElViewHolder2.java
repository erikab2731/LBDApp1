package com.example.erika.lbdapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class ElViewHolder2 extends RecyclerView.ViewHolder {

    public ImageView laimagen;


    public ElViewHolder2(View v) {
        super(v);

        laimagen = v.findViewById(R.id.imageView5);

    }
}