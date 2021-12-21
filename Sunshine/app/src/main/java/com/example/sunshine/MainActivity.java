package com.example.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.example.sunshine.database.Authentication;
import com.example.sunshine.user.User_Main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    TextView forgetPassword, signUp;
    EditText loginUsername, loginPassword;
    Button logInBtn;
    String adminUsername = "admin", adminPassword = "sunshine123";

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

        logInBtn = (Button) findViewById(R.id.loginButton);
        loginUsername = (EditText) findViewById(R.id.username);
        loginPassword = (EditText) findViewById(R.id.password);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String pass = loginPassword.getText().toString();
                if(username.length() != 0 && pass.length() != 0)
                {
                    auth.signInWithEmailAndPassword(username + "@gmail.com", pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (username.equals(adminUsername))
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
                forgotPassword();
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
            else if (user.getEmail().equals(getString(R.string.gmail)))
                auth.signOut();
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
        finish();
    }

    public void adminLogIn()
    {
        Intent intent = new Intent(this, Admin_Main.class );
        startActivity(intent);
        finish();
    }

    private void forgotPassword() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.forgot_password_username, null, false);
        EditText forgotPasswordUsername = view.findViewById(R.id.username);
        Button nextBtn = view.findViewById(R.id.findUsernameBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check username is existed, right or wrong
                String username = forgotPasswordUsername.getText().toString();
                if (checkExistedUser(username)) {
                    Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Username is not existed, please check the username or sign up!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setView(view);
        dialog.create().show();

        Toast.makeText(MainActivity.this, "Forget password", Toast.LENGTH_SHORT).show();
    }

    private boolean checkExistedUser(String username) {
        final String[] user = {""};
        auth.signInWithEmailAndPassword(getString(R.string.gmail), getString(R.string.pass))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseFirestore database = FirebaseFirestore.getInstance();
                            database.collection("Authentication").whereEqualTo("username", username)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Authentication temp = document.toObject(Authentication.class);
                                                    user[0] = temp.getUsername();
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
        auth.signOut();
        if (user[0] == null)
            return false;
        return true;
    }
}