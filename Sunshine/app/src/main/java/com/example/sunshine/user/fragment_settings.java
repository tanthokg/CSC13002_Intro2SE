package com.example.sunshine.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunshine.MainActivity;
import com.example.sunshine.R;

public class fragment_settings extends Fragment {
    Button logOutAdmin;

    public fragment_settings()
    { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment_settings, container, false);
    }

    private void logIn() {
        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logOutAdmin = (Button) view.findViewById(R.id.logOutUser);
        logOutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
    }
}
