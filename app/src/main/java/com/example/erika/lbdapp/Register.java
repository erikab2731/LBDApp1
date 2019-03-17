package com.example.erika.lbdapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Register extends AppCompatActivity {

    private Button btncancelar;
    private Button btnguardar;
    private EditText Nombreusuario;
    private EditText direccion;
    private EditText editemail;
    private EditText editpassword;
    private EditText editphone;
    private SQLiteDatabase bd;
    private String local = Locale.getDefault().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState != null) {
            // se coje el locale y se configura lalocalización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        }


        btncancelar = findViewById(R.id.btnCancelar);
        btnguardar = findViewById(R.id.btnguardar);
        Nombreusuario = findViewById(R.id.Nombre);
        direccion = findViewById(R.id.direccion);
        editemail = findViewById(R.id.editemail);
        editpassword = findViewById(R.id.editTpass);
        editphone = findViewById(R.id.editel);

        miBD GestorDB = new miBD (this, "MiBD", null, 2);
        bd = GestorDB.getWritableDatabase();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editemail.getText().toString();
                String password = editpassword.getText().toString();
                String nombreusuarios = Nombreusuario.getText().toString();
                String direc = direccion.getText().toString();
                String phone= editphone.getText().toString();


                Cursor cursor = bd.rawQuery("SELECT COUNT(1) FROM Usuarios WHERE email = '"+email+"'", null);
                cursor.moveToFirst();
                boolean existe = cursor.getString(0).equals("1");
                cursor.close();
                if (!existe){
                    ContentValues nuevo = new ContentValues();
                    nuevo.put("Nombre", nombreusuarios);
                    nuevo.put("Password", password);
                    nuevo.put("email", email);
                    nuevo.put("telefono", phone);
                    nuevo.put("direc", direc);
                    bd.insert("Usuarios", null, nuevo);
                    Intent inten = new Intent(Register.this, LoginActivity.class);
                    startActivity(inten);
                }
                else {
                    Toast.makeText(Register.this,"Datos incorrectos!",Toast.LENGTH_LONG).show();
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

        savedInstanceState.putString("locale",local);

    }

}
