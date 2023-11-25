package com.example.artmind;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.artmind.utils.FirebaseUtil;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context helpContext;
    private ArrayList<HistoryCard> historyCardArrayList;

    public HistoryAdapter(Context context, ArrayList<HistoryCard> list) {
        helpContext = context;
        historyCardArrayList = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(helpContext).inflate(R.layout.card_history, parent, false);
        return new HistoryAdapter.HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryCard currentItem = historyCardArrayList.get(position);

        String image = currentItem.getHistoryImage();
        String category = currentItem.getHistoryCategory();
        String percentage = currentItem.getHistoryPercentage();
        String desc = currentItem.getHistoryDesc();

        // Split the string using the pipe symbol
        String[] parts = desc.split("\\|");

        // Format the parts with newlines
        StringBuilder formattedDesc = new StringBuilder();
        for (String part : parts) {
            formattedDesc.append(part.trim()); // Trim to remove leading/trailing spaces
            formattedDesc.append("\n"); // Add newline after each part
        }

        // Remove the trailing newline
        if (formattedDesc.length() > 0) {
            formattedDesc.setLength(formattedDesc.length() - 1);
        }

        // Save the formatted string back to desc
        desc = formattedDesc.toString();

        holder.historyCategory.setText(category);
        holder.historyPercentage.setText(percentage + "%");
        holder.historyDesc.setText(desc);

        FirebaseUtil.readHistory(historyModels -> {
            setHistoryImage(image, holder.historyImage);
        });
    }

    @Override
    public int getItemCount() {
        return historyCardArrayList.size();
    }

    private void setHistoryImage(String fileName, ImageView image) {
        FirebaseUtil.getHistoryStorageRef().child(fileName).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        Glide.with(helpContext).load(uri).into(image);
                    }
                });
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView historyImage;
        public TextView historyCategory;
        public TextView historyPercentage;
        public TextView historyDesc;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyImage = itemView.findViewById(R.id.history_image);
            historyCategory = itemView.findViewById(R.id.history_category);
            historyPercentage = itemView.findViewById(R.id.history_percentage);
            historyDesc = itemView.findViewById(R.id.history_desc);
        }
    }
}
