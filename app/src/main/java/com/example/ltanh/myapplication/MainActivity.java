package com.example.ltanh.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private CountDownTimer countDownTimer;
    private int totalTime = 10000; // Thời gian đếm ngược (10 giây)
    private int interval = 100; // Cập nhật thanh tiến trình mỗi 100 ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);

        startCountDown();
    }

    private void startCountDown() {
        progressBar.setProgress(100); // Đặt giá trị ban đầu cho ProgressBar là 100%

        countDownTimer = new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Tính toán phần trăm còn lại
                int progress = (int) (millisUntilFinished * 100 / totalTime);
                progressBar.setProgress(progress);

                // Hiển thị thời gian còn lại (tính theo giây)
                textView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                // Khi hết giờ, thiết lập ProgressBar về 0 và TextView thành "Hết giờ"
                progressBar.setProgress(0);
                textView.setText("Hết giờ");
            }
        };

        countDownTimer.start(); // Bắt đầu bộ đếm ngược
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}
