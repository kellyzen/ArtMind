package com.example.artmind;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.artmind.utils.AndroidUtil;
import com.example.artmind.utils.FirebaseUtil;

public class HistoryFragment extends Fragment {
    ImageView historyImage;
    TextView historyCategory;
    TextView historyPercentage;
    TextView historyDesc;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyImage = view.findViewById(R.id.history_image);
        historyCategory = view.findViewById(R.id.history_category);
        historyPercentage = view.findViewById(R.id.history_percentage);
        historyDesc = view.findViewById(R.id.history_desc);

        FirebaseUtil.readHistory(historyModels -> {
            historyCategory.setText(historyModels.get(0).getCategory());
            historyPercentage.setText(String.valueOf(historyModels.get(0).getPercentage()));
            historyDesc.setText(historyModels.get(0).getDesc());
            String imagePath = historyModels.get(0).getImagePath();
            setHistoryImage(imagePath);
        });

        return view;
    }

    private void setHistoryImage(String fileName) {
        FirebaseUtil.getHistoryStorageRef().child(fileName).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        AndroidUtil.setImage(getContext(), uri, historyImage);
                    }
                });
    }
}