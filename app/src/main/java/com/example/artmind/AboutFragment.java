package com.example.artmind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * About page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class AboutFragment extends Fragment {

    /**
     * Constructor method for About Fragment
     */
    public AboutFragment() {
    }

    /**
     * Create view for About Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}