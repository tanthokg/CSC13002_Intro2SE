package com.example.sunshine.user;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SettingEditProfileActivity extends AppCompatActivity {

    private static final String[] gender = new String[] {"Male", "Female"};
    ArrayAdapter<String> adapter;
    FirebaseFirestore database;
    FirebaseAuth auth;
    ImageView imgAvt;
    //TextView usernameTv,fullNameTv,birthdayTv,genderTv;
    EditText usernameET,fullNameET,birthdayET;
    //TextView birthdayTv;
   Spinner gender_options;
    Context context;
    String _username, _fullName, _birthday;
    MaterialButton confirmBtn, cancelBtn;

    private boolean _gender;
    final Calendar myCalendar= Calendar.getInstance();


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
        cancelBtn = (MaterialButton) findViewById(R.id.cancelEditProfile);

        gender_options = (Spinner) findViewById(R.id.gender_option);
       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gender);

//        gender_options.setAdapter(adapter);
        birthdayET = (EditText) findViewById(R.id.birthdayBox);


        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

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
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        birthdayET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SettingEditProfileActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      confirmBtn.setOnClickListener(new View.OnClickListener() {

          @Override

          public void onClick(View v) {
              _username = usernameET.getText().toString();
              _fullName = fullNameET.getText().toString();
              _birthday = birthdayET.getText().toString();
             String selectedGender = gender_options.getSelectedItem().toString();
            if(selectedGender.equals("Male")) {
                _gender = true; }
              else if (selectedGender.equals("Female"))
              {
                  _gender = false;
              }

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
              docRef
                      .update("birthday", _birthday)
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
                      .update("gender", _gender)
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

      cancelBtn.setOnClickListener(new View.OnClickListener() {

          @Override

          public void onClick(View v) {

              setCancelBtn();

          }
      });
    }

    private void setCancelBtn()
    {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this, R.style.AlertDialog);
        confirmDialog.setMessage("Are you sure to cancel edit profile?");
        confirmDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SettingEditProfileActivity.this, "Edit Profile has been cancel", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        confirmDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        confirmDialog.create();
        confirmDialog.show();
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        birthdayET.setText(dateFormat.format(myCalendar.getTime()));
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

