package com.example.sunshine.user;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.database.Permission;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private boolean typeUser;


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
        getTypeUser();
        getPermission();

        permissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Timestamp timestamp = new Timestamp(new Date());
                permission = new Permission();
                //      permission.permissionId = currentUserId;
                permission.setPermissionTime(timestamp);
                sendPermission(permission);
               // getPermission();
                permissionBtn.setVisibility(View.INVISIBLE);
               // permissionBtn.setClickable(false);
            }
        });

    }

    private void getPermission() {
        String currentUserId = auth.getCurrentUser().getUid();
        DocumentReference docRef = database.collection("Permission").document(currentUserId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        permissionBtn.setVisibility(View.INVISIBLE);

                        Toast.makeText(getBaseContext(), "You has sent request for reviewer permission before", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });}
    private void getTypeUser() {
        String currentUserId = auth.getCurrentUser().getUid();
        database.collection("User").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        User user = task.getResult().toObject(User.class);
                        if (user != null) {
                            typeUser = user.isType();
                            if (typeUser) {
                                permissionBtn.setVisibility(View.INVISIBLE);
                                Toast.makeText(getBaseContext(), "You are reviewer", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
    private void sendPermission(Permission permission) {
        //  database.collection("permission").document(currentUserId).add(permission);

        database.collection("Permission").document(currentUserId).set(permission);
        Toast.makeText(getBaseContext(), "Permission Sent Successfully.", Toast.LENGTH_SHORT).show();
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
