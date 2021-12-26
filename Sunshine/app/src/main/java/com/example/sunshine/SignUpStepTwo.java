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
                if (!validateFirstQuestion() | !validateSecondQuestion() | !validateThirdQuestion() |
                    !validateFirstAnswer() | !validateSecondAnswer() | ! validateThirdAnswer()) {
                    return;
                }

                ArrayList<String> questions = new ArrayList<>();
                questions.add(question1.getText().toString().trim());
                questions.add(question2.getText().toString().trim());
                questions.add(question3.getText().toString().trim());
                authUser.setQuestions(questions);

                ArrayList<String> answers = new ArrayList<>();
                answers.add(answer1.getText().toString().trim());
                answers.add(answer2.getText().toString().trim());
                answers.add(answer3.getText().toString().trim());
                authUser.setAnswers(answers);

                signUp(authUser);
            }
        });
        return view;
    }

    private boolean validateFirstQuestion() {
        String input = question1.getText().toString().trim();
        if (input.isEmpty()) {
            question1.setError("This field must not be empty");
            return false;
        }
        else {
            question1.setError(null);
            return true;
        }
    }

    private boolean validateSecondQuestion() {
        String input = question2.getText().toString().trim();
        if (input.isEmpty()) {
            question2.setError("This field must not be empty");
            return false;
        }
        else {
            question2.setError(null);
            return true;
        }
    }

    private boolean validateThirdQuestion() {
        String input = question3.getText().toString().trim();
        if (input.isEmpty()) {
            question3.setError("This field must not be empty");
            return false;
        } else {
            question3.setError(null);
            return true;
        }
    }

    private boolean validateFirstAnswer() {
        String input = answer1.getText().toString().trim();
        String question = question1.getText().toString().trim();
        if (input.isEmpty()) {
            answer1.setError("This field must not be empty");
            return false;
        }
        else if (input.equals(question)) {
            answer1.setError("Do not repeat the question");
            return false;
        }
        else {
            answer1.setError(null);
            return true;
        }
    }

    private boolean validateSecondAnswer() {
        String input = answer2.getText().toString().trim();
        String question = question2.getText().toString().trim();
        if (input.isEmpty()) {
            answer2.setError("This field must not be empty");
            return false;
        }
        else if (input.equals(question)) {
            answer2.setError("Do not repeat the question");
            return false;
        }
        else {
            answer2.setError(null);
            return true;
        }
    }

    private boolean validateThirdAnswer() {
        String input = answer3.getText().toString().trim();
        String question = question3.getText().toString().trim();
        if (input.isEmpty()) {
            answer3.setError("This field must not be empty");
            return false;
        }
        else if (input.equals(question)) {
            answer3.setError("Do not repeat the question");
            return false;
        }
        else {
            answer3.setError(null);
            return true;
        }
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
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            database.collection("Authentication")
                                    .document(userId)
                                    .set(authUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(context, "Successfully add data.", Toast.LENGTH_SHORT).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            User user = new User(authUser.getUsername());
                            database.collection("User")
                                    .document(userId)
                                    .set(user);
                            Toast.makeText(context, "Successfully sign up.", Toast.LENGTH_SHORT).show();
                            //firebaseAuth.signOut();
                            signIn();
                                   /* .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
                                    });*/
                            //Toast.makeText(context, "Successfully sign up.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
}
