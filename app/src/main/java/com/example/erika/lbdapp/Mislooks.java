package com.example.erika.lbdapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Mislooks extends AppCompatActivity implements BDremota.AsyncResponse{
    ImageView elImageView;
    Button btn;
    Button btn2;
    Button guardar;
    Button publicar;
    Context contexto = this;
    int CODIGO_GALERIA = 111;
    int CODIGO_FOTO =222;
    Bitmap laminiatura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mislooks);
        elImageView = findViewById(R.id.imageView2);
       btn = findViewById(R.id.galeria);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent elIntentGal = new Intent(Intent.ACTION_PICK,
                       android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(elIntentGal, CODIGO_GALERIA);
           }
       });
        btn2 = findViewById(R.id.button4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elIntentFoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (elIntentFoto.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(elIntentFoto, CODIGO_FOTO);

                }
            }

        });
        guardar = findViewById(R.id.btnguardar);
        publicar = findViewById(R.id.btnpublicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( laminiatura != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    laminiatura.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] fototransformada = stream.toByteArray();
                    String fotoen64 = Base64.encodeToString(fototransformada, Base64.DEFAULT);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    String nombreimagen = "IMG_" + timeStamp + ".png";

                    String php = "https://134.209.235.115/ebracamonte001/WEB/subirimagen.php";
                    JSONObject parametrosJSON2 = new JSONObject();
                    parametrosJSON2.put("identificador", 0);
                    parametrosJSON2.put("imagen", fotoen64);
                    parametrosJSON2.put("titulo", nombreimagen);

                    BDremota bd = new BDremota(contexto, parametrosJSON2, php);
                    bd.execute();
                }
                else{
                    Toast.makeText(Mislooks.this, "Saca una foto antes de publicarla!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final int IMAGE_MAX_SIZE = 1000000;
        if (requestCode == CODIGO_GALERIA && resultCode == RESULT_OK) {
            Uri imagenSeleccionada = data.getData();
            Log.d("GALERIA", "onActivityResult: " + imagenSeleccionada.toString());
           // elImageView.setImageURI(imagenSeleccionada);
            //Picasso.with(contexto).load(imagenSeleccionada).fit().into(elImageView);
            try {
                laminiatura = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imagenSeleccionada);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int height = laminiatura.getHeight();
            int width = laminiatura.getWidth();

            double y = Math.sqrt(IMAGE_MAX_SIZE
                    / (((double) width) / height));
            double x = (y / height) * width;

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(laminiatura, (int) x,
                    (int) y, true);
            laminiatura.recycle();
            laminiatura = scaledBitmap;
            elImageView.setImageBitmap(laminiatura);
        }

        if (requestCode == CODIGO_FOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            laminiatura = (Bitmap) extras.get("data");
            elImageView.setImageBitmap(laminiatura);



            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            laminiatura.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] fototransformada = stream.toByteArray();
            String fotoen64 = Base64.encodeToString(fototransformada,Base64.DEFAULT);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String nombreimagen = "IMG_" + timeStamp + ".png";

            String php = "https://134.209.235.115/ebracamonte001/WEB/subirimagen.php";
            JSONObject parametrosJSON2 = new JSONObject();
            parametrosJSON2.put("identificador",0);
            parametrosJSON2.put("imagen", fotoen64);
            parametrosJSON2.put("titulo", nombreimagen);

            BDremota bd = new BDremota(contexto,parametrosJSON2, php);
            bd.execute();*/


        }






    }

    @Override
    public void processFinish(String output)  {
        JSONParser parser = new JSONParser();
        JSONObject json1 = null;
        try {
            json1 = (JSONObject) parser.parse(output);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("vwervv", "processFinish: " + json1);
    }
}
