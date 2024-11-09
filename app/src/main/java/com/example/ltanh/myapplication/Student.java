package com.example.ltanh.myapplication;

/**
 * Created by ltanh on 09/11/2024.
 */

public class Student {
    private String name;
    private int age;
    private double averageScore;

    // Constructor
    public Student(String name, int age, double averageScore) {
        this.name = name;
        this.age = age;
        this.averageScore = averageScore;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getAverageScore() {
        return averageScore;
    }

    // Phương thức để hiển thị thông tin học sinh
    public void displayInfo() {
        System.out.println("Tên: " + name + ", Tuổi: " + age + ", Điểm trung bình: " + averageScore);
    }
}
