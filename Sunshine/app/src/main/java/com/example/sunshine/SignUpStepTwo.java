package com.example.sunshine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignUpStepTwo extends Fragment implements FragmentCallbacks {
    private EditText question1, answer1, question2, answer2, question3, answer3;
    private Button signUpBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_two, container, false);
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
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void fromMainToFragment() {

    }
}
