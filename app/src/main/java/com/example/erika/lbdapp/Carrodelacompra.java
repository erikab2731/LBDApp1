package com.example.erika.lbdapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Carrodelacompra extends AppCompatActivity {
    private String valor = "valorinicialemail";
    private Button btnelegir;
    private Button btncomprar;
    private Button btnguardar;
    private Button recordatorio;
    private SQLiteDatabase bd;
    private TextView fecha;
    private ArrayList<Productoobject> losproductos = new ArrayList<Productoobject>();
    ElAdaptadorRecycler1 eladaptador;
    private String local = Locale.getDefault().toString();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrodelacompra);
        super.onCreate(savedInstanceState);
        // Se coje la toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // las dos opciones siguientes a true nos permiten utilizar la flecha que esta en la toolbar  para volver hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Se coje el "emailuser" del intent que nos indica el usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor = extras.getString("emailuser");
        }
        // se coje la fecha por id
        fecha = findViewById(R.id.fechaeleg);

        if (savedInstanceState != null) {
            // si se ha reiniciado la activity recojemostodo lo guardado
            fecha.setText(savedInstanceState.getString("valorfecha"));
            losproductos = savedInstanceState.getParcelableArrayList("key");
                 // se coje el locale y se configura lalocalización otra vez
            Log.d("tag", "onCreate: "+(savedInstanceState.getString("locale")));
                Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
                Locale.setDefault(nuevaloc1);
                Configuration config = new Configuration();
                config.locale = nuevaloc1;
                local = nuevaloc1.toString();
                getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        }
        // cojemos el view del recyclerview
        RecyclerView lalista = findViewById(R.id.recyclerview1);
        // inicializamos la lista de los productos a mostrar en el carro de la compra
        // por defecto cada vez que un usuario añada un elemento al carro  siempre se guarda en la base de datos
        losproductos = getAllProducts();
        // le pasamos al adapter la lista anterior
        inicializaradapter(losproductos, lalista);
        // cojemos el boton por id y si se pulsa en el boton elegir creamos un dialogo para recoger la fecha
        btnelegir =findViewById(R.id.button3);
        btnelegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogoFecha = new ClaseDialogoFecha();
                dialogoFecha.show(getSupportFragmentManager(), "Date Picker");
            }
        });
        // cojemos el boton comprar por id y si se pulsa en el botón  creamos la activity que nos confirma la compra
        btncomprar =findViewById(R.id.button7);
        btncomprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogo2 alertdialog = new alertdialogo2();
                alertdialog.show(((FragmentActivity) Carrodelacompra.this).getSupportFragmentManager(), "etiqueta");
            }

        });


        // cojemos el botón guardar por id y si se pulsa lanzamos un intent implicito intent.action_send
        // que nos permite compartir con otras aplicaciones del móvil, por ejemplo whatsapp, notes , gmail, etc

        btnguardar =findViewById(R.id.button9);
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        // se lanza un intent de tipo intent.action_send
                        Intent i = new Intent(Intent.ACTION_SEND);
                        // se cambbia el tipo a texto plano
                        i.setType("text/plain");
                        // se añade al intent el asunto
                        i.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.listadelacompra);
                        // Se crea la variable text1 que nos ayudara a concatenar los nombres de los atributos de la lista de productos
                        String text1 = "";
                        for (int x = 0 ; x < losproductos.size(); x++ ){
                                String text = losproductos.get(x).getNombre() + losproductos.get(x).getDescripcion()+losproductos.get(x).getTalla()+losproductos.get(x).getPrecio()+ "\n";
                                text1 = text1 + text;
                        }
                        // cuando ya concatenamos todos los valores de la lista la añadimos al intent
                        i.putExtra(android.content.Intent.EXTRA_TEXT,text1);
                        // lanzamos el activity con el titulo
                        startActivity(Intent.createChooser(i, getResources().getText(R.string.lista)));
                    }
        });
        // cojemos la etiqueta reordatorio por id y si el usuario pulsa en el botón
        // lanzamos la notificación
        recordatorio = findViewById(R.id.button);
        recordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager elManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "123");
                // comprobamos si estamos en una versión que aprueba canales en las notificaciones
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel elCanal = new NotificationChannel("123", "canal", NotificationManager.IMPORTANCE_DEFAULT);
                    elManager.createNotificationChannel(elCanal);
                    elCanal.setDescription(getResources().getText(R.string.canal).toString());
                    elCanal.enableLights(true);
                    elCanal.setLightColor(Color.RED);
                    elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                    elCanal.enableVibration(true);
                }
                // Configuramos la notificación, le ponemos el icono de flor, titulo y texto, y la fecha elegida.
                elBuilder.setSmallIcon(R.drawable.flor)
                        .setContentTitle((getResources().getText(R.string.app_name)))
                        .setContentText((getResources().getText(R.string.tienes)))
                        .setSubText((getResources().getText(R.string.felec)).toString() + fecha.getText())
                        .setVibrate(new long[]{0, 1000, 500, 1000})
                        .setAutoCancel(true);

                elManager.notify(1, elBuilder.build());

            }
        });
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Se guardan la lista de productos y la fecha a mostrar
        savedInstanceState.putParcelableArrayList("key", losproductos);
        savedInstanceState.putString("valorfecha", fecha.getText().toString());
        savedInstanceState.putString("locale",local);

    }

    private void removeProducto(int position) {
        //quitamos un producto de la lista de productos
        losproductos.remove(position);
        // Notificamos de un item borrado en nuestro array
        eladaptador.notifyItemRemoved(position);
    }


    public void inicializaradapter(ArrayList<Productoobject> lista, RecyclerView lalista) {

        eladaptador = new ElAdaptadorRecycler1(lista, this, new ElAdaptadorRecycler1.OnItemClickListener() {
            @Override
            public void onItemClick(Productoobject productoobject, int position) {
                //Cuando se pulsa sobre un elemento del recyclerview se borra de la base de datos
                //anteriormente cuando hemos cojido el producto del catalogo hemos cambiado el codigo de la imágen por uno nuevo que nos ervira de identificador.
                Integer codigo = productoobject.getImagen();
                miBD GestorDB = new miBD(Carrodelacompra.this, "MiBD", null, 2);
                bd = GestorDB.getWritableDatabase();
                bd.delete("Carrodelacompra", "Codigo='" + codigo + "'", null);
                bd.close();
                removeProducto(position);
            }
        });
        // actualizamos el adapter de la lista y el layoutmanager
        lalista.setAdapter(eladaptador);
        GridLayoutManager elLayoutRejillaIgual = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        lalista.setLayoutManager(elLayoutRejillaIgual);
    }

    private ArrayList<Productoobject> getAllProducts() {
        miBD GestorDB = new miBD(Carrodelacompra.this, "MiBD", null, 2);
        bd = GestorDB.getWritableDatabase();
        // Seleccionamos todos los registros de la tabla Carro de la compra
        Cursor cursor = bd.rawQuery("SELECT * FROM Carrodelacompra WHERE email = '" + valor + "'", null);
        ArrayList<Productoobject> listaproductos = new ArrayList<Productoobject>();

        if (cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que luego devolveremos
            while (cursor.isAfterLast() == false) {
                Integer codigoproducto = cursor.getInt(cursor.getColumnIndex("Codigo"));
                String name = cursor.getString(cursor.getColumnIndex("Nombre"));
                String talla = cursor.getString(cursor.getColumnIndex("talla"));
                int precio = cursor.getInt(cursor.getColumnIndex("precio"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                listaproductos.add(new Productoobject(codigoproducto, name, talla, precio, descripcion));
                cursor.moveToNext();
            }
        }
        bd.close();
        return listaproductos;
    }



    @Override
    public boolean onSupportNavigateUp() {
        // se utiliza este método para la flecha del toolbar
        onBackPressed();
        return true;
    }

}
