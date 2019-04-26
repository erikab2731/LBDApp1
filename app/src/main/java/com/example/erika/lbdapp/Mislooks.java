package com.example.erika.lbdapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.provider.MediaStore.Images;

public class Mislooks extends AppCompatActivity implements BDremota.AsyncResponse{
    ImageView elImageView;
    Button btn;
    Button btn2;
    Button guardar;
    Button publicar;
    Button gal;
    Context contexto = this;
    int CODIGO_GALERIA = 111;
    int CODIGO_FOTO =222;
    Bitmap laminiatura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mislooks);
        elImageView = findViewById(R.id.imageView2);
        if (savedInstanceState != null) {
            //if there is a bundle, use the saved image resource (if one is there)
            elImageView.setImageBitmap((Bitmap) savedInstanceState.getParcelable("laminiatura"));
        }
        gal = findViewById(R.id.btngaleria);
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(Mislooks.this,Galeria.class);
                startActivity(e);
            }
        });
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
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Images.Media.TITLE, "hola");
                values.put(Images.Media.DISPLAY_NAME, "hola");
                values.put(Images.Media.DESCRIPTION, "ergwerreg");
                values.put(Images.Media.MIME_TYPE, "image/jpeg");
                values.put(Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
                values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

               Uri url = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                OutputStream imageOut = null;
                try {
                    imageOut = getContentResolver().openOutputStream(url);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    laminiatura.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    try {
                        imageOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = Images.Thumbnails.getThumbnail(getContentResolver(), id, Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                storeThumbnail(getContentResolver(), miniThumb, id, 50F, 50F,Images.Thumbnails.MICRO_KIND);

               
                //hacer el intent
                Intent i = new Intent(Mislooks.this,Migaleria.class);
                i.putExtra("uri",url);
                startActivity(i);


            }

            private Bitmap storeThumbnail(ContentResolver cr,
                                        Bitmap source,
                                        long id,
                                        float width,
                                        float height,
                                        int kind) {

                Matrix matrix = new Matrix();

                float scaleX = width / source.getWidth();
                float scaleY = height / source.getHeight();

                matrix.setScale(scaleX, scaleY);

                Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                        source.getWidth(),
                        source.getHeight(), matrix,
                        true
                );

                ContentValues values = new ContentValues(4);
                values.put(Images.Thumbnails.KIND,kind);
                values.put(Images.Thumbnails.IMAGE_ID,(int)id);
                values.put(Images.Thumbnails.HEIGHT,thumb.getHeight());
                values.put(Images.Thumbnails.WIDTH,thumb.getWidth());

                Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

                try {
                    OutputStream thumbOut = cr.openOutputStream(url);
                    thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
                    thumbOut.close();
                    return thumb;
                } catch (FileNotFoundException ex) {
                    return null;
                } catch (IOException ex) {
                    return null;
                }



            }
        });
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


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Se guardan la lista de productos y la fecha a mostrar

        savedInstanceState.putParcelable("laminiatura", laminiatura);

    }


}
