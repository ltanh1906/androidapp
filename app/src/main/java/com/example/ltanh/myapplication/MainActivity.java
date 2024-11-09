package com.example.ltanh.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView studentListView;
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

        // Thiết lập ListView để hiển thị danh sách học sinh
        studentListView = (ListView) findViewById(R.id.studentListView);

        // Chuyển danh sách học sinh thành danh sách chuỗi
        ArrayList<String> studentInfoList = new ArrayList<>();
        for (Student student : classroom.getStudents()) {
            studentInfoList.add(student.getName() + " - Tuổi: " + student.getAge() + " - Điểm: " + student.getAverageScore());
        }

        // Tạo ArrayAdapter để hiển thị danh sách học sinh trong ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentInfoList);
        studentListView.setAdapter(adapter);

        // Tìm học sinh có điểm trung bình cao nhất và hiển thị
        Student topStudent = classroom.getTopStudent();
        if (topStudent != null) {
            String topStudentInfo = "Học sinh có điểm cao nhất: " + topStudent.getName() +
                    " - Điểm: " + topStudent.getAverageScore();
            Toast.makeText(this, topStudentInfo, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Không có học sinh nào trong lớp.", Toast.LENGTH_SHORT).show();
        }
    }



}
