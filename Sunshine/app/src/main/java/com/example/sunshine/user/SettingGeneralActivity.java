package com.example.sunshine.user;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.database.Permission;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class SettingGeneralActivity extends AppCompatActivity {


    Switch darkModeBtn,hideVoteBtn,turnOffNotiBtn;
    private MaterialButton permissionBtn;
    Permission permission;
    FirebaseFirestore database;
    String currentUserId;
    FirebaseAuth auth;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_general);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

        darkModeBtn = (Switch) findViewById(R.id.darkModeSwitch);
        hideVoteBtn = (Switch) findViewById(R.id.hideVoteSwitch);
        turnOffNotiBtn = (Switch) findViewById(R.id.turnOffNotiSwitch);

        permissionBtn = (MaterialButton) findViewById(R.id.requestPermission);

        permissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Timestamp timestamp = new Timestamp(new Date());
                permission = new Permission();
                //      permission.permissionId = currentUserId;
                permission.setPermissionTime(timestamp);
                sendPermission(permission);
                permissionBtn.setClickable(false);
            }
        requestPermissionBtn = (Button) findViewById(R.id.requestPermission);

        requestPermissionBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

            }



        });

    }

    private void sendPermission(Permission permission) {
        //  database.collection("permission").document(currentUserId).add(permission);


        database.collection("Permission").document(currentUserId).set(permission);
        Toast.makeText(getBaseContext(), "Permission Sent Successfully.", Toast.LENGTH_SHORT).show();
    }
}



}

