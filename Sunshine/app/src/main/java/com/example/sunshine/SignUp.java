package com.example.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunshine.database.Authentication;
import com.example.sunshine.database.DatabaseUtil;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SignUp extends AppCompatActivity {

    EditText userName;
    EditText password;
    EditText confirmPassword;
    Button signUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!confirmPassword.getText().toString().equals(password.getText().toString()))
                    Toast.makeText(SignUp.this, "Incorrect confirmPassword", Toast.LENGTH_SHORT).show();
                else {
                    String username = userName.getText().toString();
                    String pass = password.getText().toString();
                    Authentication authUser = new Authentication(username, pass);
                    signUp(authUser);
                }
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

    // TODO: ask for security question
    public void signIn()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void signUp(Authentication authUser) {
        //if (!checkExistedUser(authUser.getUsername())) {
            firebaseAuth.createUserWithEmailAndPassword(authUser.getUsername() + "@gmail.com", authUser.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                authUser.setPassword("");
                                database.collection("Authentication")
                                        .add(authUser)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentReference documentReference) {
                                                User user = new User(authUser.getUsername());
                                                database.collection("User")
                                                        .add(user);
                                            }
                                        });
                                signIn();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        //}
        //else
            //Toast.makeText(getApplicationContext(), "User has existed", Toast.LENGTH_LONG).show();
    }

    private boolean checkExistedUser(String username) {
        Query query = database.collection("Authentication").whereEqualTo("username", username);
        if (query == null)
            return false;
        return true;
    }

}