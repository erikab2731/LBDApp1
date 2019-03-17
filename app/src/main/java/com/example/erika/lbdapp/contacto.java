package com.example.erika.lbdapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class contacto extends AppCompatActivity implements DialogText.dialogtextlistener{
    Button btnllamar;
    Button politica;
    private final int CODIGO_DE_LLAMADA = 123;
    private boolean pedido = false;
    private String local = Locale.getDefault().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
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

        }
        // se coje el botón politica y se crea un dialog text en el que se muestra la politica
        politica = findViewById(R.id.button12);
        politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cuando el usuario pulsa en el botón
                DialogText dialog = new DialogText();
                dialog.show(((FragmentActivity) contacto.this).getSupportFragmentManager(), "etiqueta");

            }
        });

        btnllamar = findViewById(R.id.btnllamar);

        btnllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hacer el intent de llamada
                if (ContextCompat.checkSelfPermission(contacto.this, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED) {
                    //EL PERMISO NO ESTÁ CONCEDIDO, PEDIRLO


                    if (ActivityCompat.shouldShowRequestPermissionRationale(contacto.this, Manifest.permission.CALL_PHONE))
                    {
                        // MOSTRAR AL USUARIO UNA EXPLICACIÓN DE POR QUÉ ES NECESARIO EL PERMISO

                    }
                    else{
                        //EL PERMISO NO ESTÁ CONCEDIDO TODAVÍA O EL USUARIO HA INDICADO
                        // QUE NO QUIERE QUE SE LE VUELVA A SOLICITAR

                        if (pedido == true ) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                        else{
                            pedido = true;
                            Toast.makeText(contacto.this, "You declined the access", Toast.LENGTH_SHORT).show();
                        }

                    }

                    //PEDIR EL PERMISO
                    ActivityCompat.requestPermissions(contacto.this, new String[]{Manifest.permission.READ_CALL_LOG},
                            CODIGO_DE_LLAMADA);

                }
                else {
                    //EL PERMISO ESTÁ CONCEDIDO, EJECUTAR LA FUNCIONALIDAD
                    Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 671898278"));
                    startActivity(intentCall);
                }



            }
        });

    }

    private boolean CheckPermission(String callPhone) {

        int result = this.checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
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
                        Toast.makeText(contacto.this, "No has concedido el permiso ", Toast.LENGTH_SHORT).show();
                    }
                }break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("locale",local);

    }
    @Override
    public String applyText1() {
        // se lee el fichero de texto se crea el string linea en el que se almacenara el texto y linea1 se usa como variable auxiliar.
        String linea1 = " ";
        String linea = " ";
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
}




