package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class ForgotPasswordActivity extends AppCompatActivity implements MainCallbacks {
    private ForgotPasswordStepOne forgotPasswordStepOne;
    private ForgotPasswordStepTwo forgotPasswordStepTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordStepOne = new ForgotPasswordStepOne(this);
        forgotPasswordStepTwo = new ForgotPasswordStepTwo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.forgotPasswordFragmentHolder, forgotPasswordStepOne)
                .commit();
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
        if (sender.equals("FORGOT"))
            if (request.equals("STEP-TWO"))
                getSupportFragmentManager().beginTransaction().replace(R.id.forgotPasswordFragmentHolder, forgotPasswordStepTwo)
                .addToBackStack("STEP-TWO").commit();
    }
}