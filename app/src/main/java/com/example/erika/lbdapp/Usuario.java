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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Locale;

public class Usuario extends AppCompatActivity implements alertdialog1.alertidaloglistener, dialogeditar.dialogeditarlistener {
    private String valor = "valorinicialemail";
    private String local = Locale.getDefault().toString();
    private String json = "hola";
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
    Button guardarropa;

    SQLiteDatabase bd;

    int boton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_usuario);
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor = extras.getString("emailuser");
            json = extras.getString("jsondatos");
            Log.d("enusuario", "onCreate: "+ json);
        }
        textemail = findViewById(R.id.textemail);
        // ponemos el valor del usuario en el textview del email
        textemail.setText(valor);
        textnombre = findViewById(R.id.textnombre);
        textdireccion = findViewById(R.id.textdirec);
        texttelefono = findViewById(R.id.texttelefono);
        if (savedInstanceState != null) {

            texttelefono.setText(savedInstanceState.getString("valortextelefono"));
            textdireccion.setText(savedInstanceState.getString("valortextdireccion"));
            textnombre.setText(savedInstanceState.getString("valortextnombre"));
            // se coje el locale y se configura lalocalización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());



        } else {

            try {
                inicializardatos();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        logout = findViewById(R.id.btnlogout);
        // si el usuario pulsa el botón "logout"
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se llama al método para cerrar sesión
                logout();
            }
        });

        // si el usuario pulsa en el botón compartir , se encia un intent con la información del usuario.
        volver =  findViewById(R.id.btnvolver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.infousuario);
                String text = getResources().getText(R.string.nombre) + textnombre.getText().toString() + "\n" + getResources().getText(R.string.correo) + textemail.getText().toString() + "\n" + getResources().getText(R.string.dir) +textdireccion.getText().toString() +  "\n" + getResources().getText(R.string.tel) + texttelefono.getText().toString()+"\n";
                i.putExtra(android.content.Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(i, getResources().getText(R.string.infousuario)));
            }
        });
        // inicializamos el boton "actualizar dirección" con su correspondiente id


        guardarropa =  findViewById(R.id.mislooks);
        guardarropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento = new Intent(Usuario.this, Mislooks.class);
                startActivity(intento);

            }
        });

    }

        //Se cojen los datos del usuario de la base de datos
    public void inicializardatos() throws ParseException {
        /*miBD GestorDB1 = new miBD(Usuario.this, "MiBD", null, 2);
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
        bd.close();*/
        Log.d("antes de parsear", "onCreate: "+ json);
        JSONParser parser = new JSONParser();
        JSONObject jsonverdadero = (JSONObject) parser.parse(json);
        Log.d("JSONVERDADERO", "onCreate: "+ jsonverdadero);
        String name ="jiewjfi";
        name= jsonverdadero.get("nombre").toString();
       // String email = jsonverdadero.get("email").toString();
        String direccion ="qwdwqd";
                direccion = jsonverdadero.get("direccion").toString();
        String telefono ="earfvqerv";
                telefono= jsonverdadero.get("telefono").toString();
        texttelefono.setText(telefono);
        textdireccion.setText(direccion);
        textnombre.setText(name);



    }

    public void applyTexts(String lseleccion) {
       // miBD GestorDB1 = new miBD(Usuario.this, "MiBD", null, 2);
       // bd = GestorDB1.getWritableDatabase();
        // se hace el update correspondiente segun el valor de la variable botón que se ha actualizado
        // cuando el usuario pulsa el boton.

        String atributo = "";
        String cambio = lseleccion;

        switch (boton) {
            case 1:
                atributo = "direc";
                bd.execSQL("UPDATE Usuarios SET direc ='" + lseleccion + "' WHERE email='" + valor + "'");
                textdireccion.setText(lseleccion);
                break;
            case 2:
                atributo = "Nombre";
                bd.execSQL("UPDATE Usuarios SET Nombre ='" + lseleccion + "' WHERE email='" + valor + "'");
                textnombre.setText(lseleccion);
                break;
            case 3:
                atributo = "Password";
                bd.execSQL("UPDATE Usuarios SET Password ='" + lseleccion + "' WHERE email='" + valor + "'");
                break;
            case 4:
                atributo = "telefono";
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
