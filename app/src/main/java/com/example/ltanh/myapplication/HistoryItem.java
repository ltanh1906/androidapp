package com.example.ltanh.myapplication;

public class HistoryItem {
    private String packageName;
    private String message;
    private String timestamp;

    public HistoryItem(String packageName, String message, String timestamp) {
        this.packageName = packageName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
