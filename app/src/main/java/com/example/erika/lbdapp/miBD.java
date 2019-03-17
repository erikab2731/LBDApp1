package com.example.erika.lbdapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class miBD extends SQLiteOpenHelper {

    public String tusuarios = "CREATE TABLE Usuarios('Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Nombre' VARCHAR(50) ,'Password' VARCHAR(10),'email' VARCHAR(50), 'telefono' VARCHAR(50),'direc' VARCHAR(50))";
    public String tproductos = "CREATE TABLE Producto('Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Nombre' VARCHAR(50) , 'talla' Varchar(2), 'precio'  VARCHAR(50),'imagen' INTEGER, 'descripcion' VARCHAR(100))";
    public String tcompra = "CREATE TABLE Compras('CodigoCompra' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'CodigoUsuario' INTEGER , 'PrecioTotal' VARCHAR(50))";
    public String tcarrodelacompra = "CREATE TABLE Carrodelacompra('Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Nombre' VARCHAR(50) , 'talla' Varchar(2), 'email' VARCHAR(50), 'precio'  VARCHAR(50), 'descripcion' VARCHAR(100))";
    public miBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tusuarios);
        db.execSQL(tproductos);
        db.execSQL(tcompra);
        db.execSQL(tcarrodelacompra);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL("DROP TABLE IF EXISTS Producto");
        db.execSQL("DROP TABLE IF EXISTS Compras");
        db.execSQL("DROP TABLE IF EXISTS Carrodelacompra");
        db.execSQL(tusuarios);
        db.execSQL(tproductos);
        db.execSQL(tcompra);
        db.execSQL(tcarrodelacompra);
    }
}

