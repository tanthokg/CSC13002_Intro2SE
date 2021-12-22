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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SignUpStepTwo extends Fragment implements FragmentCallbacks {
    private EditText question1, answer1, question2, answer2, question3, answer3;
    private Button signUpBtn;
    Context context;

    Authentication authUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;

    public SignUpStepTwo(Context context) {
        this.context = context;
        this.authUser = new Authentication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_two, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        question1 = view.findViewById(R.id.question1);
        question2 = view.findViewById(R.id.question2);
        question3 = view.findViewById(R.id.question3);
        answer1 = view.findViewById(R.id.answer1);
        answer2 = view.findViewById(R.id.answer2);
        answer3 = view.findViewById(R.id.answer3);
        signUpBtn = view.findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> questions = new ArrayList<>();
                questions.add(question1.getText().toString());
                questions.add(question2.getText().toString());
                questions.add(question3.getText().toString());
                authUser.setQuestions(questions);

                ArrayList<String> answers = new ArrayList<>();
                answers.add(answer1.getText().toString());
                answers.add(answer2.getText().toString());
                answers.add(answer3.getText().toString());
                authUser.setAnswers(answers);

                signUp(authUser);
            }
        });
        return view;
    }

    @Override
    public void fromMainToFragment(Object request) {
        authUser = (Authentication) request;
    }

    private void signUp(Authentication authUser) {
        firebaseAuth.createUserWithEmailAndPassword(authUser.getUsername() + "@gmail.com", Authentication.hashPass(authUser.getPassword()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authUser.setPassword(Authentication.hashPass(authUser.getPassword()));
                            database.collection("Authentication")
                                    .add(authUser)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                User user = new User(authUser.getUsername());
                                                database.collection("User")
                                                        .add(user);
                                                Toast.makeText(context, "Successfully sign up.", Toast.LENGTH_SHORT).show();
                                                firebaseAuth.signOut();
                                                signIn();
                                            }
                                            else
                                                firebaseAuth.signOut();
                                        }
                                    });
                            //Toast.makeText(context, "Successfully sign up.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
