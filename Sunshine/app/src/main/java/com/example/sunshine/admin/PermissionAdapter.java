package com.example.sunshine.admin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.database.PermissionId;
import com.example.sunshine.user.TimestampConverter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.example.sunshine.R;
import com.example.sunshine.database.Permission;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Permission> listPermissions;
    FirebaseFirestore database;
    String currentUserId;
    PermissionFragment permissionFragment;
    String userName;

    public PermissionAdapter(Context context, ArrayList<Permission> permissions, String currentUserId, PermissionFragment permissionFragment) {
        this.mContext = context;
        this.listPermissions = permissions;
        this.database = FirebaseFirestore.getInstance();
        this.currentUserId = currentUserId;
        this.permissionFragment = permissionFragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_permission_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.commentTime.setText(TimestampConverter.getTime(comments.get(position).getPostTime()));
      String permissionId = listPermissions.get(position).permissionId;

        // Gets user document from Firestore as reference
        DocumentReference docRef = database.collection("User").document(permissionId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "db username getString() is: " + document.getString("username"));
                        userName = (String) document.getString("username");
                        Log.d(TAG, "String UserName is: " + userName);
                        holder.txtUsername.setText(userName);

                    }
                    else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // TODO: show user avatar
        //   holder.txtUsername.setText(requests.get(position).getPostBy());
        holder.txtTime.setText(TimestampConverter.getTime(listPermissions.get(position).getPermissionTime()));

    }

    @Override
    public int getItemCount() {
        return listPermissions.size();}
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView  txtTime,txtUsername;
        Button acceptBtn, declineBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
           // imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtTime = (TextView) itemView.findViewById(R.id.time_tv);
            acceptBtn = (Button) itemView.findViewById(R.id.acceptBtn);
            declineBtn = (Button) itemView.findViewById(R.id.declineBtn);
        }
    }
}
