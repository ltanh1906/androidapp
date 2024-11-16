package com.example.ltanh.myapplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayAdapter<String> adapter;
    private ArrayList<String> studentInfoList;
    private Classroom classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hiển thị thông báo chào mừng khi ứng dụng khởi động
        Toast.makeText(this, "Chào mừng bạn đến với hệ thống quản lý học sinh!", Toast.LENGTH_SHORT).show();

        // Tạo lớp học và thêm học sinh
        classroom = new Classroom();
        classroom.addStudent(new Student("An", 16, 8.5));
        classroom.addStudent(new Student("Bình", 17, 9.0));
        classroom.addStudent(new Student("Cường", 16, 7.8));

        // Khởi tạo ListView để hiển thị danh sách học sinh
        studentListView = (ListView) findViewById(R.id.studentListView);
        Button addStudentButton = (Button) findViewById(R.id.addStudentButton);
        updateStudentList();

        // Xử lý sự kiện nhấn để thêm học sinh mới
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentDialog(null, -1); // Thêm học sinh mới
            }
        });


    }

    private void updateStudentList() {
        // Chuyển danh sách học sinh thành danh sách chuỗi để hiển thị
        studentInfoList = new ArrayList<>();
        for (Student student : classroom.getStudents()) {
            studentInfoList.add(student.getName() + " - Tuổi: " + student.getAge() + " - Điểm: " + student.getAverageScore());
        }

        // Tạo và cập nhật adapter cho ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentInfoList);
        studentListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void showStudentDialog(final Student student, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Tạo giao diện nhập thông tin
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_student, null);
        builder.setView(dialogView);

        final EditText nameInput = (EditText) dialogView.findViewById(R.id.studentNameInput);
        final EditText ageInput =(EditText) dialogView.findViewById(R.id.studentAgeInput);
        final EditText scoreInput =(EditText) dialogView.findViewById(R.id.studentScoreInput);



        // Nút xác nhận
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();
                int age = Integer.parseInt(ageInput.getText().toString());
                double score = Double.parseDouble(scoreInput.getText().toString());

                if (student == null) {
                    // Thêm mới học sinh
                    classroom.addStudent(new Student(name, age, score));
                    Toast.makeText(MainActivity.this, "Đã thêm học sinh.", Toast.LENGTH_SHORT).show();
                }

                updateStudentList(); // Cập nhật danh sách hiển thị
            }
        });

        // Nút hủy
        builder.setNegativeButton("Hủy", null);

        builder.create().show();
    }


}
