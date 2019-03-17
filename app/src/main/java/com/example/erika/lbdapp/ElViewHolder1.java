package com.example.erika.lbdapp;


        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

public class ElViewHolder1 extends RecyclerView.ViewHolder {
    public TextView eltexto;
    public TextView eltexto1;
    public TextView eltexto2;
    public TextView eltexto3;

    public ElViewHolder1(View v) {
        super(v);
        eltexto = v.findViewById(R.id.carro1);
        eltexto1 = v.findViewById(R.id.carro2);
        eltexto2 = v.findViewById(R.id.carro3);
        eltexto3 = v.findViewById(R.id.carro4);

    }


    public void bind(final Productoobject producto, final ElAdaptadorRecycler1.OnItemClickListener listener) {
         itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(producto, getAdapterPosition());
            }
        });
    }
}