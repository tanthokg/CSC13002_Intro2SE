package com.example.sunshine.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.sunshine.LoginActivity;
import com.example.sunshine.MainCallbacks;
import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserMainActivity extends AppCompatActivity implements MainCallbacks {
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
            int id = item.getItemId();

            if (R.id.homepage == id)
                fragment = new PostFragment(this);
            else if (R.id.categories == id)
                fragment = new CategoriesFragment();
            else if (R.id.notifications == id)
                fragment = new NotificationsFragment();
            else if (R.id.settings == id)
                fragment = new SettingsFragment();

            if (fragment != null)
                fragmentManager.beginTransaction().replace(R.id.mainFragmentHolder, fragment).commit();
            return true;
        });

        nav.setSelectedItemId(R.id.homepage);
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

    @Override
    public void fromFragmentToMain(String sender, String request, Object value) {
        if (sender.equals("POST")) {
            if (request.equals("SHOW-COMMENT")) {
                Post post = (Post) value;
                CommentFragment fragment = new CommentFragment(this, post);
                getSupportFragmentManager().beginTransaction().addToBackStack("COMMENT")
                        .replace(R.id.mainFragmentHolder, fragment).commit();
            }
        }
    }
}
