package com.example.erika.lbdapp;

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
            Log.d("firebase", "Message data payload: " + remoteMessage.getData());
                String msg =remoteMessage.getData().toString();


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("remote messaging", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleNow() {
    }

    private void scheduleJob() {
    }

    public void onNewToken(String s) {
        super.onNewToken(s);
        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String s) {
    }

}
