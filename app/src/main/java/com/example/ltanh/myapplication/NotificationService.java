package com.example.ltanh.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Looper;
import android.os.Handler;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ltanh on 02/12/2024.
 */
public class NotificationService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        CharSequence notificationTitle = sbn.getNotification().extras.getCharSequence("android.title");
        CharSequence notificationText = sbn.getNotification().extras.getCharSequence("android.text");

        Log.d("NotificationLogger", "Package: " + packageName);
        Log.d("NotificationLogger", "Title: " + notificationTitle);
        Log.d("NotificationLogger", "Text: " + notificationText);
        Toast.makeText(NotificationService.this, "SMS Received: " + notificationText, Toast.LENGTH_SHORT).show();
        String message = ""+notificationText;
        Log.d("NotificationLogger", "Messenger notification: " + message);

        // Use TTS to read the notification
        if (MainActivity.tts != null) {
            MainActivity.tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }

        // Gửi thông tin đến Activity nếu cần
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d("NotificationLogger", "Notification removed: " + sbn.getPackageName());
    }

}
