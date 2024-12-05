package com.example.ltanh.myapplication;

/**
 * Created by ltanh on 05/12/2024.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private static final String PREFS_NAME = "RulePrefs";
    private static final String RULES_KEY = "rules";

    public static void saveRules(Context context, List<Rule> rules) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Serialize the list of rules to JSON
        Gson gson = new Gson();
        String json = gson.toJson(rules);

        editor.putString(RULES_KEY, json);
        editor.apply();
    }

    public static List<Rule> loadRules(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(RULES_KEY, null);

        if (json == null) {
            return new ArrayList<>(); // Return empty list if no data is saved
        }

        // Deserialize the JSON back into a list of rules
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rule>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
