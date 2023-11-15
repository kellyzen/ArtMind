package com.example.artmind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FindHelpFragment extends Fragment {
    CountryCodePicker countryCodePicker;
    private RecyclerView recyclerView;
    private FindHelpAdapter findHelpAdapter;
    private ArrayList<FindHelpCard> findHelpCardArrayList;
    private String country;

    public FindHelpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_help, container, false);

        //Determine selected country
        countryCodePicker = view.findViewById(R.id.find_help_countrycode);
        country = countryCodePicker.getSelectedCountryNameCode().toLowerCase();

        countryCodePicker.setOnCountryChangeListener(() -> {
            country = countryCodePicker.getSelectedCountryNameCode().toLowerCase();
            try {
                parseJSON();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        //Get reference of recycler view
        recyclerView = view.findViewById(R.id.help_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        findHelpCardArrayList = new ArrayList<>();
        try {
            parseJSON();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return view;
    }

    private void parseJSON() throws JSONException {
        String name;
        String title;
        String website;
        String phone;
        String email;
        String whatsapp;
        String contact;
        // get JSONObject from JSON file
        JSONObject obj = new JSONObject(loadJSONFromAsset());

        // clear previous selected country list
        findHelpCardArrayList.clear();

        // fetch JSONArray for specific country
        JSONArray countryArray = obj.optJSONArray(country);
        if (countryArray == null) {
            name = getResources().getString(R.string.country_name_null);
            title = "";
            contact = getResources().getString(R.string.country_desc_null);
            findHelpCardArrayList.add(new FindHelpCard(name, title, null, contact));
        } else {
            // implement for loop for getting users list data
            for (int i = 0; i < countryArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject helpDetail = countryArray.getJSONObject(i);

                // create a object for getting contact data from JSONObject
                JSONObject jContact = helpDetail.getJSONObject("contact");
                name = helpDetail.getString("name");
                title = helpDetail.optString("title");
                website = helpDetail.getString("website");
                phone = jContact.optString("phone");
                email = jContact.optString("email");
                whatsapp = jContact.optString("whatsapp");
                contact = "";
                if (!phone.equals("")) {
                    contact = contact + "\n" + "Phone: " + phone;
                }
                if (!email.equals("")) {
                    contact = contact + "\n" + "Email: " + email;
                }
                if (!whatsapp.equals("")) {
                    contact = contact + "\n" + "Whatsapp: " + whatsapp;
                }
//                contact = phone + "\n" + whatsapp + "\n" + email;

                findHelpCardArrayList.add(new FindHelpCard(name, title, website, contact));
            }
        }
        findHelpAdapter = new FindHelpAdapter(getActivity(), findHelpCardArrayList);
        recyclerView.setAdapter(findHelpAdapter);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("help_centres.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}