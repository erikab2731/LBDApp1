package com.example.erika.lbdapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.Locale;

public class Usuario extends AppCompatActivity implements alertdialog1.alertidaloglistener, dialogeditar.dialogeditarlistener {
    private String valor = "valorinicialemail";
    private String local = Locale.getDefault().toString();
    TextView textemail;
    TextView texttelefono;
    TextView textnombre;
    TextView textdireccion;

    Button logout;
    Button volver;
    Button actpass;
    Button actdirec;
    Button actnombre;
    Button acttelefono;
    Button eliminar;

    SQLiteDatabase bd;

    int boton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_usuario);
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor = extras.getString("emailuser");

        }
        textemail = (TextView) findViewById(R.id.textemail);
        // ponemos el valor del usuario en el textview del email
        textemail.setText(valor);
        textnombre = (TextView) findViewById(R.id.textnombre);
        textdireccion = (TextView) findViewById(R.id.textdirec);
        texttelefono = (TextView) findViewById(R.id.texttelefono);
        if (savedInstanceState != null) {
            // se coje el locale y se configura lalocalización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
            texttelefono.setText(savedInstanceState.getString("valortextelefono"));
            textdireccion.setText(savedInstanceState.getString("valortextdireccion"));
            textnombre.setText(savedInstanceState.getString("valortextnombre"));


        } else {

            inicializardatos();
        }


        logout = (Button) findViewById(R.id.btnlogout);
        // si el usuario pulsa el botón "logout"
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se llama al método para cerrar sesión
                logout();
            }
        });

        // si
        volver = (Button) findViewById(R.id.btnvolver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Lista de cesta de la compra");
                String text = "Nombre : " + textnombre.getText().toString() + "\n" + "Correo : " + textemail.getText().toString() + "\n" + "Direccion : " +textdireccion.getText().toString() +  "\n" + "Teléfono : " + texttelefono.getText().toString()+"\n";
                i.putExtra(android.content.Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(i, "Lista"));
            }
        });
        // inicializamos el boton "actualización dirección" con su correspondiente id
        actdirec = (Button) findViewById(R.id.actdirec);
        // si el usuario pulsa el botón "actualizar dirección"
        actdirec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se cambia la variable direc a true, esta variable sirve como identificador del botón que lanza el dialogo.
                boton = 1;
                // se lanza el dialogo
                dialogeditar dialog = new dialogeditar();
                dialog.show(((FragmentActivity) Usuario.this).getSupportFragmentManager(), "etiqueta");
            }
        });


        actnombre = (Button) findViewById(R.id.actnombre);
        actnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 2;
                dialogeditar dialog1 = new dialogeditar();
                dialog1.show(((FragmentActivity) Usuario.this).getSupportFragmentManager(), "etiqueta");
            }
        });


        actpass = (Button) findViewById(R.id.actpass);
        actpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 3;
                dialogeditar dialog2 = new dialogeditar();
                dialog2.show(((FragmentActivity) Usuario.this).getSupportFragmentManager(), "etiqueta");
            }
        });

        acttelefono = (Button) findViewById(R.id.acttel);
        acttelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 4;
                dialogeditar dialog2 = new dialogeditar();
                dialog2.show(((FragmentActivity) Usuario.this).getSupportFragmentManager(), "etiqueta");
            }
        });


        eliminar = findViewById(R.id.btneliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog1 alertdialog = new alertdialog1();
                alertdialog.show(((FragmentActivity) Usuario.this).getSupportFragmentManager(), "etiqueta");
            }
        });

    }


    public void inicializardatos() {
        miBD GestorDB1 = new miBD(Usuario.this, "MiBD", null, 2);
        bd = GestorDB1.getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM Usuarios WHERE email = '" + valor + "'", null);
        if (cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // e inicializamos los texview con los datos que hay en la base de datos
            String name = cursor.getString(cursor.getColumnIndex("Nombre"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String direccion = cursor.getString(cursor.getColumnIndex("telefono"));
            String telefono = cursor.getString(cursor.getColumnIndex("direc"));
            texttelefono.setText(telefono);
            textdireccion.setText(direccion);
            textnombre.setText(name);

        }
        bd.close();

    }


    public void applyTexts(String lseleccion) {
        miBD GestorDB1 = new miBD(Usuario.this, "MiBD", null, 2);
        bd = GestorDB1.getWritableDatabase();
        switch (boton) {
            case 1:
                bd.execSQL("UPDATE Usuarios SET direc ='" + lseleccion + "' WHERE email='" + valor + "'");
                textdireccion.setText(lseleccion);
                break;
            case 2:
                bd.execSQL("UPDATE Usuarios SET Nombre ='" + lseleccion + "' WHERE email='" + valor + "'");
                textnombre.setText(lseleccion);
                break;
            case 3:
                bd.execSQL("UPDATE Usuarios SET Password ='" + lseleccion + "' WHERE email='" + valor + "'");
                break;
            case 4:
                bd.execSQL("UPDATE Usuarios SET telefono ='" + lseleccion + "' WHERE email='" + valor + "'");
                texttelefono.setText(lseleccion);
                break;
        }
        bd.close();

    }

    @Override
    public void seleccion(Boolean lseleccion) {
        if (lseleccion) {

            miBD GestorDB1 = new miBD(Usuario.this, "MiBD", null, 2);
            bd = GestorDB1.getWritableDatabase();

            bd.delete("Usuarios", "email='" + valor + "'", null);

            bd.close();

            logout();

        }
    }

    public void logout() {
        // Este método lanza un intent con dos flags activados que limpian la pila de actividades
        Intent intent = new Intent(Usuario.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        String valortexemail = textemail.getText().toString();
        String valortextelefono = texttelefono.getText().toString();
        String valortextnombre = textnombre.getText().toString();
        String valortextdireccion = textdireccion.getText().toString();

        savedInstanceState.putString("valortexemail", valortexemail);
        savedInstanceState.putString("valortextelefono", valortextelefono);
        savedInstanceState.putString("valortextnombre", valortextnombre);
        savedInstanceState.putString("valortextdireccion", valortextdireccion);
        savedInstanceState.putString("locale",local);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
