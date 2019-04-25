package com.example.erika.lbdapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.out;

public class Register extends AppCompatActivity implements BDremota.AsyncResponse{

    private Button btncancelar;
    private Button btnguardar;
    private EditText Nombreusuario;
    private EditText direccion;
    private EditText editemail;
    private EditText editpassword;
    private EditText editphone;
    private SQLiteDatabase bd;
    private String local = Locale.getDefault().toString();
    private Context contexto = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        if (savedInstanceState != null) {
            // se coje el locale y se configura lalocalización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        }

        // Se cogen los elementos ppor id
        btncancelar = findViewById(R.id.btnCancelar);
        btnguardar = findViewById(R.id.btnguardar);
        Nombreusuario = findViewById(R.id.Nombre);
        direccion = findViewById(R.id.direccion);
        editemail = findViewById(R.id.editemail);
        editpassword = findViewById(R.id.editTpass);
        editphone = findViewById(R.id.editel);
        // se abre conexión con la base de datos
        miBD GestorDB = new miBD(this, "MiBD", null, 2);
        bd = GestorDB.getWritableDatabase();
        //
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editemail.getText().toString();
                String password = editpassword.getText().toString();
                String nombreusuarios = Nombreusuario.getText().toString();
                String direc = direccion.getText().toString();
                String phone = editphone.getText().toString();


              //  registrarenbdlocal(nombreusuarios, password, email, phone, direc);
                try {
                    registrarenbdremota(nombreusuarios, password, email, phone, direc);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("locale", local);

    }

    public void registrarenbdlocal(String nombreusuarios, String password, String email, String phone, String direc) {
        // se comprueba que el usuario no exista en la base de datos y se añade a la base de datos.
        Cursor cursor = bd.rawQuery("SELECT COUNT(1) FROM Usuarios WHERE email = '" + email + "'", null);
        cursor.moveToFirst();
        boolean existe = cursor.getString(0).equals("1");
        cursor.close();
        if (!existe) {
            ContentValues nuevo = new ContentValues();
            nuevo.put("Nombre", nombreusuarios);
            nuevo.put("Password", password);
            nuevo.put("email", email);
            nuevo.put("telefono", phone);
            nuevo.put("direc", direc);
            bd.insert("Usuarios", null, nuevo);
            bd.close();
            Intent inten = new Intent(Register.this, LoginActivity.class);
             startActivity(inten);
        } else {
            Toast.makeText(Register.this, "Datos incorrectos!", Toast.LENGTH_LONG).show();
        }

    }

    public void registrarenbdremota(String nombreusers, String password, String email, String phone, String direc) throws IOException {


        String php = "https://134.209.235.115/ebracamonte001/WEB/usuarios.php";
        JSONObject parametrosJSON1 = new JSONObject();
        parametrosJSON1.put("Nombre",nombreusers);
        parametrosJSON1.put("Password",password);
        parametrosJSON1.put("email",email);
        parametrosJSON1.put("telefono",phone);
        parametrosJSON1.put("direc",direc);

        BDremota bd = new BDremota(contexto,parametrosJSON1, php);
        bd.execute();

        Intent inten3 = new Intent(Register.this, LoginActivity.class);
        startActivity(inten3);


    }

    @Override
    public void processFinish(String output) throws ParseException {

    }
}