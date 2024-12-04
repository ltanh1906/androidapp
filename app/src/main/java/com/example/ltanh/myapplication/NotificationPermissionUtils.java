package com.example.ltanh.myapplication;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;


public class NotificationPermissionUtils {

    public static boolean isNotificationAccessGranted(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        return manager.areNotificationsEnabled();
    }

    public static boolean isNotificationListenerEnabled(Context context) {
        String packageName = context.getPackageName();
        String enabledListeners = Settings.Secure.getString(context.getContentResolver(),
                "enabled_notification_listeners");

        return enabledListeners != null && enabledListeners.contains(packageName);
    }

    public static void requestNotificationAccess(Context context) {
        context.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }
}
