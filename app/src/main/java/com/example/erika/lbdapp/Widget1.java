package com.example.erika.lbdapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Widget1  extends AppWidgetProvider {


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            actualizarWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void actualizarWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget1);
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String horaconformato = formato.format(calendario.getTime());
        views.setTextViewText(R.id.etiqueta, horaconformato);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
