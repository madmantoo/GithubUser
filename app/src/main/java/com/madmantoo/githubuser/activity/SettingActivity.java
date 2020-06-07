package com.madmantoo.githubuser.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.reminder.DailyReceiver;

public class SettingActivity extends AppCompatActivity {
    public static final String SHARED_PREF_NAME = "sharedprefsetting";
    public static final String KEY_DAILY = "key_daily";
    Switch switchDaily;
    Button btnChangeLanguage;
    DailyReceiver dailyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.setting));
        }

        switchDaily = findViewById(R.id.switch_daily);
        btnChangeLanguage = findViewById(R.id.btn_change_languange);
        dailyReceiver = new DailyReceiver();

        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_DAILY, null) != null) {
            switchDaily.setChecked(true);
        } else {
            switchDaily.setChecked(false);
        }

        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    dailyReceiver.setRepeatingAlarm(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_DAILY, "Reminder Daily");
                    editor.apply();
                } else {
                    dailyReceiver.cancelAlarm(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_DAILY);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
