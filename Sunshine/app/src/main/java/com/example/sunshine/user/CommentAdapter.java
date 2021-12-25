package com.example.sunshine.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Comment;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Comment> comments;
    private long differenceSeconds, differenceMinutes, differenceHours, differenceDays, differenceYears;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commentAuthor.setText(comments.get(position).getUsername());
        holder.commentContent.setText(comments.get(position).getContent());
        holder.commentTime.setText(getTime(comments.get(position).getPostTime()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView commentAuthor, commentTime, commentContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthor = itemView.findViewById(R.id.commentAuthor);
            commentTime = itemView.findViewById(R.id.commentTime);
            commentContent = itemView.findViewById(R.id.commentContent);
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
}
