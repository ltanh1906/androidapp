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

        // Hiển thị thông báo chào mừng khi ứng dụng khởi động
        Toast.makeText(this, "Chào mừng bạn đến với hệ thống quản lý học sinh!", Toast.LENGTH_SHORT).show();

        // Tạo lớp học và thêm học sinh
        Classroom classroom = new Classroom();
        classroom.addStudent(new Student("An", 16, 8.5));
        classroom.addStudent(new Student("Bình", 17, 9.0));
        classroom.addStudent(new Student("Cường", 16, 7.8));

        // Tìm học sinh có điểm trung bình cao nhất
        Student topStudent = classroom.getTopStudent();
        if (topStudent != null) {
            String topStudentInfo = "Học sinh có điểm cao nhất: " + topStudent.getName() +
                    " - Điểm: " + topStudent.getAverageScore();
            // Hiển thị thông tin học sinh có điểm cao nhất bằng Toast
            Toast.makeText(this, topStudentInfo, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Không có học sinh nào trong lớp.", Toast.LENGTH_SHORT).show();
        }
    }



}
