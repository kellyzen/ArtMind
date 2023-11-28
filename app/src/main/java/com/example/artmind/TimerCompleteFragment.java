package com.example.artmind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

/**
 * Timer Complete page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class TimerCompleteFragment extends Fragment {
    private ImageView timerImageView;
    private Button buttonBack;
    TimerFragment timerFragment;

    /**
     * Constructor method for Timer Complete Fragment
     */
    public TimerCompleteFragment() {
    }

    /**
     * Create view for Timer Complete Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_complete, container, false);

        buttonBack = view.findViewById(R.id.button_back);
        timerImageView = view.findViewById(R.id.timer_image_view);
        Glide.with(getActivity()).load(R.drawable.drawing_frog).into(new DrawableImageViewTarget(timerImageView));

        // Navigates back to timer page when clicked on back button
        timerFragment = new TimerFragment();
        buttonBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, timerFragment).commit();
        });

        return view;
    }
}