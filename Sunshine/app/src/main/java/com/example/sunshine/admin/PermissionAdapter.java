package com.example.sunshine.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.TimestampConverter;
import com.example.sunshine.database.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {
    private Context context;
    List<Request> permissions;
    FirebaseFirestore database;
    String currentUserId;
    boolean isReadLater;
  //  PostFragment postFragment;

    public PermissionAdapter(Context context, List<Request> permissions, String currentUserId) {
        this.context = context;
        this.permissions= permissions;
        this.database = FirebaseFirestore.getInstance();
        this.currentUserId = currentUserId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_permission_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String requestId = permissions.get(position).requestId;


        // TODO: show user avatar
        //   holder.txtUsername.setText(requests.get(position).getPostBy());
        // holder.txtAuthor.setText(posts.get(position).getAuthor());
        //  holder.txtStatus.setText(posts.get(position).getStatus());
        holder.txtTime.setText(TimestampConverter.getTime(permissions.get(position).getRequestTime()));
        //   holder.txtTitle.setText(posts.get(position).getBookName());
        //  holder.txtContent.setText(posts.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

//        holder.btnUpvote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                database.collection("Post/" + postId + "/Upvotes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (!task.getResult().exists()) {
//                            Map<String, Object> likesMap = new HashMap<>();
//                            likesMap.put("timestamp", FieldValue.serverTimestamp());
//                            database.collection("Post/" + postId + "/Upvotes").document(currentUserId).set(likesMap);
//                        }
//                        else {
//                            database.collection("Post/" + postId + "/Upvotes").document(currentUserId).delete();
//                        }
//                    }
//                });
//
//                database.collection("Post/" + postId + "/Upvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error == null)
//                            if (value.exists())
//                                holder.btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
//                            else
//                                holder.btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
//                    }
//                });
//
//                database.collection("Post/" + postId + "/Upvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error == null) {
//                            if (!value.isEmpty()) {
//                                int count = value.size();
//                                holder.btnUpvote.setText(String.valueOf(count));
//                            }
//                            else
//                                holder.btnUpvote.setText("0");
//                        }
//                    }
//                });
//            }
//        });
//
//        holder.btnDownvote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String postId = posts.get(position).postId;
//                database.collection("Post/" + postId + "/Downvotes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (!task.getResult().exists()) {
//                            Map<String, Object> likesMap = new HashMap<>();
//                            likesMap.put("timestamp", FieldValue.serverTimestamp());
//                            database.collection("Post/" + postId + "/Downvotes").document(currentUserId).set(likesMap);
//                        }
//                        else {
//                            database.collection("Post/" + postId + "/Downvotes").document(currentUserId).delete();
//                        }
//                    }
//                });
//
//                database.collection("Post/" + postId + "/Downvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error == null)
//                            if (value.exists())
//                                holder.btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
//                            else
//                                holder.btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
//                    }
//                });
//
//                database.collection("Post/" + postId + "/Downvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error == null) {
//                            if (!value.isEmpty()) {
//                                int count = value.size();
//                                holder.btnDownvote.setText(String.valueOf(count));
//                            }
//                            else
//                                holder.btnDownvote.setText("0");
//                        }
//                    }
//                });
//            }
//        });
//
//        holder.btnComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((UserMainActivity)context).fromFragmentToMain("POST", "SHOW-COMMENT",
//                        posts.get(holder.getAdapterPosition()));
//            }
//        });
//
//        holder.btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isReadLater) {
//                    AlertDialog.Builder unsaveDialog = new AlertDialog.Builder(context);
//                    unsaveDialog.setTitle("Do you want to unsave this post?")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    database.collection("User/" + currentUserId + "/Read Later").document(postId).delete();
//                                    postFragment.listenDataChanged();
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            });
//                    unsaveDialog.create().show();
//                }
//                else {
//                    database.collection("User/" + currentUserId + "/Read Later").document(postId).get()
//                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (!task.getResult().exists()) {
//                                        Map<String, Object> saveTime = new HashMap<>();
//                                        saveTime.put("saveTime", FieldValue.serverTimestamp());
//                                        database.collection("User/" + currentUserId + "/Read Later").document(postId).set(saveTime);
//                                        holder.btnSave.setIconTint(ColorStateList.valueOf(Color.BLUE));
//                                    } else
//                                        Toast.makeText(context, "User saved this post before.", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }
//            }
//        });
//    }

//    @Override
//    public int getItemCount() {
//        return posts.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtUsername, txtTime;
       // ImageButton btnReport;
        MaterialButton btnAccept, btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
//            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
//            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
//            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
//            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
//            btnReport = (ImageButton) itemView.findViewById(R.id.btnReport);
//            btnUpvote = (MaterialButton) itemView.findViewById(R.id.btnUpvote);
//            btnDownvote = (MaterialButton) itemView.findViewById(R.id.btnDownvote);
//            btnComment = (MaterialButton) itemView.findViewById(R.id.btnComment);
//            btnSave = (MaterialButton) itemView.findViewById(R.id.btnSave);
        }
    }
}
