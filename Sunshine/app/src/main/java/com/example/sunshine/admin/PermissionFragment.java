package com.example.sunshine.admin;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Permission;
import com.example.sunshine.database.PermissionId;
import com.example.sunshine.database.User;
import com.example.sunshine.user.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PermissionFragment extends Fragment {
    private RecyclerView permissionRecView;
    private PermissionAdapter adapter;
    private Context context;
    private ArrayList<Permission> perList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String currentUserId;

    public PermissionFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View permissionFragment = inflater.inflate(R.layout.admin_fragment_permission, container, false);

        perList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();
        permissionRecView = permissionFragment.findViewById(R.id.permissionRecView);
        adapter = new PermissionAdapter(context, perList, currentUserId, this);
        permissionRecView.setAdapter(adapter);
        permissionRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        readPermissionData();
        return permissionFragment;
    }

    private void readPermissionData() {

        firebaseFirestore.collection("Permission").orderBy("permissionTime").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (!value.isEmpty()) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String perId = doc.getDocument().getId();
                                Permission _permission = doc.getDocument().toObject(Permission.class).withId(perId);
                                perList.add(_permission);
                                adapter.notifyDataSetChanged();
                            } else
                                adapter.notifyDataSetChanged();
                        }
                    } else
                        Toast.makeText(context, "No permission", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void listenDataChanged(String ID) {
        adapter.notifyDataSetChanged();
        firebaseFirestore.collection("Permission").document(ID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Permission _permission = doc.getDocument().toObject(Permission.class).withId(perId);
                       // perList.remove();
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        adapter.notifyDataSetChanged();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
