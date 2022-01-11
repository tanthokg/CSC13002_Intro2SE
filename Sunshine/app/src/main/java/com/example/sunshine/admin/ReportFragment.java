package com.example.sunshine.admin;

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
import com.example.sunshine.database.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {
    private RecyclerView reports;
    private ReportAdapter reportAdapter;
    public ReportFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reports = (RecyclerView) view.findViewById(R.id.reports);
        reportAdapter = new ReportAdapter(this.getActivity());

        LinearLayoutManager reportLinearLayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        reports.setLayoutManager(reportLinearLayoutManager);
        reportAdapter.setData(getReports());
        reports.setAdapter(reportAdapter);
    }

    private List<Report> getReports() {
        List<Report> list = new ArrayList<>();

        Report Report1 = new Report(3, "user32", "nothing", "Just now");
        Report Report2 = new Report(3, "user22", "nothing", "5 minutes ago");
        Report Report3 = new Report(1, "user15", "Harry Potter and The Goblet of Fire", "2 hours ago");
        Report Report4 = new Report(2, "user675", "Naruto", "6 hours ago");
        Report Report5 = new Report(1, "user17", "Les Misérables", "20 hours ago");
        Report Report6 = new Report(1, "user12", "To Kill a Mocking Birds", "a day ago");
        Report Report7 = new Report(2, "user11", "Twilight", "2 days ago");

        list.add(Report1);
        list.add(Report2);
        list.add(Report3);
        list.add(Report4);
        list.add(Report5);
        list.add(Report6);
        list.add(Report7);

        return list;
    }
}
