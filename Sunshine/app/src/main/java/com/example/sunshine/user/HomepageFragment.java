package com.example.sunshine.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Book;
import com.example.sunshine.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class HomepageFragment extends Fragment {

    EditText searchBar;
    RecyclerView recommendRecView, newTrendingRecView;
    FloatingActionButton btnCreatePost;
    HomeAdapter recommendAdapter, newTrendingAdapter;
    Context context;
    private List<Book> recommendBookList;
    private List<Book> newTrendingBookList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private boolean typeUser;

    public HomepageFragment(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        getTypeUser();
        return view;
    }

    private void getTypeUser() {
        String currentUserId = auth.getCurrentUser().getUid();
        firebaseFirestore.collection("User").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        User user = task.getResult().toObject(User.class);
                        if (user != null) {
                            typeUser = user.isType();
                            if (typeUser)
                                btnCreatePost.setVisibility(View.VISIBLE);
                            else
                                btnCreatePost.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recommendBookList = new ArrayList<>();
        newTrendingBookList = new ArrayList<>();

        listenDataChange();

        searchBar = (EditText) view.findViewById(R.id.searchBar);
        recommendRecView = (RecyclerView) view.findViewById(R.id.recommendRecView);
        newTrendingRecView = (RecyclerView) view.findViewById(R.id.newTrendingRecView);
        btnCreatePost = (FloatingActionButton) view.findViewById(R.id.btnCreatePost);

        btnCreatePost.setVisibility(View.INVISIBLE);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreatePostActivity.class);
                startActivity(intent);
            }
        });

        recommendAdapter = new HomeAdapter(recommendBookList, context);
        recommendRecView.setAdapter(recommendAdapter);
        recommendRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        newTrendingAdapter = new HomeAdapter(newTrendingBookList, context);
        newTrendingRecView.setAdapter(newTrendingAdapter);
        newTrendingRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    private void listenDataChange() {
        firebaseFirestore.collection("Book").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null)
                    if (!value.isEmpty())
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED)
                            {
                                String bookId = doc.getDocument().getId();
                                Book book = doc.getDocument().toObject(Book.class).withId(bookId);
                                recommendBookList.add(book);
                                recommendAdapter.notifyDataSetChanged();

                                newTrendingBookList.add(book);
                                newTrendingAdapter.notifyDataSetChanged();
                            }
                        }
            }
        });
    }
}
