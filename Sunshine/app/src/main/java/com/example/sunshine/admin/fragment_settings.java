package com.example.sunshine.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunshine.MainActivity;
import com.example.sunshine.R;

public class fragment_settings extends Fragment {
    Button logOutUser;

    public fragment_settings()
    { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_settings =  inflater.inflate(R.layout.admin_fragment_settings, container, false);
        return fragment_settings;
    }

    private void logIn() {
        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logOutUser = (Button) view.findViewById(R.id.logOutUser);
        logOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
    }
}
