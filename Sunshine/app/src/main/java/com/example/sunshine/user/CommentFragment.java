package com.example.sunshine.user;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Comment;
import com.example.sunshine.database.Post;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private RecyclerView commentRecView;

    private long differenceSeconds, differenceMinutes, differenceHours, differenceDays, differenceYears;

    public CommentFragment (Context context, Post post) {
        this.context = context;
        this.post = post;
        this.comments = new ArrayList<Comment>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        addCommentEditText = view.findViewById(R.id.addCommentEditText);
        commentRecView = view.findViewById(R.id.commentRecView);

        txtUsername.setText(post.getAuthor());
        txtStatus.setText(post.getStatus());
        txtTime.setText(getTime(post.getPostTime()));
        txtTitle.setText(post.getBookName());
        txtContent.setText(post.getContent());
        btnUpvote.setText(String.valueOf(post.getUpvote()));
        btnDownvote.setText(String.valueOf(post.getDownvote()));
        btnComment.setText(String.valueOf(post.getCommentCount()));

        comments = fetchComments();

        CommentAdapter adapter = new CommentAdapter(context, comments);
        commentRecView.setAdapter(adapter);
        commentRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private void differenceBetweenDate(Timestamp postTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String startDate = sdf.format(postTime.toDate());
        String endDate = sdf.format(new Date());

        try {
            Date d1 = sdf.parse(startDate);
            Date d2 = sdf.parse(endDate);

            long differenceTime = d2.getTime() - d1.getTime();
            differenceSeconds = Math.abs(TimeUnit.MILLISECONDS.toSeconds(differenceTime) % 60);
            differenceMinutes = Math.abs(TimeUnit.MILLISECONDS.toMinutes(differenceTime) % 60);
            differenceHours = Math.abs(TimeUnit.MILLISECONDS.toHours(differenceTime) % 24);
            differenceDays = Math.abs(TimeUnit.MILLISECONDS.toDays(differenceTime) % 365);
            differenceYears = TimeUnit.MILLISECONDS.toDays(differenceTime) / 365L;
        }
        catch (Exception e) {
            Log.v("Error when get the difference date", e.getMessage());
        }
    }

    private String getTime(Timestamp postTime) {
        SimpleDateFormat sfd;

        differenceBetweenDate(postTime);
        String time = "";
        if (differenceYears > 0) {
            sfd = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            time = sfd.format(postTime.toDate());
        }
        else {
            if (differenceDays > 6) {
                sfd = new SimpleDateFormat("MMM d", Locale.getDefault());
                time = sfd.format(postTime.toDate());
            } else {
                if (differenceDays > 0) {
                    time = differenceDays + "d ago";
                }
                else {
                    if (differenceHours > 0)
                        time = differenceHours + "h ago";
                    else {
                        if (differenceMinutes > 0)
                            time = differenceMinutes + "m ago";
                        else
                            time = differenceSeconds + "s ago";
                    }
                }
            }
        }
        return time;
    }

    private ArrayList<Comment> fetchComments() {
        ArrayList<Comment> result = new ArrayList<Comment>();



        return result;
    }
}
