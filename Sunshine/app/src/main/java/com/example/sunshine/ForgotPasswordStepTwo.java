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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ForgotPasswordStepTwo extends Fragment implements FragmentCallbacks {
    private EditText newPassword, confirmNewPassword;
    private Button confirmBtn;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseFirestore database;
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
        database = FirebaseFirestore.getInstance();

        newPassword = view.findViewById(R.id.newPassword);
        confirmNewPassword = view.findViewById(R.id.confirmPassword);
        confirmBtn = view.findViewById(R.id.confirmNewPasswordBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = newPassword.getText().toString();
                String newConfirmPass = confirmNewPassword.getText().toString();
               if (! newConfirmPass.equals(newPass))
                    Toast.makeText(context, "Re-typed password is not match!", Toast.LENGTH_SHORT).show();
                else
                { updatePassword(newPass); }
            }
        });
        return view;
    }

    @Override
    public void fromMainToFragment(Object request) {
        user = new Authentication();
        user = (Authentication) request;
    }

    private void updatePassword(String newPassword) {
        auth.signInWithEmailAndPassword(user.getUsername() + "@gmail.com", user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setPassword(newPassword);
                            user.setPassword(Authentication.hashPass(user.getPassword()));
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null)
                                currentUser.updatePassword(user.getPassword())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(@NonNull Void unused) {
                                                database.collection("Authentication").whereEqualTo("username", user.getUsername())
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                                    database.collection("Authentication")
                                                                            .document(documentSnapshot.getId())
                                                                            .update("password", user.getPassword())
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(@NonNull Void unused) {
                                                                                    Toast.makeText(context, "Successfully change password.", Toast.LENGTH_SHORT).show();
                                                                                    auth.signOut();
                                                                                    logIn();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                    auth.signOut();
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                auth.signOut();
                                            }
                                        });
                            else {
                                Toast.makeText(context, "Can't reset password.", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                            }
                        }
                        else
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void logIn(){
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
}
