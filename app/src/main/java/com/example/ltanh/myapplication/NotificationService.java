package com.example.ltanh.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.Handler;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
/**
 * Created by ltanh on 02/12/2024.
 */
public class NotificationService extends NotificationListenerService {
    private List<Rule> ruleList;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        ruleList = RuleManager.loadRules(this);

        String packageName = sbn.getPackageName();
        CharSequence notificationTitle = sbn.getNotification().extras.getCharSequence("android.title");
        CharSequence notificationText = sbn.getNotification().extras.getCharSequence("android.text");

        Log.d("NotificationLogger", "Package: " + packageName);
        Log.d("NotificationLogger", "Title: " + notificationTitle);
        Log.d("NotificationLogger", "Text: " + notificationText);
        Toast.makeText(NotificationService.this, "SMS Received: " + notificationText, Toast.LENGTH_SHORT).show();
        String message = ""+notificationText;
        Log.d("NotificationLogger", "Messenger notification: " + message);
        for (Rule rule : ruleList) {
            if(rule.getPackageName().equals(packageName)){
                Pattern pattern = Pattern.compile(rule.getRegex());
                java.util.regex.Matcher matcher = pattern.matcher(notificationText);
                Log.d("NotificationLogger", "Match package: " + notificationText);
                // Find and speak the matched part
                if (matcher.find()) {
                    String matchedText = matcher.group(); // Extract the matched substring
                    Toast.makeText(NotificationService.this, "Match: "+ matchedText, Toast.LENGTH_SHORT).show();
                    Log.d("NotificationLogger", "Match content: " + matchedText);
                    break;
                }
            }
        }

        // Gửi thông tin đến Activity nếu cần
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d("NotificationLogger", "Notification removed: " + sbn.getPackageName());
    }

    private List<Rule> loadRules() {
        SharedPreferences sharedPreferences = getSharedPreferences("RulePrefs", MODE_PRIVATE);
        String json = sharedPreferences.getString("rules", null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Rule>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
