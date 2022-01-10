package com.example.sunshine.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunshine.LoginActivity;
import com.example.sunshine.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {
    Button btnLogOut, btnReadLater, btnGeneral, btnFeedback, btnFAQ;

    private FirebaseAuth auth;
    private Context context;

    public SettingsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.user_fragment_settings, container, false);
    }

    private void logIn() {
        Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // delete this
        Button edit_profile = view.findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnReadLater = (Button) view.findViewById(R.id.read_later);
        btnReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserMainActivity)context).fromFragmentToMain("SETTING", "READ-LATER", auth.getCurrentUser().getUid());
            }
        });
        btnGeneral = (Button) view.findViewById(R.id.general);
        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingGeneralActivity.class));
            }
        });

        btnLogOut = (Button) view.findViewById(R.id.logOutUser);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                logIn();
            }
        });

        btnFeedback = (Button) view.findViewById(R.id.give_feedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingGiveFeedbackActivity.class));
            }
        });

        btnFAQ = (Button) view.findViewById(R.id.faq);
        btnFAQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingFAQActivity.class));
            }
        });
    }
}
