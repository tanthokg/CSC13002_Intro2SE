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

public class ForgotPasswordStepTwo extends Fragment implements FragmentCallbacks {
    private EditText newPassword, confirmNewPassword;
    private Button confirmBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_step_two, container, false);
        newPassword = view.findViewById(R.id.password);
        confirmNewPassword = view.findViewById(R.id.confirmPassword);
        confirmBtn = view.findViewById(R.id.confirmNewPasswordBtn);
        return view;
    }

    @Override
    public void fromMainToFragment() {

    }
}
