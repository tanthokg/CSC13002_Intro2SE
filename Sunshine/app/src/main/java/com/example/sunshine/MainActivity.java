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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView forgetPassword, signUp;
    EditText userName, password;
    Button logIn;
    String adminName = "admin", adminPassword = "sunshine123";

    String forgetPasswordText = "Forgot password";
    SpannableString forgetPasswordSpan;
    ClickableSpan clickableForgetPassword;

    String signUpText = "Not a member ? SIGN UP";
    SpannableString signUpSpan;
    ClickableSpan clickableSignUp;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        logIn = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String pass = password.getText().toString();
                if(username.length() != 0 && pass.length() != 0)
                {
                    auth.signInWithEmailAndPassword(username + "@gmail.com", pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (username.equals(adminName))
                                            adminLogIn();
                                        else
                                            userLogIn();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                            });

                }
                else if (username.length() == 0)
                    Toast.makeText(MainActivity.this, "Please write your username", Toast.LENGTH_SHORT).show();
                else if (pass.length() == 0)
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals("admin@gmail.com"))
                adminLogIn();
            else
                userLogIn();
        }
    }

    public void signUp()
    {
        Intent intent = new Intent(this, SignUpActivity.class );
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