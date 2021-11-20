package com.example.sunshine.user;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.sunshine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class User_Main extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new fragment_home();
                    break;
                case R.id.library:
                    fragment = new fragment_library();
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

}
