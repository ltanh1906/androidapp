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

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho tuổi
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0) { // Kiểm tra tuổi hợp lệ
            this.age = age;
        } else {
            throw new IllegalArgumentException("Tuổi phải lớn hơn 0");
        }
    }

    // Getter và Setter cho điểm trung bình
    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        if (averageScore >= 0 && averageScore <= 10) { // Kiểm tra điểm hợp lệ
            this.averageScore = averageScore;
        } else {
            throw new IllegalArgumentException("Điểm phải nằm trong khoảng 0 đến 10");
        }
    }

    // Phương thức để hiển thị thông tin học sinh
    public void displayInfo() {
        System.out.println("Tên: " + name + ", Tuổi: " + age + ", Điểm trung bình: " + averageScore);
    }
}
