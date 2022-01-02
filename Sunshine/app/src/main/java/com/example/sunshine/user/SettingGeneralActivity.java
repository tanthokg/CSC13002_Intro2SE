package com.example.sunshine.user;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.database.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.Date;

public class SettingGeneralActivity extends AppCompatActivity {


    Switch darkModeBtn,hideVoteBtn,turnOffNotiBtn;
    Button requestPermissionBtn;
    Request request;
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
        requestPermissionBtn = (Button) findViewById(R.id.requestPermission);

        requestPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Timestamp timestamp = new Timestamp(new Date());
                request = new Request();
                //      request.requestId = currentUserId;
                request.setRequestTime(timestamp);
                sendRequest(request);
                requestPermissionBtn.setClickable(false);
            }
        });

    }

    private void sendRequest(Request request) {
        //  database.collection("Request").document(currentUserId).add(request);

        database.collection("Request").document(currentUserId).set(request);
        Toast.makeText(getBaseContext(), "Request Sent Successfully.", Toast.LENGTH_SHORT).show();
    }
}

