package com.example.sunshine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunshine.database.Authentication;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpStepOne extends Fragment implements FragmentCallbacks {
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private Button btnNext;

    private Context context;

    public SignUpStepOne(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_one, container,false);

        userName = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        btnNext = (Button) view.findViewById(R.id.signUp);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpStepOne();
            }
        });

        return view;
    }

    private void signUpStepOne() {

        if (!validateUsername() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }

        String username = userName.getText().toString();
        String pass = password.getText().toString();
        Authentication authUser = new Authentication(username, pass);
        ((SignUpActivity)context).fromFragmentToMain("SIGN-UP", "STEP-TWO", authUser);

        /*if (!confirmPassword.getText().toString().equals(password.getText().toString()))
            Toast.makeText(context, "Re-typed password is not match!", Toast.LENGTH_SHORT).show();
        else {
            String username = userName.getText().toString();
            String pass = password.getText().toString();
            Authentication authUser = new Authentication(username, pass);
            ((SignUpActivity)context).fromFragmentToMain("SIGN-UP", "STEP-TWO", authUser);
        }*/
    }

    private boolean validateUsername()
    {
        String input = userName.getText().toString().trim();

        if (input.isEmpty()) {
            userName.setError("This field must not be empty");
            return false;
        }
        else if (Character.isDigit(input.charAt(0))) {
            userName.setError("Username must not begin with a number");
            return false;
        }
        else if (input.length() < 8) {
            userName.setError("Username must contain at least 8 characters");
            return false;
        }
        else {
            userName.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String input = confirmPassword.getText().toString().trim();

        if (input.isEmpty()) {
            confirmPassword.setError("This field must not be empty");
            return false;
        }
        else if (!input.equals(password.getText().toString().trim())) {
            confirmPassword.setError("Confirm password does not match");
            return false;
        }
        else {
            confirmPassword.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String input = password.getText().toString().trim();

        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasUppercase = uppercase.matcher(input);
        Matcher hasDigit = digit.matcher(input);
        Matcher hasSpecial = special.matcher(input);

        if (input.isEmpty()) {
            password.setError("This field must not be empty");
            return false;
        }
        else if (input.length() < 8) {
            password.setError("Password must contain at least 8 characters");
            return false;
        }
        else if (input.contains(" ")) {
            password.setError("Password must not contain whitespace");
            return false;
        }
        else if (!hasUppercase.find()) {
            password.setError("Password must contain at least 1 uppercase");
            return false;
        }
        else if (!hasDigit.find()) {
            password.setError("Password must contain at least 1 digit");
            return false;
        }
        else if (!hasSpecial.find()) {
            password.setError("Password must contain at least 1 special character");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    @Override
    public void fromMainToFragment(Object request) {

    }
}
