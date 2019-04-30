package com.example.erika.lbdapp;

import android.Manifest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.example.erika.lbdapp.fragments.ContactFragment;
import com.example.erika.lbdapp.fragments.MapsFragment;
import com.example.erika.lbdapp.fragments.MapsFragment.OnFragmentInteractionListenermaps;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class Contacto extends AppCompatActivity implements  DialogText.dialogtextlistener, ContactFragment.OnFragmentInteractionListener, OnFragmentInteractionListenermaps{
    private final int CODIGO_DE_LLAMADA = 123;
    private boolean pedido = false;
    private String local = Locale.getDefault().toString();
    Fragment currentfragment;
    Boolean maps = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // las dos opciones siguientes a true nos permiten utilizar la flecha que esta en la toolbar  para volver hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
         String valor= extras.getString("emailuser");
        }
        if (savedInstanceState != null) {
            // se coje el locale y se configura lalocalización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
            maps= savedInstanceState.getBoolean("MapsFragment");
        }

        if (!maps) {
            currentfragment = new ContactFragment();
            changefragment(currentfragment);
        }else {
            cambiaramaps(currentfragment);
        }


    }

    private void changefragment(Fragment currentfragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentfragment).commit();

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CODIGO_DE_LLAMADA:
                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 671898278"));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                        startActivity(intentCall);
                    }
                    else {
                        //No se ha concedido el permiso
                        Toast.makeText(Contacto.this, "No has concedido el permiso ", Toast.LENGTH_SHORT).show();
                    }
                }break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("locale",local);
        savedInstanceState.putBoolean("MapsFragment",maps);

    }
    @Override
    public String applyText1() {
        // se lee el fichero de texto se crea el string linea en el que se almacenara el texto y linea1 se usa como variable auxiliar.
        String linea1 = " ";
        String linea;
        InputStream fich = getResources().openRawResource(R.raw.nota);
        BufferedReader buff = new BufferedReader(new InputStreamReader(fich));
        try {
            while((linea = buff.readLine())!=null) {
                linea1 = linea1 + linea +"\n";
                linea = buff.readLine();
                linea1 = linea1 + linea +"\n";
            }
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // devuelve la linea leída
        return linea1;
    }



    @Override
    public void onFragmentInteraction() {
        DialogText dialog = new DialogText();
        dialog.show( Contacto.this.getSupportFragmentManager(), "etiqueta");
    }


    public void cambiaramaps(Fragment fragment1) {
        maps = true;
        fragment1 = new MapsFragment();
        changefragment(fragment1);
    }




    @Override
    public void onFragmentInteractionmaps() {

    }
}




