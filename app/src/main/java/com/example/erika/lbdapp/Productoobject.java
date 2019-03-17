package com.example.erika.lbdapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Productoobject implements Parcelable {
    private Integer imagen;
    private String  nombre;
    private String talla;
    private Integer precio;
    private String descripcion;


    public Productoobject(Integer pimagen, String pnombre, String ptalla, Integer pprecio, String pdescripcion){
        this.imagen = pimagen;
        this.nombre= pnombre;
        this.talla = ptalla;
        this.precio = pprecio;
        this.descripcion = pdescripcion;

    }

    public Productoobject(Parcel in) {
        imagen = in.readInt();
        nombre = in.readString();
        talla = in.readString();
        precio = in.readInt();
        descripcion = in.readString();

    }

    public String getNombre() {
        return nombre;
    }

    public String getTalla() {
        return talla;
    }

    public Integer getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imagen);
        dest.writeString(nombre);
        dest.writeString(talla);
        dest.writeInt(precio);
        dest.writeString(descripcion);
    }

    public static final Parcelable.Creator<Productoobject> CREATOR = new Parcelable.Creator<Productoobject>() {
        public Productoobject createFromParcel(Parcel in) {
            return new Productoobject(in);
        }

        public Productoobject[] newArray(int size) {
            return new Productoobject[size];
        }
    };




}
