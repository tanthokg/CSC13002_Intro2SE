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

public class PostFragment extends Fragment {

    RecyclerView postRecView;
    PostAdapter adapter;
    Context context;

    public PostFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View postFragment = inflater.inflate(R.layout.fragment_post, container, false);

        postRecView = postFragment.findViewById(R.id.postRecView);
        adapter = new PostAdapter();
        postRecView.setAdapter(adapter);
        postRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        return postFragment;
    }
}
