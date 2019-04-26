package com.example.erika.lbdapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DescargarImagen extends AsyncTask<Void, Void, Bitmap> {

    public DescargarImagen.AsyncResponse delegate=null;
    private Context mContext;
    private JSONObject datos;
    private String url;


    public DescargarImagen (Context context, String purl) {
        delegate = (DescargarImagen.AsyncResponse)context;
        mContext = context;
        url = purl;

    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
         URL destino = null;
        Bitmap elBitmap = null;
        try {
            destino = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection conn= GeneradorConexionesSeguras.getInstance().crearConexionSegura(mContext,url);
        int responseCode = 0;
        try {
            responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                elBitmap = BitmapFactory.decodeStream(conn.getInputStream());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elBitmap;

    }

    protected void onPostExecute(final Bitmap result) {

        if(result != null){
            Log.i("tag", "onPostExecute: " + result);
            try {
                delegate.processFinish(result);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    public interface AsyncResponse {
        void processFinish(Bitmap output) throws ParseException;
    }
}
