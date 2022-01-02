package com.example.sunshine.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;

import java.util.Objects;

public class SettingGeneralActivity extends AppCompatActivity {


    Switch darkModeBtn,hideVoteBtn,turnOffNotiBtn;
    Button requestPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_fragment_setting_general);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle("Settings");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        darkModeBtn = (Switch) findViewById(R.id.darkModeSwitch);
        hideVoteBtn = (Switch) findViewById(R.id.hideVoteSwitch);
        turnOffNotiBtn = (Switch) findViewById(R.id.turnOffNotiSwitch);
        requestPermission = (Button) findViewById(R.id.requestPermission);
    }
}
