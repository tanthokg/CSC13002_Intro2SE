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

public class CreatePostActivity extends AppCompatActivity {
    private static final String[] status = new String[] {"Completed", "On Going", "Drop"};
    ArrayAdapter<String> adapter;

    Post post;
    FirebaseFirestore database;

    EditText titleBox, authorBox, descriptionBox;
    Spinner status_options;
    Button createBtn, cancelBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create_post_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseFirestore.getInstance();

        titleBox = (EditText) findViewById(R.id.titlebox);
        authorBox = (EditText) findViewById(R.id.authorbox);
        status_options = (Spinner) findViewById(R.id.status_options);
        descriptionBox = (EditText) findViewById(R.id.descriptionbox);
        createBtn = (Button) findViewById(R.id.create_post);
        cancelBtn = (Button) findViewById(R.id.cancel_post);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, status);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_options.setAdapter(adapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(new Date());
                post = new Post();
                post.setAuthor(authorBox.getText().toString());
                post.setBookName(titleBox.getText().toString());
                post.setStatus(status_options.getSelectedItem().toString());
                post.setContent(descriptionBox.getText().toString());
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
        Toast.makeText(getBaseContext(), "Post Created Successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, UserMainActivity.class));
    }

}
