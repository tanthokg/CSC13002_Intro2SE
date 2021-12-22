package com.example.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunshine.database.Authentication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordStepOne extends Fragment implements FragmentCallbacks {
    private EditText answer1, answer2, answer3;
    private TextView question1, question2, question3;
    private Button submitBtn;
    private Context context;
    private String username;

    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private List<String> questions;
    private List<String> answers;
    private Authentication user;

    public ForgotPasswordStepOne(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_step_one, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        question1 = view.findViewById(R.id.question1Forgot);
        question2 = view.findViewById(R.id.question2Forgot);
        question3 = view.findViewById(R.id.question3Forgot);
        answer1 = view.findViewById(R.id.answer1Forgot);
        answer2 = view.findViewById(R.id.answer2Forgot);
        answer3 = view.findViewById(R.id.answer3Forgot);
        submitBtn = view.findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAnswer())
                    ((ForgotPasswordActivity) context).fromFragmentToMain("FORGOT", "STEP-TWO", (Authentication) user);
                else
                    Toast.makeText(context, "Your answers are wrong, please to check again.", Toast.LENGTH_SHORT).show();
            }
        });

        initView();
        return view;
    }

    @Override
    public void fromMainToFragment(Object request) {

    }

    private void initView() {
        Toast.makeText(context, "Wait some minutes to load questions.", Toast.LENGTH_LONG).show();
        auth.signInWithEmailAndPassword(getString(R.string.gmail), getString(R.string.pass))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Query query = database.collection("Authentication")
                                    .whereEqualTo("username", username);
                            query.get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                // TODO: create a dialog to loading...
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    user = new Authentication();
                                                    user = document.toObject(Authentication.class);
                                                    questions = user.getQuestions();
                                                    answers = user.getAnswers();

                                                    question1.setText(questions.get(0));
                                                    question2.setText(questions.get(1));
                                                    question3.setText(questions.get(2));
                                                }
                                                auth.signOut();
                                            }
                                            else
                                                auth.signOut();
                                        }
                                    });
                        }
                        else
                            auth.signOut();
                    }
                });
    }

    private boolean checkAnswer() {
        if (answer1.getText().toString().equals(answers.get(0)) && answer2.getText().toString().equals(answers.get(1)) && answer3.getText().toString().equals(answers.get(2)))
            return true;
        return false;
    }
}
