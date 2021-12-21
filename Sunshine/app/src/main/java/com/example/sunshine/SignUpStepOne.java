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
        if (!confirmPassword.getText().toString().equals(password.getText().toString()))
            Toast.makeText(context, "Re-typed password is not match!", Toast.LENGTH_SHORT).show();
        else {
            String username = userName.getText().toString();
            String pass = password.getText().toString();
            Authentication authUser = new Authentication(username, pass);
            ((SignUpActivity)context).fromFragmentToMain("SIGN-UP", "STEP-TWO", authUser);
        }
    }

    @Override
    public void fromMainToFragment(Object request) {

    }
}
