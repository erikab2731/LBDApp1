package com.example.erika.lbdapp;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class servicioFirebase extends FirebaseMessagingService {
    public servicioFirebase() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("hola", "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d("firebase", "Message data payload: " + remoteMessage.getData().get("mensaje"));
                String msg =remoteMessage.getData().get("mensaje");


        
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("remote messaging", "Message Notification Body: " + remoteMessage.getNotification().getBody());

            NotificationManager elManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "123");
            // comprobamos si estamos en una versión que aprueba canales en las notificaciones

            // Configuramos la notificación, le ponemos el icono de flor, titulo y texto, y la fecha elegida.
            elBuilder.setSmallIcon(R.drawable.flor)
                    .setContentTitle((getResources().getText(R.string.app_name)))
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSubText((getResources().getText(R.string.felec)).toString()+remoteMessage.getData().get("fecha"))
                    .setVibrate(new long[]{0, 1000, 500, 1000})
                    .setAutoCancel(false);

            elManager.notify(1, elBuilder.build());

        }
    }



}
