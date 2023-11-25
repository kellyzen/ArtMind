package com.example.artmind;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.artmind.utils.AndroidUtil;

import java.util.Locale;

public class TimerFragment extends Fragment {
    private EditText editTextInput;
    private TextView textViewCountDown;
    private Button buttonSet;
    private Button buttonStartPause;
    private Button buttonReset;
    private ImageView timerImageView;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis;
    private long endTime;
    private NotificationManagerCompat notificationManager;
    private static final int NOTIFICATION_ID = 1;
    TimerCompleteFragment timerCompleteFragment;
    private final Handler handler = new Handler();
    private Runnable timerRunnable;
    private static final int PERMISSION_REQUEST_VIBRATE = 1;


    public TimerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        editTextInput = getView().findViewById(R.id.edit_text_input);
        textViewCountDown = getView().findViewById(R.id.text_view_countdown);
        timerImageView = getView().findViewById(R.id.timer_image_view);
        buttonSet = getView().findViewById(R.id.button_set);
        buttonStartPause = getView().findViewById(R.id.button_start_pause);
        buttonReset = getView().findViewById(R.id.button_reset);

        Glide.with(getActivity()).load(R.drawable.drawing_frog).into(new DrawableImageViewTarget(timerImageView));

        notificationManager = NotificationManagerCompat.from(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

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

        buttonStartPause.setOnClickListener(v -> {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
                startNotificationTimer();
            }
        });

        buttonReset.setOnClickListener(v -> resetTimer());
    }

    private void setTime(long milliseconds) {
        startTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                updateTimerInterface();
            }
        }.start();

        timerRunning = true;
        updateTimerInterface();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        updateTimerInterface();
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void resetTimer() {
        timeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        updateTimerInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        textViewCountDown.setText(timeLeftFormatted);
    }

    private void updateTimerInterface() {
        if (timerRunning) {
            editTextInput.setVisibility(View.INVISIBLE);
            buttonSet.setVisibility(View.INVISIBLE);
            buttonReset.setVisibility(View.INVISIBLE);
            buttonStartPause.setText("Pause");
        } else {
            editTextInput.setVisibility(View.VISIBLE);
            buttonSet.setVisibility(View.VISIBLE);
            buttonStartPause.setText("Start");

            if (timeLeftInMillis < 1000) {
                resetTimer();
                timerCompleteFragment = new TimerCompleteFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, timerCompleteFragment).commit();
            } else {
                buttonStartPause.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis < startTimeInMillis) {
                buttonReset.setVisibility(View.VISIBLE);
            } else {
                buttonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void startNotificationTimer() {
        new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the notification with the remaining time
                updateNotification((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // Notification timer finished, perform additional actions here
                showNotification("Timer Finished", "Your countdown timer has finished!");
            }
        }.start();
    }

    private void updateNotification(int secondsLeft) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Timer Countdown")
                .setContentText(String.format(Locale.getDefault(), "Time remaining: %d seconds", secondsLeft))
                .setPriority(NotificationCompat.PRIORITY_LOW);

        // Check if the app has the FOREGROUND_SERVICE permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.VIBRATE)
                == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            // Request the FOREGROUND_SERVICE permission if not granted
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.VIBRATE},
                    123);
        }
    }

    private void showNotification(String title, String content) {
        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        // Check for permission before notifying
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Request the VIBRATE permission if not granted
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.VIBRATE}, PERMISSION_REQUEST_VIBRATE);
            return;
        }


        // Notify using the notification manager
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", startTimeInMillis);
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);

        editor.apply();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

        startTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        timeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateTimerInterface();

        if (timerRunning) {
            endTime = prefs.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
                timerRunning = false;
                updateCountDownText();
                updateTimerInterface();
            } else {
                startTimer();
            }
        }
    }
}