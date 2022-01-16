package com.example.sunshine.user;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class SettingEditProfileActivity extends AppCompatActivity {

    private static final String[] gender = new String[] {"Male", "Female"};
    ArrayAdapter<String> adapter;
    FirebaseFirestore database;
    FirebaseAuth auth;
    ImageView imgAvt;
    //TextView usernameTv,fullNameTv,birthdayTv,genderTv;
    EditText usernameET,fullNameET,birthdayET;
   Spinner gender_options;
    Context context;
    String _username, _fullName, _birthday;
    MaterialButton confirmBtn;

    private boolean _gender;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgAvt = (ImageView) findViewById(R.id.imgAvatar);
      //  usernameTv = (TextView)  findViewById(R.id.usernameBox);
        usernameET = (EditText) findViewById(R.id.usernameBox);
        fullNameET = (EditText) findViewById(R.id.fullnameBox);
      //  genderET = (TextView)findViewById(R.id.genderBox);
        confirmBtn =(MaterialButton) findViewById(R.id.confirmEditProfile);

        gender_options = (Spinner) findViewById(R.id.gender_option);
     adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gender);
//      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        gender_options.setAdapter(adapter);
        birthdayET = (EditText) findViewById(R.id.birthdayBox);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //currentUserId = auth.getCurrentUser().getUid();
        //getUsername();
        // usernameTv.setText(username);
        String userId = auth.getCurrentUser().getUid();
//        database.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task <DocumentSnapshot> task) {
//                if (task.isSuccessful())
//                    if (task.getResult() != null) {
//                        User user;
//                        user = task.getResult().toObject(User.class);
//                        username = user.getUsername();
//                        fullname =user.getFullname();
//                    }
//            }
//        });
        // Gets user document from Firestore as reference
        DocumentReference docRef = database.collection("User").document(userId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        _username = (String) document.getString("username");
                        _fullName = (String) document.getString("fullname");
                        _birthday = (String) document.getString("birthday");
                        _gender =  (Boolean) document.getBoolean("gender");
//                        usernameTv.setText(_username);
                        usernameET.setText(_username);
                        fullNameET.setText(_fullName);
                        birthdayET.setText(_birthday);
                        if (_gender)
                        {
                            gender_options.setAdapter(adapter);
                            gender_options.setSelection(0);
                        }
                        else
                        {
                            gender_options.setAdapter(adapter);
                            gender_options.setSelection(1);
                        }


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

      confirmBtn.setOnClickListener(new View.OnClickListener() {

          @Override

          public void onClick(View v) {
              _username = usernameET.getText().toString();
              _fullName = fullNameET.getText().toString();

              DocumentReference docRef = database.collection("User").document(userId);
              docRef
                      .update("username", _username)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Log.d(TAG, "Permission successfully!");
                          }
                      })
                      .addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Log.w(TAG, "Error updating document", e);
                          }
                      });

              docRef
                      .update("fullname", _fullName)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Log.d(TAG, "Permission successfully!");
                          }
                      })
                      .addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Log.w(TAG, "Error updating document", e);
                          }
                      });
          }
      });





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
    private void getUsername() {

    }


}

