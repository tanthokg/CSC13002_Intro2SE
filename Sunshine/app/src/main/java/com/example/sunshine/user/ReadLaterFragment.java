package com.example.sunshine.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReadLaterFragment extends Fragment {

    Context context;
    String currentUserId;
    FirebaseFirestore db;
    RecyclerView readLaterRecView;
    ReadLaterAdapter adapter;
    List<Post> readLaterList;

    public ReadLaterFragment(Context context, String currentUserId) {
        this.context = context;
        this.currentUserId = currentUserId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_read_later, container, false);

        db = FirebaseFirestore.getInstance();
        readLaterList = new ArrayList<Post>();

        readLaterRecView = view.findViewById(R.id.readLaterRecView);

        adapter = new ReadLaterAdapter(readLaterList, context, currentUserId, this);
        readLaterRecView.setAdapter(adapter);
        readLaterRecView.setLayoutManager(new LinearLayoutManager(context));

        listenDataChanged();
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void listenDataChanged() {
        readLaterList.clear();
        adapter.notifyDataSetChanged();
        db.collection("User/" + currentUserId + "/Read Later").orderBy("saveTime", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null) {
                            if (!value.isEmpty()) {
                                for (DocumentChange doc : value.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        String id = doc.getDocument().getId();
                                        db.collection("Post").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    Post reviewPost = task.getResult().toObject(Post.class).withId(id);
                                                    if (reviewPost != null)
                                                    {
                                                        readLaterList.add(reviewPost);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        });
                                    }
                                    else
                                        adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }
}
