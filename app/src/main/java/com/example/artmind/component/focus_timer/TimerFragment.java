package com.example.artmind.component.focus_timer;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.artmind.R;
import com.example.artmind.model.MusicPlayerModel;
import com.example.artmind.model.TimerModel;
import com.example.artmind.utils.AndroidUtil;

/**
 * Focus Timer page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class TimerFragment extends Fragment {
    private EditText editTextInput;
    private TextView textViewCountDown;
    private Button buttonSet;
    private Button buttonStartPause;
    private Button buttonReset;
    private ImageView timerImageView;
    private CountDownTimer countDownTimer;
    TimerCompleteFragment timerCompleteFragment;
    private MusicPlayerModel musicPlayer;
    private TimerModel timerModel;
    private TimerNotificationHelper notificationHelper;
    private boolean isMuted = false;

    /**
     * Constructor method for Timer Fragment
     */
    public TimerFragment() {
    }

    /**
     * Create view for Timer Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        AndroidUtil.setupOnQuitPressed(requireActivity());
        return view;
    }

    /**
     * Add functions when fragment view is created
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        editTextInput = getView().findViewById(R.id.edit_text_input);
        textViewCountDown = getView().findViewById(R.id.text_view_countdown);
        timerImageView = getView().findViewById(R.id.timer_image_view);
        buttonSet = getView().findViewById(R.id.button_set);
        buttonStartPause = getView().findViewById(R.id.button_start_pause);
        buttonReset = getView().findViewById(R.id.button_reset);
        musicPlayer = new MusicPlayerModel(getActivity(), R.raw.timer_bg_music);
        timerModel = new TimerModel();
        notificationHelper = new TimerNotificationHelper(requireContext());

        // Load frog image gif
        Glide.with(getActivity()).load(R.drawable.drawing_frog).into(new DrawableImageViewTarget(timerImageView));

        // Set timer in minutes
        buttonSet.setOnClickListener(v -> {
            String input = editTextInput.getText().toString();
            if (input.length() == 0) {
                AndroidUtil.showToast(getActivity(), "Field can't be empty");
                return;
            }

            long millisInput = Long.parseLong(input) * 60000;
            if (millisInput == 0) {
                AndroidUtil.showToast(getActivity(), "Please enter a positive number");
                return;
            }

            setTime(millisInput);
            editTextInput.setText("");
        });

        // Start or pause timer
        buttonStartPause.setOnClickListener(v -> {
            if (timerModel.isTimerRunning()) {
                pauseTimer();
            } else {
                startTimer();
                if (!isMuted) {
                    musicPlayer.startMusic();
                }
            }
        });

        // Reset timer or Mute/Unmute music
        buttonReset.setOnClickListener(v -> {
            if (timerModel.isTimerRunning()) {
                // If the timer is running, toggle mute/unmute
                isMuted = musicPlayer.toggleMute(buttonReset, isMuted);
            } else {
                // If the timer is not running, perform reset
                resetTimer();
            }
        });
    }

    /**
     * Set countdown timer in minutes
     */
    private void setTime(long milliseconds) {
        timerModel.setStartTimeInMillis(milliseconds);
        resetTimer();
        closeKeyboard();
    }

    /**
     * Start countdown timer
     */
    private void startTimer() {
        timerModel.setEndTime(System.currentTimeMillis() + timerModel.getTimeLeftInMillis());

        countDownTimer = new CountDownTimer(timerModel.getTimeLeftInMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerModel.setTimeLeftInMillis(millisUntilFinished);
                updateCountDownText();
                notificationHelper.updateNotification(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerModel.setTimerRunning(false);
                updateTimerInterface();
                notificationHelper.showNotification("Timer Finished", "Your countdown timer has finished!");
            }
        }.start();

        timerModel.setTimerRunning(true);
        updateTimerInterface();
    }

    /**
     * Pause countdown timer
     */
    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            notificationHelper.cancelNotification();
        }
        timerModel.setTimerRunning(false);
        updateTimerInterface();
        musicPlayer.pauseMusic();
    }


    /**
     * Reset countdown timer
     */
    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            notificationHelper.cancelNotification();
        }
        timerModel.setTimeLeftInMillis(timerModel.getStartTimeInMillis());
        updateCountDownText();
        updateTimerInterface();
    }

    /**
     * Continuously update countdown timer
     */
    private void updateCountDownText() {
        textViewCountDown.setText(timerModel.convertTimer(timerModel.getTimeLeftInMillis()));
    }

    /**
     * Continuously set the updated countdown timer on interface
     */
    private void updateTimerInterface() {
        if (timerModel.isTimerRunning()) {
            editTextInput.setVisibility(View.INVISIBLE);
            buttonSet.setVisibility(View.INVISIBLE);
            buttonStartPause.setText("Pause");
            buttonReset.setVisibility(View.VISIBLE);
            if (isMuted) {
                buttonReset.setText("Unmute");
            } else {
                buttonReset.setText("Mute");
            }
        } else {
            editTextInput.setVisibility(View.VISIBLE);
            buttonSet.setVisibility(View.VISIBLE);
            buttonStartPause.setText("Start");

            if (timerModel.getTimeLeftInMillis() < 1000) {
                resetTimer();
                timerCompleteFragment = new TimerCompleteFragment(timerModel.getStartTimeInMillis());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, timerCompleteFragment).commit();
            } else {
                buttonStartPause.setVisibility(View.VISIBLE);
            }

            if (timerModel.getTimeLeftInMillis() < timerModel.getStartTimeInMillis()) {
                buttonReset.setVisibility(View.VISIBLE);
                buttonReset.setText("Reset");
            } else {
                buttonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Close keyboard when finished typing
     */
    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Update the timer interface when timer is stopped and save in Shared Preferences
     */
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", timerModel.getStartTimeInMillis());
        editor.putLong("millisLeft", timerModel.getTimeLeftInMillis());
        editor.putBoolean("timerRunning", timerModel.isTimerRunning());
        editor.putLong("endTime", timerModel.getEndTime());

        editor.apply();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        // Stop the music when the fragment is stopped
        musicPlayer.stopMusic();
    }

    /**
     * Update the timer interface when timer is started from Shared Preferences
     */
    @Override
    public void onStart() {
        super.onStart();

        // Save the time left in SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

        timerModel.setStartTimeInMillis(prefs.getLong("startTimeInMillis", 600000));
        timerModel.setTimeLeftInMillis(prefs.getLong("millisLeft", timerModel.getStartTimeInMillis()));
        timerModel.setTimerRunning(prefs.getBoolean("timerRunning", false));

        updateCountDownText();
        updateTimerInterface();

        if (timerModel.isTimerRunning()) {
            timerModel.setEndTime(prefs.getLong("endTime", 0));
            timerModel.setTimeLeftInMillis(timerModel.getEndTime() - System.currentTimeMillis());

            if (timerModel.getTimeLeftInMillis() < 0) {
                timerModel.setTimeLeftInMillis(0);
                timerModel.setTimerRunning(false);
                updateCountDownText();
                updateTimerInterface();
            } else {
                startTimer();
            }
        }
    }


    /**
     * Destroy the timer when user navigates to another page
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.stopMusic();
    }
}