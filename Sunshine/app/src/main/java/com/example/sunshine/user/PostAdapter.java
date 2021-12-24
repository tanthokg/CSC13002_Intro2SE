package com.example.sunshine.user;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    List<Post> postList;
    long differenceSeconds, differenceMinutes, differenceHours, differenceDays, differenceYears;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
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
        //TODO: the avatar of the user and the author of the book
        holder.txtUsername.setText(postList.get(position).getAuthor());

        Timestamp postDate = postList.get(position).getPostTime();
        SimpleDateFormat sfd;
        //long differenceSeconds = 0, differenceMinutes = 0, differenceHours = 0, differenceDays = 0, differenceYears = 0;
        differenceBetweenDate(postDate);
        String time = "";
        if (differenceYears > 0) {
            sfd = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            time = sfd.format(postDate.toDate());
        }
        else {
            if (differenceDays > 6) {
                sfd = new SimpleDateFormat("MMM d", Locale.getDefault());
                time = sfd.format(postDate.toDate());
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
        holder.txtStatus.setText(postList.get(position).getStatus());
        holder.txtTime.setText(/*postList.get(position).getPostTime().toDate().toString()*/ time);
        holder.txtTitle.setText(postList.get(position).getBookName());
        holder.txtContent.setText(postList.get(position).getContent());
        holder.btnUpvote.setText(String.valueOf(postList.get(position).getUpvote()));
        holder.btnDownvote.setText(String.valueOf(postList.get(position).getDownvote()));
        holder.btnComment.setText(String.valueOf(postList.get(position).getCommentCount()));

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
            differenceYears = TimeUnit.MILLISECONDS.toDays(differenceTime) / 365l;
        }
        catch (Exception e) {
            Log.v("Error when get the difference date", e.getMessage());
        }
    }
}
