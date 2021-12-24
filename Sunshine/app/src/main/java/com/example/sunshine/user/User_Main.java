package com.example.sunshine.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.sunshine.MainActivity;
import com.example.sunshine.MainCallbacks;
import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_Main extends AppCompatActivity implements MainCallbacks {
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
                    fragment = new/* fragment_home(this)*/PostFragment(this);
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
                        .replace(R.id.mainFragmentHolder, fragment)
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

    @Override
    public void fromFragmentToMain(String sender, String request, Object value) {
        if (sender.equals("POST")) {
            if (request.equals("SHOW-COMMENT")) {
                Post post = (Post) value;
                CommentFragment fragment = new CommentFragment(post);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentHolder, fragment)
                        .addToBackStack("COMMENT").commit();
            }
        }
    }
}
