package com.example.erika.lbdapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity implements dialogtalla.dialogtallalistener {
    String valor = "nousuario";
    private SQLiteDatabase bd;
    private ArrayList<Productoobject> productos = new ArrayList<Productoobject>();
    private Context elcontexto = this;
    private String local = Locale.getDefault().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState != null) {
            // se coje el locale y se configura la localización otra vez
            Locale nuevaloc1 = new Locale(savedInstanceState.getString("locale"));
            Locale.setDefault(nuevaloc1);
            Configuration config = new Configuration();
            config.locale = nuevaloc1;
            local = nuevaloc1.toString();
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor = extras.getString("emailuser");

        }
        //inicializamosdatos
        inicializardatos();
        //inicializamos la base de datos
        miBD GestorDB = new miBD(Main3Activity.this, "MiBD", null, 2);
        bd = GestorDB.getWritableDatabase();
        //borramostodo lo que habia anteriormente
        bd.delete("Producto", "", null);
        //creamos la base de datos
        createdatabase();
        // cojemos el array ya rellenado
        productos = getAllProducts();
        RecyclerView lalista = findViewById(R.id.elreciclerview);
        // inicializamos el adapter y le pasmos la lista de productos y el contexto del activity
        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(productos, elcontexto);
        lalista.setAdapter(eladaptador);
        // le ponemos el gridview al recyclerview
        GridLayoutManager elLayoutRejillaIgual = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        lalista.setLayoutManager(elLayoutRejillaIgual);
    }

    public void createdatabase() {

        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Creamos el registro a insertar como objeto ContentValues

            ContentValues nuevo = new ContentValues();

            for (int i = 0; i < productos.size(); i++) {
                nuevo.put("talla", productos.get(i).getTalla());
                nuevo.put("precio", productos.get(i).getPrecio());
                nuevo.put("descripcion", productos.get(i).getDescripcion());
                nuevo.put("imagen", productos.get(i).getImagen());
                nuevo.put("Nombre", productos.get(i).getNombre());
                bd.insert("Producto", null, nuevo);

            }
        }

    }


    public void inicializardatos() {
        //Rellenamos el array con los datos de los productos
        productos.add(new Productoobject(R.drawable.lbd7, getResources().getText(R.string.nombre1).toString(), "S", 500, getResources().getText(R.string.descrip1).toString()));
        productos.add(new Productoobject(R.drawable.lb3,  getResources().getText(R.string.nombre2).toString(), "S", 400, getResources().getText(R.string.descrip2).toString()));
        productos.add(new Productoobject(R.drawable.lbd1,  getResources().getText(R.string.nombre3).toString(), "S", 450, getResources().getText(R.string.descrip3).toString()));
        productos.add(new Productoobject(R.drawable.lbd8,  getResources().getText(R.string.nombre4).toString(), "S", 450, getResources().getText(R.string.descrip4).toString()));
        productos.add(new Productoobject(R.drawable.lbd9,  getResources().getText(R.string.nombre5).toString(), "S", 400, getResources().getText(R.string.descrip5).toString()));
        productos.add(new Productoobject(R.drawable.lbd6,  getResources().getText(R.string.nombre6).toString(), "S", 400, getResources().getText(R.string.descrip6).toString()));
        productos.add(new Productoobject(R.drawable.hola,  getResources().getText(R.string.nombre7).toString(), "S", 400, getResources().getText(R.string.descrip7).toString()));
        productos.add(new Productoobject(R.drawable.lbd2,  getResources().getText(R.string.nombre8).toString(), "S", 400, getResources().getText(R.string.descrip8).toString()));
        productos.add(new Productoobject(R.drawable.lbd5,  getResources().getText(R.string.nombre9).toString(), "S", 400, getResources().getText(R.string.descrip9).toString()));
        productos.add(new Productoobject(R.drawable.lbd10, getResources().getText(R.string.nombre10).toString(), "S", 400, getResources().getText(R.string.descrip10).toString()));

    }

    private ArrayList<Productoobject> getAllProducts() {
        // Seleccionamos todos los registros de la tabla Producto
        Cursor cursor = bd.rawQuery("select * from Producto", null);
        ArrayList<Productoobject> listaproductos = new ArrayList<Productoobject>();

        if (cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y  rellenamos la lista a devolver
            while (cursor.isAfterLast() == false) {
                int imagen = cursor.getInt(cursor.getColumnIndex("imagen"));
                String name = cursor.getString(cursor.getColumnIndex("Nombre"));
                String talla = cursor.getString(cursor.getColumnIndex("talla"));
                int precio = cursor.getInt(cursor.getColumnIndex("precio"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                listaproductos.add(new Productoobject(imagen, name, talla, precio, descripcion));
                cursor.moveToNext();
            }
        }
        return listaproductos;
    }


    protected void onDestroy() {
        // cerramos conexión base de datos antes de destruir el activity
        bd.close();
        super.onDestroy();
    }


    public void applyTexts(Productoobject productoobject) {
        // este es el listener que del dialogTalla, recibe el producto elegido
        Productoobject productonuevo = productoobject;
        // creamos un nuevo objeto y lanzamos un intent al carrodelacompra
        Intent in = new Intent(this, Carrodelacompra.class);
        String nombre = productonuevo.getNombre();
        String talla = productonuevo.getTalla();
        Integer precio = productonuevo.getPrecio();
        String descripcion = productonuevo.getDescripcion();
        // enviamos el emailuser y el producto nuevo lo añadimos a la basededatos
        in.putExtra("emailuser", valor);
        //añadimos el producto a la tabla carrodelacompra en la base de datos, he creado esta tabla para identificar el producto y al usuario al que pertenece
        ContentValues nuevos = new ContentValues();
        nuevos.put("talla", talla);
        nuevos.put("precio", precio);
        nuevos.put("descripcion", descripcion);
        nuevos.put("email", valor);
        nuevos.put("Nombre", nombre);
        bd.insert("Carrodelacompra", null, nuevos);
        startActivity(in);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

}
