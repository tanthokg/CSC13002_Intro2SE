package com.example.sunshine.user;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.LoginActivity;
import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {
    private static final String[] status = new String[] {"Completed", "On Going", "Drop"};
    ArrayAdapter<String> adapter;

    Post post;
    FirebaseFirestore database;
    FirebaseAuth auth;
    String username;

    EditText titleBox, authorBox, descriptionBox;
    Spinner status_options;
    Button createBtn, cancelBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create_post_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        getUsername();

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
                setCancelBtn();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateTitle() | !validateAuthor() | !validateDescription() ) {
                    return;
                }

                Timestamp timestamp = new Timestamp(new Date());
                post = new Post();
                post.setPostBy(username);
                post.setAuthor(authorBox.getText().toString());
                post.setBookName(titleBox.getText().toString());
                post.setStatus(status_options.getSelectedItem().toString());
                post.setContent(descriptionBox.getText().toString());
                post.setPostTime(timestamp);

                createPost(post);
            }
        });


    }
    private boolean validateTitle() {
        String input = titleBox.getText().toString().trim();
        if (input.isEmpty()) {
            titleBox.setError("Title must not be empty");
            return false;
        }
        else {
            titleBox.setError(null);
            return true;
        }
    }
    private boolean validateAuthor() {
        String input = authorBox.getText().toString().trim();
        if (input.isEmpty()) {
            authorBox.setError("Author must not be empty");
            return false;
        }
        else {
            authorBox.setError(null);
            return true;
        }
    }
    private boolean validateDescription() {
        String input = descriptionBox.getText().toString().trim();
        if (input.isEmpty()) {
            descriptionBox.setError("Description must not be empty");
            return false;
        }
        else {
            descriptionBox.setError(null);
            return true;
        }
    }

    private void setCancelBtn()
    {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this, R.style.AlertDialog);
        confirmDialog.setMessage("Are you sure to cancel create post?");
        confirmDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CreatePostActivity.this, "Create post has been cancel", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        confirmDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        confirmDialog.create();
        confirmDialog.show();
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

    private void getUsername() {
        String userId = auth.getCurrentUser().getUid();
        database.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                    if (task.getResult() != null) {
                        User user;
                        user = task.getResult().toObject(User.class);
                        username = user.getUsername();
                    }
            }
        });
    }
}
