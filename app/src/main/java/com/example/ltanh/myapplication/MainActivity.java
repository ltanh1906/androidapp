package com.example.ltanh.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;
    private Button startButton;
    private CountDownTimer countDownTimer;
    private boolean isCountingDown = false;
    private int selectedTime = 10; // Thời gian đếm ngược mặc định là 10 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        startButton = (Button) findViewById(R.id.startButton);

        // Cập nhật TextView khi người dùng điều chỉnh SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedTime = progress;
                textView.setText(selectedTime + "s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Bắt đầu hoặc dừng bộ đếm ngược khi nhấn nút
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCountingDown) {
                    stopCountDown();
                } else {
                    startCountDown(selectedTime * 1000); // Chuyển giây thành mili giây
                }
            }
        });

    }

    private void startCountDown(int timeInMillis) {
        isCountingDown = true;
        startButton.setText("Stop");
        seekBar.setEnabled(false);

        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                textView.setText("Hết giờ");
                stopCountDown();
            }
        };

        countDownTimer.start();
    }

    private void stopCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isCountingDown = false;
        startButton.setText("Start");
        seekBar.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}
