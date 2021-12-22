package com.example.sunshine.user;

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

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<Post> postList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return postList.size();
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
