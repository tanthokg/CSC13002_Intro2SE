package com.example.sunshine.user;

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    RecyclerView postRecView;
    PostAdapter adapter;
    Context context;
    private List<Post> postList;
    private FirebaseFirestore firebaseFirestore;

    public PostFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View postFragment = inflater.inflate(R.layout.fragment_post, container, false);

        postList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        listenDataChanged();

        postRecView = postFragment.findViewById(R.id.postRecView);
        adapter = new PostAdapter(context, postList);
        postRecView.setAdapter(adapter);
        postRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        return postFragment;
    }

    private void listenDataChanged() {
        firebaseFirestore.collection("Post").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Post reviewPost = doc.getDocument().toObject(Post.class);
                        postList.add(reviewPost);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
