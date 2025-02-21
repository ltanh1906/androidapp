package com.example.ltanh.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.Handler;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
/**
 * Created by ltanh on 02/12/2024.
 */
public class NotificationService extends NotificationListenerService {
    private List<Rule> ruleList;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        ruleList = RuleManager.loadRules(this);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String packageName = sbn.getPackageName();
        CharSequence notificationTitle = sbn.getNotification().extras.getCharSequence("android.title");
        CharSequence notificationText = sbn.getNotification().extras.getCharSequence("android.text");
        saveNotification(packageName, notificationText.toString() ,timestamp);
        Toast.makeText(NotificationService.this, "SMS Received: " + notificationText, Toast.LENGTH_SHORT).show();
        String message = ""+notificationText;
        for (Rule rule : ruleList) {
            if (rule.getPackageName().equals(packageName)) {
                Pattern pattern = Pattern.compile(rule.getRegex());
                java.util.regex.Matcher matcher = pattern.matcher(notificationText);
                Log.d("NotificationLogger", "Match package: " + notificationText);
                Log.d("NotificationLogger", "Pattern: " + pattern);

                // Find and speak the matched part
                if (matcher.find()) {
                    String matchedText = matcher.group()+"VND"; // Extract the matched substring
                    Toast.makeText(NotificationService.this, "Match: " + matchedText, Toast.LENGTH_SHORT).show();
                    Log.d("NotificationLogger", "Match content: " + matchedText);
                    // Gửi nội dung về MainActivity
                    sendNotificationToActivity(matchedText);
                } else {
                    sendNotificationToActivity("Xin chào");
                }
            }
        }

        // Gửi thông tin đến Activity nếu cần
    }
    private void sendNotificationToActivity(String message) {
        Intent intent = new Intent("NotificationMessage");
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
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

    private void saveNotification(String packageName, String message, String timestamp) {
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<HistoryItem> historyList = loadHistory();
        historyList.add(0, new HistoryItem(packageName, message, timestamp)); // Thêm vào đầu danh sách

        Gson gson = new Gson();
        editor.putString("history", gson.toJson(historyList));
        editor.apply();
    }

    private List<HistoryItem> loadHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationHistory", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("history", null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<HistoryItem>>() {}.getType();
        return gson.fromJson(json, type);
    }


}
