package com.example.erika.lbdapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<String> datos;
    private ArrayList<Integer> imagenes;
    private String emailusuario;

    public Adapter(Context pcontext, ArrayList<String> pdatos, ArrayList<Integer> pimagenes, String email){
        contexto = pcontext;
        datos = pdatos;
        imagenes = pimagenes;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        emailusuario = email;
    }

    @Override
    public int getCount() {
        return imagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return imagenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // cojemos la posicion en la que se ha pulsado
        final int pos = position;
        // Ponemos el layout
        convertView = inflater.inflate(R.layout.grid_item2, null);
        // cojemos el imagebutton por id
        ImageButton imgbtn = convertView.findViewById(R.id.imageButton2);
        // quitamos el padding del botón para que no tenga margenes
        imgbtn.setPadding(0,0,0,0);
        //utilizamos la libreria picasso para ayudarnos a cargar las imágenes
        Picasso.with(contexto).load(imagenes.get(position)).into(imgbtn);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // Utilizamos un switch para saber que acción ejecutaar cuando tengamos la posición en la que el usuario ha pulsado

                switch(pos){
                    case 0: Intent intent= new Intent(contexto,Main3Activity.class);
                    //Si el usuario ha pulsado 0, nos vamos al main3activity que es el catálogo y enviamos el correo del usuario
                            intent.putExtra("emailuser",emailusuario);
                            contexto.startActivity(intent);
                            break;
                    case 1:
                        Intent intent2= new Intent(contexto,Usuario.class);
                        //Si el usuario ha pulsado 0, nos vamos al activity del perfil del usuario y enviamos el correo del  usuario
                        intent2.putExtra("emailuser",emailusuario);
                        contexto.startActivity(intent2);
                        break;
                    case 2:
                        Intent intent4= new Intent(contexto,Carrodelacompra.class);
                        //Si el usuario ha pulsado 0, nos vamos al Carrode la compra  y enviamos el correo del  usuario
                        intent4.putExtra("emailuser",emailusuario);
                        contexto.startActivity(intent4);
                        break;
                    case 3:
                        Intent intent6= new Intent(contexto,contacto.class);
                        //Si el usuario ha pulsado 0, nos vamos al activity de contacto  y enviamos el correo del  usuario
                        intent6.putExtra("emailuser",emailusuario);
                        contexto.startActivity(intent6);
                        break;
                }
                  }
        });

        return convertView;
    }
}
