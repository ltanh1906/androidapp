package com.example.ltanh.myapplication;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static StringBuilder logBuilder = new StringBuilder();
    private TextView tvLogs;
    public static TextToSpeech tts;
    private List<Rule> ruleList = new ArrayList<>();
    private RuleAdapter ruleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!NotificationPermissionUtils.isNotificationListenerEnabled(this)) {
            NotificationPermissionUtils.requestNotificationAccess(this);
        }

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.getDefault());
                    Toast.makeText(MainActivity.this, "Text-to-Speech initialized successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to initialize Text-to-Speech!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Button addRuleButton = (Button) findViewById(R.id.add_rule_button);

        // Thiết lập RecyclerView
        ruleList = RuleManager.loadRules(this);
        ruleAdapter = new RuleAdapter(this, ruleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ruleAdapter);

        // Xử lý nút Add Rule
        addRuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddRuleDialog();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save rules to SharedPreferences when the app is paused
        RuleManager.saveRules(this, ruleList);
    }

    public static void appendLog(String log) {
        logBuilder.append(log).append("\n");
    }

    private void updateLogs(String log) {
        appendLog(log);
        tvLogs.setText(logBuilder.toString());
    }
    private void showAddRuleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_rule, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();

        // Lấy các view từ dialog
        final TextView packageNameInput = (TextView) dialogView.findViewById(R.id.package_name_input);
        final TextView regexInput = (TextView) dialogView.findViewById(R.id.regex_input);
        Button confirmButton = (Button) dialogView.findViewById(R.id.add_rule_confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = packageNameInput.getText().toString();
                String regex = regexInput.getText().toString();

                if (!packageName.isEmpty() && !regex.isEmpty()) {
                    ruleList.add(new Rule(packageName, regex));
                    ruleAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
