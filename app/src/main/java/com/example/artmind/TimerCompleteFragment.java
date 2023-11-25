package com.example.artmind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class TimerCompleteFragment extends Fragment {
    private ImageView timerImageView;
    private Button buttonBack;
    TimerFragment timerFragment;

    public TimerCompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_complete, container, false);

        buttonBack = view.findViewById(R.id.button_back);
        timerImageView = view.findViewById(R.id.timer_image_view);
        Glide.with(getActivity()).load(R.drawable.drawing_frog).into(new DrawableImageViewTarget(timerImageView));

        timerFragment = new TimerFragment();
        buttonBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, timerFragment).commit();
        });

        return view;
    }
}