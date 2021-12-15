package com.example.sunshine.user;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;

public class create_post extends AppCompatActivity {
    private static final String[] status = new String[] {"Completed", "On Going", "Drop"};
    ArrayAdapter<String> adapter;
    Spinner status_options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create_post_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, status);
        status_options = (Spinner) findViewById(R.id.status_options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_options.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
