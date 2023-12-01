package com.example.artmind.component.focus_timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.artmind.R;
import com.example.artmind.component.settings.SettingFragment;
import com.example.artmind.model.TimerModel;
import com.example.artmind.utils.AndroidUtil;

/**
 * Timer Complete page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class TimerCompleteFragment extends Fragment {
    private ImageView timerImageView;
    private Button buttonBack;
    private TextView completeTxt;
    private long focusTime;
    private TimerModel timerModel;
    TimerFragment timerFragment;

    /**
     * Constructor method for Timer Complete Fragment
     */
    public TimerCompleteFragment(long time) {
        focusTime = time;
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
        completeTxt = view.findViewById(R.id.timer_complete_msg);
        timerModel = new TimerModel();
        Glide.with(getActivity()).load(R.drawable.drawing_frog).into(new DrawableImageViewTarget(timerImageView));

        // Set congratulatory message
        String convertedTime = timerModel.convertTimer(focusTime);
        System.out.println(focusTime);
        System.out.println("Converted: " + convertedTime);
        completeTxt.setText("Congratulations! You have focused for " + convertedTime + " mins");

        // Navigates back to timer page when clicked on back button
        timerFragment = new TimerFragment();
        buttonBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, timerFragment).commit();
        });

        AndroidUtil.setupOnBackPressed(requireActivity(), timerFragment);
        return view;
    }
}