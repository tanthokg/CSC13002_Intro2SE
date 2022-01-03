package com.example.sunshine.user;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.example.sunshine.R;

public class CreateBookActivity extends Activity {

    ImageView imgBook;
    EditText edtBookTitle, edtAuthor, edtPublisedYear, edtCategories;
    Button btnCreateBook;
    ProgressBar progressBarCreateBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_create_book);

        imgBook = (ImageView) findViewById(R.id.imgBook);
        edtBookTitle = (EditText) findViewById(R.id.edtBookTitle);
        edtAuthor = (EditText) findViewById(R.id.edtAuthor);
        edtPublisedYear = (EditText) findViewById(R.id.edtPublishedYear);
        edtCategories = (EditText) findViewById(R.id.edtCategories);
        btnCreateBook = (Button) findViewById(R.id.btnCreateBook);
        progressBarCreateBook = (ProgressBar) findViewById(R.id.progressBarCreateBook);
        progressBarCreateBook.setVisibility(View.INVISIBLE);

        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCreateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarCreateBook.setVisibility(View.VISIBLE);
            }
        });
    }
}
