package com.example.sunshine;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.text.Html;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{

    private Context context;
    private List<Notification> notificationList;

    public NotificationAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Notification> list)
    {
        this.notificationList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        if (notification == null)
            return;

        holder.notificationImage.setImageResource(notification.getResourceID());
        holder.notificationTextView.setText(Html.fromHtml(notification.toString()));
        holder.timeTextView.setText(notification.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (notificationList != null)
            return notificationList.size();
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private ImageView notificationImage;
        private TextView notificationTextView;
        private TextView timeTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationImage = itemView.findViewById(R.id.notification_img);
            notificationTextView = itemView.findViewById(R.id.notification_tv);
            timeTextView = itemView.findViewById(R.id.time_tv);

        }
    }
}
