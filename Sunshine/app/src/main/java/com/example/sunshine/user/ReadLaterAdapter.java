package com.example.sunshine.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReadLaterAdapter extends RecyclerView.Adapter<ReadLaterAdapter.ViewHolder> {

    List<Post> readLaterPostsList;
    Context context;
    String currentUserId;
    ReadLaterFragment readLaterFragment;
    FirebaseFirestore db;

    public ReadLaterAdapter(List<Post> readLaterPostsList, Context context, String currentUserId, ReadLaterFragment readLaterFragment) {
        this.readLaterPostsList = readLaterPostsList;
        this.context = context;
        this.currentUserId = currentUserId;
        this.readLaterFragment = readLaterFragment;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_read_later_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String postId = readLaterPostsList.get(position).postId;
        holder.readLaterNameBook.setText(readLaterPostsList.get(position).getBookName());
        holder.readLaterUserPost.setText(readLaterPostsList.get(position).getPostBy());

        db.collection("User/" + currentUserId + "/Read Later").document(postId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        DocumentSnapshot doc = task.getResult();
                        holder.txtTimeSaved.setText(TimestampConverter.getTime(doc.getTimestamp("saveTime")));
                    }
                }
            }
        });

        holder.btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserMainActivity) context).fromFragmentToMain("READ-LATER", "POST", readLaterPostsList.get(holder.getAdapterPosition()));
            }
        });

        holder.btnUnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder unsaveDialog = new AlertDialog.Builder(context);
                unsaveDialog.setTitle("Do you want to unsave this post?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("User/" + currentUserId + "/Read Later").document(postId).delete();
                                readLaterFragment.listenDataChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                unsaveDialog.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return readLaterPostsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView readLaterNameBook, readLaterUserPost, txtTimeSaved;
        Button btnMoreInfo, btnUnsave;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            readLaterNameBook = (TextView) itemView.findViewById(R.id.readLaterNameBook);
            readLaterUserPost = (TextView) itemView.findViewById(R.id.readLaterUserPost);
            txtTimeSaved = (TextView) itemView.findViewById(R.id.txtTimeSaved);
            btnMoreInfo = (Button) itemView.findViewById(R.id.btnMoreInfo);
            btnUnsave = (Button) itemView.findViewById(R.id.btnUnsave);
        }
    }
}
