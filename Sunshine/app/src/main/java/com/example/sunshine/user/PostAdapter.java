package com.example.sunshine.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    List<Post> posts;
    long differenceSeconds, differenceMinutes, differenceHours, differenceDays, differenceYears;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        this.differenceSeconds = 0;
        this.differenceMinutes = 0;
        this.differenceHours = 0;
        this.differenceDays = 0;
        this.differenceYears = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: show user avatar and book author
        holder.txtUsername.setText(posts.get(position).getAuthor());
        holder.txtStatus.setText(posts.get(position).getStatus());
        holder.txtTime.setText(TimestampConverter.getTime(posts.get(position).getPostTime()));
        holder.txtTitle.setText(posts.get(position).getBookName());
        holder.txtContent.setText(posts.get(position).getContent());
        holder.btnUpvote.setText(String.valueOf(posts.get(position).getUpvote()));
        holder.btnDownvote.setText(String.valueOf(posts.get(position).getDownvote()));
        holder.btnComment.setText(String.valueOf(posts.get(position).getCommentCount()));

        holder.btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserMainActivity)context).fromFragmentToMain("POST", "SHOW-COMMENT",
                        posts.get(holder.getAdapterPosition()));
            }
        });

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtUsername, txtTime, txtTitle, txtAuthor, txtStatus, txtContent;
        ImageButton btnReport;
        MaterialButton btnUpvote, btnDownvote, btnComment, btnSave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            btnReport = (ImageButton) itemView.findViewById(R.id.btnReport);
            btnUpvote = (MaterialButton) itemView.findViewById(R.id.btnUpvote);
            btnDownvote = (MaterialButton) itemView.findViewById(R.id.btnDownvote);
            btnComment = (MaterialButton) itemView.findViewById(R.id.btnComment);
            btnSave = (MaterialButton) itemView.findViewById(R.id.btnSave);
        }
    }

}
