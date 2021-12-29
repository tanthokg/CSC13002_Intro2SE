package com.example.sunshine.user;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Comment;
import com.example.sunshine.database.Post;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommentFragment extends Fragment {
    private Context context;
    private Post post;
    private ArrayList<Comment> comments;

    private ImageView imgAvatar;
    private TextView txtUsername, txtTime, txtTitle, txtAuthor, txtStatus, txtContent;
    private ImageButton btnReport;
    private MaterialButton btnUpvote, btnDownvote, btnComment, btnSave;

    private EditText addCommentEditText;
    private Button sendBtn;
    private RecyclerView commentRecView;
    private CommentAdapter adapter;

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String currentUserId, postId;

    public CommentFragment (Context context, Post post) {
        this.context = context;
        this.post = post;
        this.postId = post.postId;
        this.comments = new ArrayList<Comment>();
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
        this.currentUserId = auth.getCurrentUser().getUid();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_comment, container, false);

        // Check the user was upvoted or not
        db.collection("Post/" + postId + "/Upvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (value.exists())
                        btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
                    else
                        btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
            }
        });

        // Get the upvote count to display on button upvote
        db.collection("Post/" + postId + "/Upvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (!value.isEmpty()) {
                        int count = value.size();
                        btnUpvote.setText(String.valueOf(count));
                    }
                    else
                        btnUpvote.setText("0");
                }
            }
        });

        // Check the user was downvoted or not
        db.collection("Post/" + postId + "/Downvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (value.exists())
                        btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
                    else
                        btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
            }
        });

        // Get the downvote count to display on button downvote
        db.collection("Post/" + postId + "/Downvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (!value.isEmpty()) {
                        int count = value.size();
                        btnDownvote.setText(String.valueOf(count));
                    }
                    else
                        btnDownvote.setText("0");
                }
            }
        });

        // Get the comment count to display on button comment
        db.collection("Post/" + postId + "/Comment").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (!value.isEmpty())
                        btnComment.setText(String.valueOf(value.size()));
                    else
                        btnComment.setText("0");
            }
        });

        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtTime = view.findViewById(R.id.txtTime);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtStatus = view.findViewById(R.id.txtStatus);
        txtContent = view.findViewById(R.id.txtContent);
        btnReport = view.findViewById(R.id.btnReport);
        btnUpvote = view.findViewById(R.id.btnUpvote);
        btnDownvote = view.findViewById(R.id.btnDownvote);
        btnComment = view.findViewById(R.id.btnComment);
        btnSave = view.findViewById(R.id.btnSave);
        addCommentEditText = view.findViewById(R.id.addCommentEditText);
        sendBtn = view.findViewById(R.id.sendBtn);
        commentRecView = view.findViewById(R.id.commentRecView);

        txtUsername.setText(post.getAuthor());
        txtStatus.setText(post.getStatus());
        txtTime.setText(TimestampConverter.getTime(post.getPostTime()));
        txtTitle.setText(post.getBookName());
        txtContent.setText(post.getContent());

        adapter = new CommentAdapter(context, comments);
        fetchComments();
        commentRecView.setAdapter(adapter);
        commentRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        return view;
    }

    private void fetchComments() {
        /*CollectionReference ref = db.collection("Post");
        Query query = ref.whereEqualTo("author", post.getAuthor()).whereEqualTo("bookName", post.getBookName());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        db.collection("Post").document(document.getId()).collection("Comment")
                        .orderBy("postTime").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Comment comment = doc.getDocument().toObject(Comment.class);
                                        comments.add(comment);
                                        adapter.notifyItemInserted(comments.size());
                                    }
                                }
                            }
                        });
                    }
                }
                else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });*/

        db.collection("Post/" + postId + "/Comment").orderBy("postTime").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                {
                    if (!value.isEmpty())
                    {
                        for (DocumentChange doc : value.getDocumentChanges())
                        {
                            if (doc.getType() == DocumentChange.Type.ADDED)
                            {
                                Comment comment = doc.getDocument().toObject(Comment.class);
                                comments.add(comment);
                                adapter.notifyItemInserted(comments.size());
                            }
                        }
                    }
                }
                else
                    Log.d(TAG, "Error getting documents: ", error);
            }
        });
    }

    private void addComment() {
        /*String content = addCommentEditText.getText().toString();
        if (content.length() == 0)
            return;
        Comment comment = new Comment("anonymous", content, new Timestamp(new Date()));

        CollectionReference ref = db.collection("Post");
        Query query = ref.whereEqualTo("author", post.getAuthor()).whereEqualTo("bookName", post.getBookName());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        db.collection("Post").document(document.getId()).collection("Comment")
                                .add(comment);
                        db.collection("Post/" + postId + "/Comment").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error == null)
                                    if (!value.isEmpty())
                                        btnComment.setText(String.valueOf(value.size()));
                                    else
                                        btnComment.setText("0");
                            }
                        });
                    }
                }
                else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        addCommentEditText.setText("");*/

        String content = addCommentEditText.getText().toString();
        if (content.length() == 0)
        {
            Toast.makeText(context, "Please write a comment", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("User").document(currentUserId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            if (task.getResult() != null) {
                                User user = (User) task.getResult().toObject(User.class);
                                String username = user.getUsername();
                                Comment comment = new Comment(username, content, new Timestamp(new Date()));
                                db.collection("Post/" + postId + "/Comment").add(comment);
                                db.collection("Post/" + postId + "/Comment").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (error == null)
                                            if (!value.isEmpty())
                                                btnComment.setText(String.valueOf(value.size()));
                                            else
                                                btnComment.setText("0");
                                    }
                                });
                            }
                            else
                                Toast.makeText(context, "No found user.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        addCommentEditText.setText("");
    }
}
