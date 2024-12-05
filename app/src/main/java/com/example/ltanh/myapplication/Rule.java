package com.example.ltanh.myapplication;

/**
 * Created by ltanh on 05/12/2024.
 */

public class Rule {
    private String packageName;
    private String regex;
    private boolean isEnabled;

    public Rule(String packageName, String regex) {
        this.packageName = packageName;
        this.regex = regex;
        this.isEnabled = true; // Mặc định là bật
    }

    public String getPackageName() {
        return packageName;
    }

    public String getRegex() {
        return regex;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
