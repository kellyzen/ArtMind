package com.example.artmind.component.settings.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.artmind.R;
import com.example.artmind.component.settings.SettingFragment;
import com.example.artmind.utils.AndroidUtil;

/**
 * About page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class AboutFragment extends Fragment {
    SettingFragment settingFragment;

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
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        settingFragment = new SettingFragment();
        AndroidUtil.setupOnBackPressed(requireActivity(), settingFragment);
        return view;
    }
}