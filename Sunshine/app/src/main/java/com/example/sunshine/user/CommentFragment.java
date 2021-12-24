package com.example.sunshine.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.android.material.button.MaterialButton;

public class CommentFragment extends Fragment {
    private Post post;
    ImageView imgAvatar;
    TextView txtUsername, txtTime, txtTitle, txtAuthor, txtStatus, txtContent;
    ImageButton btnReport;
    MaterialButton btnUpvote, btnDownvote, btnComment, btnSave;

    public CommentFragment (Post post) {
        this.post = post;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((User_Main) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_comment, container, false);
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

        txtStatus.setText(post.getStatus());
        txtTime.setText(post.getPostTime().toString());
        txtTitle.setText(post.getBookName());
        txtContent.setText(post.getContent());
        btnUpvote.setText(String.valueOf(post.getUpvote()));
        btnDownvote.setText(String.valueOf(post.getDownvote()));
        btnComment.setText(String.valueOf(post.getCommentCount()));

        return view;
    }
}
