package com.example.erika.lbdapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
        private String local = Locale.getDefault().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (savedInstanceState != null) {
            // se coje el locale y se configura lalocalización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        }

        String valor = "nousuario";
        Context contexto = this;
        ArrayList<String> datos = new ArrayList<String>();
        datos.add("CATALOGO");
        datos.add("CESTA DE LA COMPRA");
        datos.add("MI CUENTA");
        datos.add("CONTACTO");
        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        imagenes.add(R.drawable.catalogo);
        imagenes.add(R.drawable.perfil);
        imagenes.add(R.drawable.cesta);
        imagenes.add(R.drawable.contacto);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor = extras.getString("email");

        }

        GridView grid = findViewById(R.id.gridview);
        Adapter eladap = new Adapter(contexto, datos, imagenes, valor);
        grid.setAdapter(eladap);


    }



    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("locale",local);

    }


}
