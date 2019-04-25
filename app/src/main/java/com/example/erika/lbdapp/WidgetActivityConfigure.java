package com.example.erika.lbdapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WidgetActivityConfigure extends AppCompatActivity {
     private int idWidgetAConfigurar;
     private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configure);

        setResult(RESULT_CANCELED);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
             idWidgetAConfigurar = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultadoConfig = new Intent();
        resultadoConfig.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, idWidgetAConfigurar);
        setResult(RESULT_OK, resultadoConfig);
        finish();


    }

    protected void onUpdate(Bundle savedInstanceState){
        AppWidgetManager managerWidgets = AppWidgetManager.getInstance(context);
        Widget1.actualizarWidget(context, managerWidgets, idWidgetAConfigurar);
    }

}
