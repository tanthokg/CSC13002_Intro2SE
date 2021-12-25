package com.example.sunshine.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class create_post extends AppCompatActivity {
    private static final String[] status = new String[] {"Completed", "On Going", "Drop"};
    ArrayAdapter<String> adapter;

    Post post;
    FirebaseFirestore database;

    EditText titlebox;
    EditText authorbox;
    Spinner status_options;
    EditText descriptionbox;
    Button create_post;
    Button cancel_post;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create_post_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseFirestore.getInstance();

        titlebox = (EditText) findViewById(R.id.titlebox);
        authorbox = (EditText) findViewById(R.id.authorbox);
        status_options = (Spinner) findViewById(R.id.status_options);
        descriptionbox = (EditText) findViewById(R.id.descriptionbox);
        create_post = (Button) findViewById(R.id.create_post);
        cancel_post = (Button) findViewById(R.id.cancel_post);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, status);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_options.setAdapter(adapter);

        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(new Date());
                post = new Post();
                post.setAuthor(authorbox.getText().toString());
                post.setBookName(titlebox.getText().toString());
                post.setStatus(status_options.getSelectedItem().toString());
                post.setContent(descriptionbox.getText().toString());
                post.setPostTime(timestamp);
                post.setCommentCount(0);
                post.setDownvote(0);
                post.setUpvote(0);

                createPost(post);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void createPost(Post post) {
        database.collection("Post").add(post);
        Toast.makeText(getBaseContext(), "Successfully Post.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, UserMainActivity.class));
    }

}
