package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sunshine.database.Authentication;

public class ForgotPasswordActivity extends AppCompatActivity implements MainCallbacks {
    private ForgotPasswordStepOne forgotPasswordStepOne;
    private ForgotPasswordStepTwo forgotPasswordStepTwo;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        forgotPasswordStepOne = new ForgotPasswordStepOne(this, username);
        forgotPasswordStepTwo = new ForgotPasswordStepTwo(this);
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
    public void fromFragmentToMain(String sender, String request, Object value) {
        if (sender.equals("FORGOT"))
            if (request.equals("STEP-TWO"))
                getSupportFragmentManager().beginTransaction().replace(R.id.forgotPasswordFragmentHolder, forgotPasswordStepTwo)
                .addToBackStack("STEP-TWO").commit();
            forgotPasswordStepTwo.fromMainToFragment((Authentication)value);
    }
}