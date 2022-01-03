package com.example.sunshine.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class SettingGeneralActivity extends AppCompatActivity {


    Switch darkModeBtn,hideVoteBtn,turnOffNotiBtn;
    Button requestPermissionBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_general);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        darkModeBtn = (Switch) findViewById(R.id.darkModeSwitch);
        hideVoteBtn = (Switch) findViewById(R.id.hideVoteSwitch);
        turnOffNotiBtn = (Switch) findViewById(R.id.turnOffNotiSwitch);
        requestPermissionBtn = (Button) findViewById(R.id.requestPermission);

        requestPermissionBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

            }


        });

    }



}