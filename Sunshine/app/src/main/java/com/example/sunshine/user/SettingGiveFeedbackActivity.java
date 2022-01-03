package com.example.sunshine.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sunshine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import androidx.appcompat.app.AppCompatActivity;

public class SettingGiveFeedbackActivity extends AppCompatActivity {

    Button sendFeedback;
    RadioGroup scoreGroup;
    RadioButton radioButtonOne;
    RadioButton radioButtonTwo;
    RadioButton radioButtonThree;
    RadioButton radioButtonFour;
    RadioButton radioButtonFive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_give_feedback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendFeedback = (Button) findViewById(R.id.give_feedback);
        scoreGroup = (RadioGroup) findViewById(R.id.score_groupbox);

        radioButtonOne = (RadioButton) findViewById(R.id.one);
        radioButtonTwo = (RadioButton) findViewById(R.id.two);
        radioButtonThree = (RadioButton) findViewById(R.id.three);
        radioButtonFour = (RadioButton) findViewById(R.id.four);
        radioButtonFive = (RadioButton) findViewById(R.id.five);


    }





}
