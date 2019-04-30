package com.example.erika.lbdapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

public class Galeria extends AppCompatActivity implements DescargarImagen.AsyncResponse, ConectarAlServidor.AsyncResponse{
    private ArrayList<String> productos = new ArrayList<String>();
    private ArrayList<Bitmap> imagenes = new ArrayList<Bitmap>();
    private Context elcontexto = this;
    private AdaptadorGaleria eladaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        String php = "https://134.209.235.115/ebracamonte001/WEB/titulos.php";
        JSONObject parametrosJSON1 = new JSONObject();
        parametrosJSON1.put("Nombre","hola");

        ConectarAlServidor bd = new ConectarAlServidor(this,parametrosJSON1, php);
        bd.execute();

        RecyclerView lalista = findViewById(R.id.elreciclerview2);
        // inicializamos el adapter y le pasmos la lista de productos y el contexto del activity
       eladaptador = new AdaptadorGaleria(imagenes, elcontexto);
        lalista.setAdapter(eladaptador);
        // le ponemos el gridview al recyclerview
        GridLayoutManager elLayoutRejillaIgual = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        lalista.setLayoutManager(elLayoutRejillaIgual);
    }


    @Override
    public void processFinish(Bitmap output) throws ParseException {
        imagenes.add(output);
        eladaptador.notifyDataSetChanged();
    }

    @Override
    public void processFinish(String output) throws ParseException {
        String nombre = " ";
        JSONParser parser = new JSONParser();

        JSONObject json2 = (JSONObject) parser.parse(output);
        nombre = json2.get("titulos").toString();


        org.json.simple.JSONArray json3 = (org.json.simple.JSONArray) parser.parse(nombre);

       Iterator it = json3.iterator();

        while(it.hasNext()){
            Object  p = it.next();
            String imagen = (String) p;
           productos.add("https://134.209.235.115/ebracamonte001/WEB/"+imagen );
           eladaptador.notifyDataSetChanged();
        }


        for (int i = 0 ; i < productos.size() ;i++ ){
            DescargarImagen dc = new DescargarImagen(this,productos.get(i));
            dc.execute();
        }

    }




}
