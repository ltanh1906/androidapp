package com.example.ltanh.myapplication;

/**
 * Created by ltanh on 09/11/2024.
 */

import java.util.ArrayList;

public class Classroom {
    private ArrayList<Student> students;

    // Constructor
    public Classroom() {
        students = new ArrayList<>();
    }

    // Thêm học sinh vào lớp
    public void addStudent(Student student) {
        students.add(student);
    }

    // Hiển thị danh sách học sinh
    public void displayStudents() {
        System.out.println("Danh sách học sinh:");
        for (Student student : students) {
            student.displayInfo();
        }
    }

    public void removeStudent(int position) {
        if (position >= 0 && position < students.size()) {
            students.remove(position);
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    // Tìm học sinh có điểm trung bình cao nhất
    public Student getTopStudent() {
        if (students.isEmpty()) {
            return null;
        }

        Student topStudent = students.get(0);
        for (Student student : students) {
            if (student.getAverageScore() > topStudent.getAverageScore()) {
                topStudent = student;
            }
        }
        return topStudent;
    }
}
