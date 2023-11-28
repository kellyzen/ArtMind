package com.example.artmind;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.artmind.utils.FirebaseUtil;

/**
 * Setting page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class SettingFragment extends Fragment {
    Button logoutBtn;
    Button aboutBtn;
    Button manualBtn;
    Button profileBtn;
    AboutFragment aboutFragment;
    UserFragment userFragment;
    EthicalGuidelineFragment ethicalGuidelineFragment;

    /**
     * Constructor method for Setting Fragment
     */
    public SettingFragment() {
    }

    /**
     * Create view for Setting Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        aboutFragment = new AboutFragment();
        userFragment = new UserFragment();
        ethicalGuidelineFragment = new EthicalGuidelineFragment();
        aboutBtn = view.findViewById(R.id.menu_about);
        profileBtn = view.findViewById(R.id.menu_profile);
        manualBtn = view.findViewById(R.id.menu_user_manual);

        // Navigates to respective pages on button click
        aboutBtn.setOnClickListener((v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, aboutFragment).commit();
        });

        profileBtn.setOnClickListener((v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, userFragment).commit();
        });

        manualBtn.setOnClickListener((v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, ethicalGuidelineFragment).commit();
        });

        // Logout
        logoutBtn = view.findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener((v) -> {
            FirebaseUtil.logout();
            Intent intent = new Intent(getContext(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}