package com.example.erika.lbdapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class Migaleria extends AppCompatActivity {
    ImageView img;
    Uri uridinamica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migaleria);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uridinamica = (Uri) extras.get("uri");
            Log.d("lauridinamica es ", " : "+ uridinamica);
        }
      img = findViewById(R.id.img);
        try {
          Bitmap bit = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uridinamica);
          img.setImageBitmap(bit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
