package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText userName;
    EditText password;
    EditText confirmPassword;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!confirmPassword.getText().toString().equals(password.getText().toString()))
                    Toast.makeText(SignUp.this, "Incorrect confirmPassword", Toast.LENGTH_SHORT).show();
                else
                    signIn();
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

    // check password and username's validation
    // check if confirm password and password is the same

    // ask for security question
    public void signIn()
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("username", userName.getText().toString());
        intent.putExtra("password", password.getText().toString());

        startActivity(intent);
    }

}