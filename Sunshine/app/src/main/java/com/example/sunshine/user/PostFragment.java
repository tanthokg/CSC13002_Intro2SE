package com.example.sunshine.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    RecyclerView postRecView;
    PostAdapter adapter;
    Context context;
    private List<Post> postList;
    private FirebaseFirestore firebaseFirestore;
    private String bookName;

    public PostFragment(Context context, String bookName) {
        this.context = context;
        this.bookName = bookName;
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
        firebaseFirestore.collection("Post").whereEqualTo("bookName", bookName)
                .orderBy("postTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    if (!value.isEmpty()) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String postId = doc.getDocument().getId();
                                Post reviewPost = doc.getDocument().toObject(Post.class).withId(postId);
                                postList.add(reviewPost);
                                adapter.notifyDataSetChanged();
                            } else
                                adapter.notifyDataSetChanged();
                        }
                    }
                    else
                        Toast.makeText(context, "No review post for " + bookName, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
