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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPasswordStepTwo extends Fragment implements FragmentCallbacks {
    private EditText newPassword, confirmNewPassword;
    private Button confirmBtn;
    private Context context;
    private FirebaseAuth auth;
    private Authentication user;

    public ForgotPasswordStepTwo(Context context) {
        this.context = context;
        this.user = new Authentication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_step_two, container, false);

        auth = FirebaseAuth.getInstance();

        newPassword = view.findViewById(R.id.newPassword);
        confirmNewPassword = view.findViewById(R.id.confirmPassword);
        confirmBtn = view.findViewById(R.id.confirmNewPasswordBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: update password
                String newPass = newPassword.getText().toString();
                updatePassword(newPass);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void fromMainToFragment(Object request) {
        user = (Authentication) request;
    }

    private void updatePassword(String newPassword) {
        auth.signInWithEmailAndPassword(user.getUsername() + "@gmail.com", user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setPassword(newPassword);
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null)
                                currentUser.updatePassword(user.getPassword())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Successfully change password.", Toast.LENGTH_SHORT).show();
                                                //TODO: update password in dataabse
                                                auth.signOut();
                                            }
                                        });
                            else
                                Toast.makeText(context, "Can't reset password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        auth.signOut();
                    }
                });
    }
}
