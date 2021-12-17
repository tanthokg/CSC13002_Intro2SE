package com.example.sunshine.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.Notification;
import com.example.sunshine.NotificationAdapter;
import com.example.sunshine.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_notifications extends Fragment {

    public fragment_notifications(){}

    private RecyclerView newRev;
    private NotificationAdapter newAdapter;

    private RecyclerView recentlyRev;
    private NotificationAdapter recentlyAdapter;

    private RecyclerView oldRev;
    private NotificationAdapter oldAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newRev = (RecyclerView) view.findViewById(R.id.new_rev);
        newAdapter = new NotificationAdapter(this.getActivity());

        LinearLayoutManager newLinearLayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        newRev.setLayoutManager(newLinearLayoutManager);
        newAdapter.setData(getNewNotifications());
        newRev.setAdapter(newAdapter);

        recentlyRev = (RecyclerView) view.findViewById(R.id.recently_rev);
        recentlyAdapter = new NotificationAdapter(this.getActivity());

        LinearLayoutManager recentlyLinearLayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recentlyRev.setLayoutManager(recentlyLinearLayoutManager);
        recentlyAdapter.setData(getRecentlyNotifications());
        recentlyRev.setAdapter(recentlyAdapter);





        oldRev = (RecyclerView) view.findViewById(R.id.old_rev);
        oldAdapter = new NotificationAdapter(this.getActivity());

        LinearLayoutManager oldLinearLayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        oldRev.setLayoutManager(oldLinearLayoutManager);
        oldAdapter.setData(getOldNotifications());
        oldRev.setAdapter(oldAdapter);


    }

    private List<Notification> getNewNotifications()
    {
        List<Notification> list = new ArrayList<>();

        List<String> firstUserNames = new ArrayList<>();
        int upvote = 1;
        firstUserNames.add("user22");
        firstUserNames.add("user45");
        String firstPost = "Harry Potter and The Philosopher's Stone";
        String firstTime = "Just now";

        int downvote = 0;
        List<String> secondUserNames = new ArrayList<>();
        secondUserNames.add("user118");
        secondUserNames.add("user03");
        String secondPost = "Đắc Nhân Tâm";
        String secondTime = "5 minutes ago";

        int comment = 2;
        List<String> thirdUserNames = new ArrayList<>();
        thirdUserNames.add("user99");
        thirdUserNames.add("user47");
        String thirdPost = "Cánh Đồng Bất Tận";
        String thirdTime = "20 minutes ago";

        Notification firstNew = new Notification(upvote,firstUserNames, firstPost, 98,firstTime);
        Notification secondNew = new Notification(downvote, secondUserNames ,secondPost,17, secondTime);
        Notification thirdNew = new Notification(comment, thirdUserNames, thirdPost,9,thirdTime);

        list.add(firstNew);
        list.add(secondNew);
        list.add(thirdNew);

        return list;
    }

    private List<Notification> getRecentlyNotifications()
    {
        List<Notification> list = new ArrayList<>();

        int upvote = 1;
        List<String> firstUserNames = new ArrayList<>();
        firstUserNames.add("user77");
        firstUserNames.add("user93");
        String firstPost = "Harry Potter and The Goblet of Fire";
        String firstTime = "2 hours ago";


        int comment = 2;
        List<String> secondUserNames = new ArrayList<>();
        secondUserNames.add("user22");
        secondUserNames.add("user10");
        String secondPost = "Harry Potter and The Goblet of Fire";
        String secondTime = "4 hours ago";

        Notification firstRecent = new Notification(upvote, firstUserNames, firstPost, 131, firstTime);
        Notification secondRecent = new Notification(comment, secondUserNames, secondPost, 34, secondTime);

        list.add(firstRecent);
        list.add(secondRecent);

        return list;
    }

    private List<Notification> getOldNotifications()
    {
        List<Notification> list = new ArrayList<>();

        int promoted = 3;
        List<String> firstUserNames = new ArrayList<>();

        Notification firstOld = new Notification(promoted, firstUserNames, "",0,"3 days ago");

        list.add(firstOld);

        return list;
    }


}
