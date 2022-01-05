package com.example.sunshine.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sunshine.R;
import com.example.sunshine.database.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Arrays;
import java.util.List;

public class CreateBookActivity extends Activity {

    ImageView imgBook;
    EditText edtBookTitle, edtAuthor, edtPublisedYear, edtCategories;
    Button btnCreateBook;
    ProgressBar progressBarCreateBook;
    Uri coverBookUri;
    FirebaseFirestore firestore;
    StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_create_book);

        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        imgBook = (ImageView) findViewById(R.id.imgBook);
        edtBookTitle = (EditText) findViewById(R.id.edtBookTitle);
        edtAuthor = (EditText) findViewById(R.id.edtAuthor);
        edtPublisedYear = (EditText) findViewById(R.id.edtPublishedYear);
        edtCategories = (EditText) findViewById(R.id.edtCategories);
        btnCreateBook = (Button) findViewById(R.id.btnCreateBook);
        progressBarCreateBook = (ProgressBar) findViewById(R.id.progressBarCreateBook);
        progressBarCreateBook.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("newTitle");
            String author = extras.getString("newAuthor");
            //The key argument here must match that used in the other activity
            if (!title.isEmpty())
                edtBookTitle.setText(title);

            if (!author.isEmpty())
                edtAuthor.setText(author);
        }

        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (ContextCompat.checkSelfPermission(CreateBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreateBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    else {
                        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(CreateBookActivity.this);
                    }
                }
            }
        });

        btnCreateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateBookTitle() | !validateAuthor() | !validateCategories() |
                        !validatePublishedYear()) {
                    return;
                }

                progressBarCreateBook.setVisibility(View.VISIBLE);
                String title = edtBookTitle.getText().toString();
                String author = edtAuthor.getText().toString();
                String[] categoriesArray = edtCategories.getText().toString().split(",");
                List<String> categories = Arrays.asList(categoriesArray);
                int publishedYear = Integer.parseInt(edtPublisedYear.getText().toString());
                // TODO: Kiem tra cac thong tin da dien day du

                Book book = new Book(title, author, categories, publishedYear);
                if (coverBookUri != null) {
                    StorageReference coverBookRef = storageReference.child("cover_books").child(title + ".jpg");
                    coverBookRef.putFile(coverBookUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                addBookToFirestore(task, book, coverBookRef);
                            }
                            else {
                                Toast.makeText(CreateBookActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(CreateBookActivity.this, "Please select picture", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateBookTitle()
    {
        String input = edtBookTitle.getText().toString().trim();

        if (input.isEmpty())
        {
            edtBookTitle.setError("This field must not be empty");
            return false;
        }
        else
        {
            edtBookTitle.setError(null);
            return true;
        }

    }

    private boolean validateAuthor()
    {
        String input = edtAuthor.getText().toString().trim();

        if (input.isEmpty())
        {
            edtAuthor.setError("This field must not be empty");
            return false;
        }
        else
        {
            edtAuthor.setError(null);
            return true;
        }

    }

    private boolean validatePublishedYear()
    {
        String input = edtPublisedYear.getText().toString().trim();
        if (input.isEmpty())
        {
            edtPublisedYear.setError("This field must not be empty");
            return false;
        }
        else
        {
            edtPublisedYear.setError(null);
            return true;
        }

    }

    private boolean validateCategories()
    {
        String input = edtCategories.getText().toString().trim();
        if (input.isEmpty())
        {
            edtCategories.setError("This field must not be empty");
            return false;
        }
        else
        {
            edtCategories.setError(null);
            return true;
        }

    }

    private void addBookToFirestore(Task<UploadTask.TaskSnapshot> task, Book book, StorageReference coverBookRef) {
        coverBookRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(@NonNull Uri uri) {
                book.setImageUri(uri.toString());
                firestore.collection("Book").add(book).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(CreateBookActivity.this, CreatePostActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                coverBookUri = result.getUri();
                imgBook.setImageURI(coverBookUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(CreateBookActivity.this, result.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
