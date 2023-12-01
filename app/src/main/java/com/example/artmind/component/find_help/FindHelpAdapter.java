package com.example.artmind.component.find_help;

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

import com.example.artmind.R;

import java.util.ArrayList;

/**
 * Find Help page (adapter)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class FindHelpAdapter extends RecyclerView.Adapter<FindHelpAdapter.FindHelpViewHolder> {
    private Context helpContext;
    private ArrayList<FindHelpCard> findHelpCardArrayList;

    /**
     * Constructor method for Find Help Adapter
     *
     * @param context find help fragment's context
     * @param list    find help card list
     */
    public FindHelpAdapter(Context context, ArrayList<FindHelpCard> list) {
        helpContext = context;
        findHelpCardArrayList = list;
    }

    /**
     * Create view holder for find help card
     */
    @NonNull
    @Override
    public FindHelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(helpContext).inflate(R.layout.card_find_help, parent, false);
        return new FindHelpViewHolder(v);
    }

    /**
     * Set text for find help card on bind
     */
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

        holder.itemView.setOnClickListener(view -> {
            if (website != null) {
                // Redirect to website on item click
                Uri uri = Uri.parse("http://" + website);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                helpContext.startActivity(intent);
            }
        });
    }

    /**
     * Get find help card list size
     */
    @Override
    public int getItemCount() {
        return findHelpCardArrayList.size();
    }

    /**
     * Find Help page (view holder)
     */
    public class FindHelpViewHolder extends RecyclerView.ViewHolder {
        public TextView helpCentreName;
        public TextView helpCentreTitle;
        public TextView helpCentreContact;
        public TextView helpCentreWebsite;

        /**
         * Constructor method for Find Help Holder
         *
         * @param itemView view id of the find help card
         */
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
