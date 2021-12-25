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
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;
import com.example.sunshine.database.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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

    public HomepageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*recommendBookList = new ArrayList<>();
        newTrendingBookList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        //listenDataChange();

        searchBar = (EditText) view.findViewById(R.id.searchBar);
        recommendRecView = (RecyclerView) view.findViewById(R.id.recommendRecView);
        newTrendingRecView = (RecyclerView) view.findViewById(R.id.newTrendingRecView);*/
        btnCreatePost = (FloatingActionButton) view.findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreatePostActivity.class);
                startActivity(intent);
            }
        });

        /*recommendAdapter = new HomeAdapter(recommendBookList);
        recommendRecView.setAdapter(recommendAdapter);
        recommendRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        newTrendingAdapter = new HomeAdapter(newTrendingBookList);
        newTrendingRecView.setAdapter(newTrendingAdapter);
        newTrendingRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));*/
    }
    //TODO: code adapter to display review post
    private void listenDataChange() {
        firebaseFirestore.collection("Book").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                
            }
        });
    }
}
