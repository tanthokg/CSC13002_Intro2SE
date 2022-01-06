package com.example.sunshine.admin;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.Notification;
import com.example.sunshine.NotificationAdapter;
import com.example.sunshine.R;
import com.example.sunshine.database.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private Context context;
    private List<Report> reportList;

    public ReportAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Report> list)
    {
        this.reportList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ReportAdapter.ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        Report report = reportList.get(position);
        if (report == null)
            return;

        holder.reportImage.setImageResource(report.getResourceID());
        holder.reportTextView.setText(Html.fromHtml(report.toString()));
        holder.timeTextView.setText(report.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reportList != null)
            return reportList.size();
        return 0;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private ImageView reportImage;
        private TextView reportTextView;
        private TextView timeTextView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            reportImage = itemView.findViewById(R.id.notification_img);
            reportTextView = itemView.findViewById(R.id.notification_tv);
            timeTextView = itemView.findViewById(R.id.time_tv);

        }
    }
}
