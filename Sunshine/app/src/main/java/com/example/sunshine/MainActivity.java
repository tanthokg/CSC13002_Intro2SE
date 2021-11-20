package com.example.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunshine.admin.Admin_Main;
import com.example.sunshine.user.User_Main;

public class MainActivity extends AppCompatActivity {
    TextView forgetPassword;
    TextView signUp;
    EditText userName;
    EditText password;
    Button logIn;
    String adminName = "admin";
    String adminPassword = "sunshine123";
    String user = " ";
    String pass = " ";

    String forgetPasswordText = "Forgot password";
    SpannableString forgetPasswordSpan;
    ClickableSpan clickableForgetPassword;

    String signUpText = "Not a member ? SIGN UP";
    SpannableString signUpSpan;
    ClickableSpan clickableSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        pass = intent.getStringExtra("password");

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().length() != 0 && password.getText().length() != 0)
                {
                    if (userName.getText().toString().equals(adminName) && password.getText().toString().equals(adminPassword)) {
                        Toast.makeText(MainActivity.this, "Success Log In", Toast.LENGTH_SHORT).show();
                        adminLogIn();
                    } else if(userName.getText().toString().equals(user) && password.getText().toString().equals(pass)) {
                        Toast.makeText(MainActivity.this, "Success Log In", Toast.LENGTH_SHORT).show();
                        userLogIn();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
                else if (userName.getText().length() == 0)
                    Toast.makeText(MainActivity.this, "Please write your username", Toast.LENGTH_SHORT).show();
                else if (password.getText().length() == 0)
                    Toast.makeText(MainActivity.this, "Please write your password", Toast.LENGTH_SHORT).show();
            }
        });


        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        signUp = (TextView) findViewById(R.id.signUp);

        forgetPasswordSpan = new SpannableString(forgetPasswordText);
        clickableForgetPassword = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "Forget password", Toast.LENGTH_SHORT).show();
            }

        };

        forgetPasswordSpan.setSpan(clickableForgetPassword,0,
                forgetPasswordText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forgetPassword.setText(forgetPasswordSpan);
        forgetPassword.setMovementMethod(LinkMovementMethod.getInstance());


        signUpSpan = new SpannableString(signUpText);
        clickableSignUp = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "Sign Up", Toast.LENGTH_SHORT).show();
                signUp();
            }
        };

        signUpSpan.setSpan(clickableSignUp,15,
                signUpText.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        signUp.setText(signUpSpan);
        signUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void signUp()
    {
        Intent intent = new Intent(this, SignUp.class );
        startActivity(intent);
    }

    public void userLogIn()
    {
        Intent intent = new Intent(this, User_Main.class );
        startActivity(intent);
    }

    public void adminLogIn()
    {
        Intent intent = new Intent(this, Admin_Main.class );
        startActivity(intent);
    }
}