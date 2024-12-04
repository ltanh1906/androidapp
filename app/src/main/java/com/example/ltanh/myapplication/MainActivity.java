package com.example.ltanh.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static StringBuilder logBuilder = new StringBuilder();
    private TextView tvLogs;
    public static TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Kiểm tra quyền truy cập thông báo
        if (!NotificationPermissionUtils.isNotificationListenerEnabled(this)) {
            // Hiển thị thông báo hoặc yêu cầu quyền
            NotificationPermissionUtils.requestNotificationAccess(this);
        }


        tvLogs = (TextView) findViewById(R.id.tvLogs);

        // Gọi hàm cập nhật log
        updateLogs("Ứng dụng đã khởi chạy. Đang chờ thông báo...");
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.getDefault());
                    Toast.makeText(MainActivity.this, "Text-to-Speech initialized successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to initialize Text-to-Speech!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static void appendLog(String log) {
        logBuilder.append(log).append("\n");
    }

    private void updateLogs(String log) {
        appendLog(log);
        tvLogs.setText(logBuilder.toString());
    }

}
