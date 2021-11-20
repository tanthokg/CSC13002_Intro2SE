package com.example.sunshine.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sunshine.R;
import com.example.sunshine.user.fragment_home;
import com.example.sunshine.user.fragment_library;
import com.example.sunshine.user.fragment_settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Main extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.botNavView_Admin);

        nav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home_admin:
                    fragment = new fragment_home();
                    break;
                case R.id.settings_admin:
                    fragment = new fragment_settings();
                    break;
            }

            // Use addToBackStack to return the previous fragment when the Back button is pressed
            // Checking null was just a precaution
            if (fragment != null)
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.adminMain, fragment)
                        .commit();
            return true;
        });

        nav.setSelectedItemId(R.id.home_admin);
    }
}
