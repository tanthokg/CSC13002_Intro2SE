package com.example.sunshine.user;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.database.Permission;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Date;


public class SettingEditProfileActivity extends AppCompatActivity {

    ImageView imgAvt;
    TextView usernameTv,fullNameTv,birthdayTv;
    Spinner gender;
//    User user;
//    FirebaseFirestore database;
//    String currentUserId;
//    FirebaseAuth auth;
//    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        database = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        currentUserId = auth.getCurrentUser().getUid();

        //imgAvt = (imgAvt) findViewById(R.id.imgAvatar);
        usernameTv = (TextView)  findViewById(R.id.usernameBox);
        fullNameTv = (TextView) findViewById(R.id.fullnameBox);
        birthdayTv = (TextView) findViewById(R.id.birthdayBox);
        gender = (Spinner) findViewById(R.id.gender_option);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

}

