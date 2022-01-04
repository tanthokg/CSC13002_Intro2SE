package com.example.sunshine.user;

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
import com.example.sunshine.database.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    List<Post> posts;
    FirebaseFirestore database;
    String currentUserId;

    public PostAdapter(Context context, List<Post> posts, String currentUserId) {
        this.context = context;
        this.posts = posts;
        this.database = FirebaseFirestore.getInstance();
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String postId = posts.get(position).postId;

        // Check the user was upvoted or not
        database.collection("Post/" + postId + "/Upvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (value.exists())
                        holder.btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
                    else
                        holder.btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
            }
        });

        // Get the upvote count to display on button upvote
        database.collection("Post/" + postId + "/Upvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (!value.isEmpty()) {
                        int count = value.size();
                        holder.btnUpvote.setText(String.valueOf(count));
                    }
                    else
                        holder.btnUpvote.setText("0");
                }
            }
        });

        // Check the user was downvoted or not
        database.collection("Post/" + postId + "/Downvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (value.exists())
                        holder.btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
                    else
                        holder.btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
            }
        });

        // Get the downvote count to display on button downvote
        database.collection("Post/" + postId + "/Downvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (!value.isEmpty()) {
                        int count = value.size();
                        holder.btnDownvote.setText(String.valueOf(count));
                    }
                    else
                        holder.btnDownvote.setText("0");
                }
            }
        });

        // Get the comment count to display on button comment
        database.collection("Post/" + postId + "/Comment").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (!value.isEmpty())
                        holder.btnComment.setText(String.valueOf(value.size()));
                    else
                        holder.btnComment.setText("0");
            }
        });

        // Check the user to save this post before
        database.collection("User/" + currentUserId + "/Read Later").document(postId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (value.exists())
                        holder.btnSave.setIconTint(ColorStateList.valueOf(Color.BLUE));
                    else
                        holder.btnSave.setIconTint(ColorStateList.valueOf(Color.BLACK));
                }
            }
        });
        
        holder.txtUsername.setText(posts.get(position).getPostBy());
        holder.txtAuthor.setText(posts.get(position).getAuthor());
        holder.txtStatus.setText(posts.get(position).getStatus());
        holder.txtTime.setText(TimestampConverter.getTime(posts.get(position).getPostTime()));
        holder.txtTitle.setText(posts.get(position).getBookName());
        holder.txtContent.setText(posts.get(position).getContent());

        holder.btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.collection("Post/" + postId + "/Upvotes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            database.collection("Post/" + postId + "/Downvotes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.getResult().exists()) {
                                        database.collection("Post/" + postId + "/Downvotes").document(currentUserId).delete();
                                    }

                                    Map<String, Object> likesMap = new HashMap<>();
                                    likesMap.put("timestamp", FieldValue.serverTimestamp());
                                    database.collection("Post/" + postId + "/Upvotes").document(currentUserId).set(likesMap);
                                }
                            });
                        }
                        else {
                            database.collection("Post/" + postId + "/Upvotes").document(currentUserId).delete();
                        }
                    }
                });

                database.collection("Post/" + postId + "/Upvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null)
                            if (value.exists())
                                holder.btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
                            else
                                holder.btnUpvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
                    }
                });

                database.collection("Post/" + postId + "/Upvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null) {
                            if (!value.isEmpty()) {
                                int count = value.size();
                                holder.btnUpvote.setText(String.valueOf(count));
                            }
                            else
                                holder.btnUpvote.setText("0");
                        }
                    }
                });
            }
        });

        holder.btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("Post/" + postId + "/Downvotes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            database.collection("Post/" + postId + "/Upvotes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.getResult().exists()) {
                                        database.collection("Post/" + postId + "/Upvotes").document(currentUserId).delete();
                                    }

                                    Map<String, Object> likesMap = new HashMap<>();
                                    likesMap.put("timestamp", FieldValue.serverTimestamp());
                                    database.collection("Post/" + postId + "/Downvotes").document(currentUserId).set(likesMap);
                                }
                            });
                        }
                        else {
                            database.collection("Post/" + postId + "/Downvotes").document(currentUserId).delete();
                        }
                    }
                });

                database.collection("Post/" + postId + "/Downvotes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null)
                            if (value.exists())
                                holder.btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLUE));
                            else
                                holder.btnDownvote.setIconTint(ColorStateList.valueOf(Color.BLACK));
                    }
                });

                database.collection("Post/" + postId + "/Downvotes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null) {
                            if (!value.isEmpty()) {
                                int count = value.size();
                                holder.btnDownvote.setText(String.valueOf(count));
                            }
                            else
                                holder.btnDownvote.setText("0");
                        }
                    }
                });
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
                database.collection("User/" + currentUserId + "/Read Later").document(postId).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!task.getResult().exists()) {
                                    Map<String, Object> saveTime = new HashMap<>();
                                    saveTime.put("saveTime", FieldValue.serverTimestamp());
                                    database.collection("User/" + currentUserId + "/Read Later").document(postId).set(saveTime);
                                    holder.btnSave.setIconTint(ColorStateList.valueOf(Color.BLUE));
                                } else
                                    Toast.makeText(context, "User saved this post before.", Toast.LENGTH_SHORT).show();
                            }
                        });
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
