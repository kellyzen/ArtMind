package com.example.artmind;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.artmind.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingFragment extends Fragment {
    Button logoutBtn;
    Button aboutBtn;
    Button manualBtn;
    Button profileBtn;
    AboutFragment aboutFragment;
    UserFragment userFragment;
    EthicalGuidelineFragment ethicalGuidelineFragment;

    public SettingFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        //Navigation Buttons
        aboutFragment = new AboutFragment();
        userFragment = new UserFragment();
        ethicalGuidelineFragment = new EthicalGuidelineFragment();

        aboutBtn = view.findViewById(R.id.menu_about);
        profileBtn = view.findViewById(R.id.menu_profile);
        manualBtn = view.findViewById(R.id.menu_user_manual);

        aboutBtn.setOnClickListener((v)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, aboutFragment).commit();
        });

        profileBtn.setOnClickListener((v)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, userFragment).commit();
        });

        manualBtn.setOnClickListener((v)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, ethicalGuidelineFragment).commit();
        });

        //Logout
        logoutBtn = view.findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener((v)->{
            FirebaseUtil.logout();
            Intent intent = new Intent(getContext(),SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return view;
    }
}