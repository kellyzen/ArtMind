package com.example.artmind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artmind.utils.FirebaseUtil;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryCard> historyCardArrayList;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        //Get reference of recycler view
        recyclerView = view.findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyCardArrayList = new ArrayList<>();
        retrieveHistory();
        return view;
    }

    private void retrieveHistory() {
        // clear previous history list
        historyCardArrayList.clear();

        //read history from firestore
        FirebaseUtil.readHistory(historyModels -> {
            for (int i = historyModels.size() - 1; i >= 0; i--) {
                String category = historyModels.get(i).getCategory();
                String percentage = String.valueOf(historyModels.get(i).getPercentage());
                String desc = historyModels.get(i).getDesc();
                String imagePath = historyModels.get(i).getImagePath();
                historyCardArrayList.add(new HistoryCard(imagePath, category, percentage, desc));
            }
            historyAdapter = new HistoryAdapter(getActivity(), historyCardArrayList);
            recyclerView.setAdapter(historyAdapter);
        });
    }
}