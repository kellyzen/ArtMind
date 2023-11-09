package com.example.artmind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindHelpAdapter extends RecyclerView.Adapter<FindHelpAdapter.FindHelpViewHolder> {
    private Context helpContext;
    private ArrayList<FindHelpCard> findHelpCardArrayList;

    public FindHelpAdapter(Context context, ArrayList<FindHelpCard> list) {
        helpContext = context;
        findHelpCardArrayList = list;
    }

    @NonNull
    @Override
    public FindHelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(helpContext).inflate(R.layout.card_find_help, parent, false);
        return new FindHelpViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FindHelpViewHolder holder, int position) {
        FindHelpCard currentItem = findHelpCardArrayList.get(position);

        String name = currentItem.getHelpCentreName();
        String title = currentItem.getHelpCentreTitle();
        String website = currentItem.getHelpCentreWebsite();
        String contact = currentItem.getHelpCentreContact();

        holder.helpCentreName.setText(name);
        holder.helpCentreTitle.setText(title);
        holder.helpCentreContact.setText(contact);
        if (website == null) {
            holder.helpCentreWebsite.setText("");
        }

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(view -> {
            if (website != null) {
                // redirect to website on item click
                Uri uri = Uri.parse("http://" + website);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                helpContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return findHelpCardArrayList.size();
    }

    public class FindHelpViewHolder extends RecyclerView.ViewHolder {
        public TextView helpCentreName;
        public TextView helpCentreTitle;
        public TextView helpCentreContact;
        public TextView helpCentreWebsite;

        public FindHelpViewHolder(@NonNull View itemView) {
            super(itemView);
            helpCentreName = itemView.findViewById(R.id.help_centre_name);
            helpCentreTitle = itemView.findViewById(R.id.help_centre_title);
            helpCentreContact = itemView.findViewById(R.id.help_centre_contact);
            helpCentreWebsite = itemView.findViewById(R.id.website_redirect);
            helpCentreTitle.setPaintFlags(helpCentreTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
