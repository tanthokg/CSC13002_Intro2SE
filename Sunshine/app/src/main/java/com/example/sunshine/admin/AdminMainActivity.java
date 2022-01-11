package com.example.sunshine.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sunshine.LoginActivity;
import com.example.sunshine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        auth = FirebaseAuth.getInstance();

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.botNavView_Admin);

        nav.setOnItemSelectedListener(item -> {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.permission:
                  fragment = new PermissionFragment(this);
                    break;
                case R.id.report:
                    fragment = new ReportFragment();
                    break;
                case R.id.settings_admin:
                    fragment = new AdminSettingsFragment(this);
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

        nav.setSelectedItemId(R.id.permission);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void changFragmentSettings(int value){
        if(value == 1){
            Fragment adminSettingsGeneralFragment = new AdminSettingsGeneralFragment(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.adminMain, adminSettingsGeneralFragment)
                    .addToBackStack("SETTINGS").commit();
        }
        else {
            Fragment adminSettingsNotificationFragment = new AdminSettingsNotificationFragment(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.adminMain, adminSettingsNotificationFragment)
                    .addToBackStack("SETTINGS").commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
