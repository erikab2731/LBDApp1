package com.example.erika.lbdapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText editTextemail;
    private EditText editTextPassword;
    private Button login;
    private Switch switchRecordar;
    private Button registro;
    private Button catalogo;
    private SQLiteDatabase bd;
    private Button idioma;
    String nuevalocale ="";
    Boolean sehacambiadoidioma = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login);

        // se cojen los valores guardados en el método onSaveInstanceState
        if (savedInstanceState != null) {
            // se coje el locale y se configura la localización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
           nuevalocale = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //método para inicializar todos los elementos
        cojerelementos();
        // se cojen las preferencias del usuario
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        // Se inicializa el gestor de Bases de Datos
        miBD GestorDB = new miBD (this, "MiBD", null, 2);
        bd = GestorDB.getWritableDatabase();
        //  el metodo setcredencialesdeusuario coje las preferencias del usuario en este caso , el email y el password
        setcredencialesdeusuario();

        login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String email = editTextemail.getText().toString();
                 String password = editTextPassword.getText().toString();
                //Se cojen el email y el password
                 // en el método login se comprueba que el email y el password es válido
                 if (login(email,password)){
                     //Sitodo va bien se lanza un intent hacia el main2activity, que es la actividad que representa el menú principal
                     Intent intent= new Intent(LoginActivity.this,Main2Activity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     //se envia el email que es el que se necesitará para controlar que usuario esta en cada activity
                     intent.putExtra("email",email);
                     if(sehacambiadoidioma){
                         // si se ha cambiado el idioma cojemos el nuevolocale
                         intent.putExtra("locale",nuevalocale);
                     }
                     startActivity(intent);
                     // Se llama a al método para guardar preferencias
                     saveOnPreferences(email,password);
                 }
             }
         });

        registro.setOnClickListener(new View.OnClickListener() {
            //Si el usuario no se ha registrado y pulsa en este botón, se lanza un intent hacia el activity de registro
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
        //Si el usuario pulsa en el botón de sólo compra, se lanza un intent hacia el cátalogo
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
                startActivity(intent);
            }
        });
        //Si el usuario pulsa en el botón cambiar idioma, se cambiara de español a ingles o de ingles a español
        idioma= findViewById(R.id.idioma);
        idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sehacambiadoidioma = true;
                 Locale nuevaloc;
                Configuration config = new Configuration();
                // Se compara la localizacion actual si esta en español se cambia a ingles sino, de ingles a español
                if (Locale.getDefault().toString().equals("es")){
                    nuevaloc = new Locale("en");
                    Locale.setDefault(nuevaloc);
                    config.locale = nuevaloc;
                }
                else {
                    nuevaloc = new Locale("es");
                    Locale.setDefault(nuevaloc);
                    config.locale = nuevaloc;
                }
                 nuevalocale = nuevaloc.toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("nuevaconfi", nuevalocale);
                editor.commit();
                editor.apply();
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
                finish();
                startActivity(getIntent());

            }
        });


    }

    private void setcredencialesdeusuario() {
        //Se cojen el email y el password de las preferencias
        String email = getUsermailPrefs();
        String pass = getUserpassPrefs();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            //Si no es nulo, se  hacen set de los edittext
            editTextemail.setText(email);
            editTextPassword.setText(pass);
        }
    }

    private void cojerelementos(){
        // Se asigna cada elemento por su correspondiente id
        editTextemail = findViewById(R.id.editTextemail);
        editTextPassword =  findViewById(R.id.textpassword);
        login =  findViewById(R.id.loginbutton);
        switchRecordar =  findViewById(R.id.switch1);
        registro = findViewById(R.id.button11);
        catalogo = findViewById(R.id.button13);

    }
    private boolean login(String email, String password){

        Cursor cursor = bd.rawQuery("SELECT COUNT(1) FROM Usuarios WHERE email = '"+email+"'AND Password = '" + password + "'", null);

        cursor.moveToFirst();
        boolean existe = cursor.getString(0).equals("1");

        if(!emailvalido(email)){
            Toast.makeText(this,R.string.emailnovalido, Toast.LENGTH_LONG).show();
            return false;
        } else if (!passwordvalido(password)){
            Toast.makeText(this,R.string.contranovalida,Toast.LENGTH_LONG).show();
            return false;
        } else {
            if(existe){
                return true;
            } else { Toast.makeText(this,R.string.usernotregister ,Toast.LENGTH_LONG).show();
                return false;
            }

        }
    }
    //validamos el email
    private boolean  emailvalido(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    // método para comprobar si el password tiene más de 4 caracteres
    private boolean  passwordvalido(String password){
        return (password.length() > 4);
    }

    private void saveOnPreferences(String email, String password){
        if(switchRecordar.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass",password);
            editor.commit();
            editor.apply();
        }
    }

    private String getUsermailPrefs(){
        return prefs.getString("email", "");
    }

    private String getUserpassPrefs(){
        return prefs.getString("pass", "");
    }

    protected void onDestroy() {
        // cerramos conexión base de datos antes de destruir el activity
        bd.close();
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        String nueva= prefs.getString("nuevaconfi", "");
        nuevalocale = nueva;
        savedInstanceState.putString("locale",nuevalocale);

    }
}
