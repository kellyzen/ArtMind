package com.example.artmind;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.artmind.utils.FirebaseUtil;

import java.util.ArrayList;

/**
 * History page (adapter)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context helpContext;
    private ArrayList<HistoryCard> historyCardArrayList;

    /**
     * Constructor method for History Adapter
     *
     * @param context history fragment's context
     * @param list    history card list
     */
    public HistoryAdapter(Context context, ArrayList<HistoryCard> list) {
        helpContext = context;
        historyCardArrayList = list;
    }

    /**
     * Create view holder for history card
     */
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(helpContext).inflate(R.layout.card_history, parent, false);
        return new HistoryAdapter.HistoryViewHolder(v);
    }

    /**
     * Set text for history card on bind
     */
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
        String newDesc = formattedDesc.toString();

        holder.historyCategory.setText(category);
        holder.historyPercentage.setText(percentage + "%");
        holder.historyDesc.setText(newDesc);

        FirebaseUtil.readHistory(historyModels -> {
            setHistoryImage(image, holder.historyImage);
        });

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(view -> {
            ResultFragment resultFragment = new ResultFragment(Integer.parseInt(percentage), category, desc, image);
            ((AppCompatActivity) helpContext).getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, resultFragment).commit();
        });

    }

    /**
     * Get history card list size
     */
    @Override
    public int getItemCount() {
        return historyCardArrayList.size();
    }

    /**
     * Set result image on history card
     *
     * @param fileName file name of the image in Firebase (xxx.jpg)
     * @param image image view of the image to be set to
     */
    private void setHistoryImage(String fileName, ImageView image) {
        FirebaseUtil.getHistoryStorageRef().child(fileName).getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri uri = task.getResult();
                Glide.with(helpContext).load(uri).into(image);
            }
        });
    }

    /**
     * History page (view holder)
     */
    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView historyImage;
        public TextView historyCategory;
        public TextView historyPercentage;
        public TextView historyDesc;

        /**
         * Constructor method for History Holder
         *
         * @param itemView view id of the history card
         */
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyImage = itemView.findViewById(R.id.history_image);
            historyCategory = itemView.findViewById(R.id.history_category);
            historyPercentage = itemView.findViewById(R.id.history_percentage);
            historyDesc = itemView.findViewById(R.id.history_desc);
        }
    }
}
