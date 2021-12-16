package com.example.sunshine.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.sunshine.MainActivity;
import com.example.sunshine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_Main extends AppCompatActivity {
    FragmentManager fragmentManager;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        auth = FirebaseAuth.getInstance();

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new fragment_home();
                    break;
                case R.id.categories:
                    fragment = new fragment_categories();
                    break;
                case R.id.notifications:
                    fragment = new fragment_notifications();
                    break;
                case R.id.settings:
                    fragment = new fragment_settings();
                    break;
            }

            // Use addToBackStack to return the previous fragment when the Back button is pressed
            // Checking null was just a precaution
            if (fragment != null)
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.userMain, fragment)
                        .commit();
            return true;
        });

        nav.setSelectedItemId(R.id.home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
