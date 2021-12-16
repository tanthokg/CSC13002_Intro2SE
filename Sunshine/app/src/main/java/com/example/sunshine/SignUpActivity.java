package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class SignUpActivity extends AppCompatActivity implements MainCallbacks {
    private SignUpStepOne signUpStepOne;
    private SignUpStepTwo signUpStepTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpStepOne = new SignUpStepOne(this);
        signUpStepTwo = new SignUpStepTwo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.signUpFragmentHolder, signUpStepOne).commit();

        /*firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpStepOne();
            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void fromFragmentToMain(String sender, String request) {
        if (sender.equals("SIGN-UP"))
            if (request.equals("STEP-TWO"))
                getSupportFragmentManager().beginTransaction().replace(R.id.signUpFragmentHolder, signUpStepTwo)
                        .addToBackStack("STEP-TWO").commit();

    }
}