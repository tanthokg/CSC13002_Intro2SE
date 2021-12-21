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
        signUpStepTwo = new SignUpStepTwo(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.signUpFragmentHolder, signUpStepOne).commit();
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
    public void fromFragmentToMain(String sender, String request, Object value) {
        if (sender.equals("SIGN-UP")) {
            if (request.equals("STEP-TWO")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.signUpFragmentHolder, signUpStepTwo)
                        .addToBackStack("STEP-TWO").commit();
                signUpStepTwo.fromMainToFragment(value);
            }
        }
    }
}