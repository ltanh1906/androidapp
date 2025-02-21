package com.example.ltanh.myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static StringBuilder logBuilder = new StringBuilder();
    private TextView tvLogs;
    public static TextToSpeech tts;
    private List<Rule> ruleList = new ArrayList<>();
    private RuleAdapter ruleAdapter;
    private boolean serviceEnabled = true; // Track if the service is enabled
    private float speechRate = 1.0f; // Default speech rate

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
                    tts.setSpeechRate(speechRate); // Apply stored speech rate
                    Toast.makeText(MainActivity.this, "Text-to-Speech initialized successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Failed to initialize Text-to-Speech!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Button addRuleButton = (Button) findViewById(R.id.add_rule_button);
        Switch enableServiceSwitch = (Switch) findViewById(R.id.enable_service_switch);
        Button speechSettingsButton = (Button) findViewById(R.id.speech_settings_button);

        // Set up the service enable/disable switch
        enableServiceSwitch.setChecked(serviceEnabled);
        enableServiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                serviceEnabled = isChecked;
                Toast.makeText(MainActivity.this,
                        isChecked ? "Service enabled" : "Service disabled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Set up speech settings button
        speechSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpeechSettingsDialog();
            }
        });

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
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter("NotificationMessage"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // BroadcastReceiver nhận tin nhắn từ NotificationService
    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("MainActivity", "Received message: " + message);
            if (message != null && serviceEnabled) { // Only speak if service is enabled
                speakMessage(message);
            }
        }
    };
    // Đọc tin nhắn bằng TextToSpeech
    private void speakMessage(String message) {
        if (tts != null) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Save rules to SharedPreferences when the app is paused
        RuleManager.saveRules(this, ruleList);
        // Save other settings
        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putBoolean("service_enabled", serviceEnabled)
                .putFloat("speech_rate", speechRate)
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load settings
        serviceEnabled = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getBoolean("service_enabled", true);
        speechRate = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getFloat("speech_rate", 1.0f);

        // Update UI to reflect loaded settings
        Switch enableServiceSwitch = (Switch) findViewById(R.id.enable_service_switch);
        if (enableServiceSwitch != null) {
            enableServiceSwitch.setChecked(serviceEnabled);
        }

        // Apply speech rate if TTS is initialized
        if (tts != null) {
            tts.setSpeechRate(speechRate);
        }
    }

    public static void appendLog(String log) {
        logBuilder.append(log).append("\n");
    }

    private void updateLogs(String log) {
        appendLog(log);
        tvLogs.setText(logBuilder.toString());
    }

    private void showSpeechSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_speech_settings, null);
        builder.setView(dialogView);
        builder.setTitle("Speech Settings");

        final AlertDialog dialog = builder.create();
        dialog.show();

        // Get view references
        final SeekBar rateSeekBar = (SeekBar) dialogView.findViewById(R.id.speech_rate_seekbar);
        final TextView rateValueText = (TextView) dialogView.findViewById(R.id.speech_rate_value);
        Button testButton = (Button) dialogView.findViewById(R.id.test_speech_button);
        Button saveButton = (Button) dialogView.findViewById(R.id.save_settings_button);

        // Set up the seek bar
        rateSeekBar.setProgress((int)(speechRate * 10));
        rateValueText.setText(String.format("%.1fx", speechRate));

        rateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float rate = progress / 10.0f;
                if (rate < 0.1f) rate = 0.1f;
                rateValueText.setText(String.format("%.1fx", rate));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Test button
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rate = rateSeekBar.getProgress() / 10.0f;
                if (rate < 0.1f) rate = 0.1f;
                if (tts != null) {
                    tts.setSpeechRate(rate);
                    tts.speak("Xin chào", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        // Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRate = rateSeekBar.getProgress() / 10.0f;
                if (speechRate < 0.1f) speechRate = 0.1f;
                if (tts != null) {
                    tts.setSpeechRate(speechRate);
                }
                Toast.makeText(MainActivity.this, "Speech settings saved", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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