package com.example.artmind.component.focus_timer;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.artmind.R;
import com.example.artmind.model.TimerModel;

import java.util.Locale;

/**
 * Helper to display countdown timer in notification
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class TimerNotificationHelper {

    private static final int NOTIFICATION_ID = 1;
    private static final int PERMISSION_REQUEST_VIBRATE = 1;

    private final Context context;
    private final NotificationManagerCompat notificationManager;

    /**
     * Constructor for TimerNotificationHelper
     */
    public TimerNotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);

        createNotificationChannel();
    }

    /**
     * Create notification channel with their id and name
     */
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Update notification timer
     *
     * @param millisUntilFinished time left until timer is finished (millis)
     */
    public void updateNotification(long millisUntilFinished) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Timer Countdown")
                .setContentText(convertMillisToTimerFormat(millisUntilFinished))
                .setPriority(NotificationCompat.PRIORITY_LOW);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            requestVibratePermission();
        }
    }

    /**
     * Show notification with title and content
     *
     * @param title   title of the notification
     * @param content content of the notification
     */
    public void showNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            requestVibratePermission();
        }
    }

    /**
     * Request notification permission through vibrate
     */
    private void requestVibratePermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.VIBRATE}, PERMISSION_REQUEST_VIBRATE);
    }

    /**
     * Convert milliseconds to hours, minutes, seconds
     *
     * @param millis timer (millis)
     * @return String
     */
    private String convertMillisToTimerFormat(long millis) {
        int hours = (int) (millis / 1000) / 3600;
        int minutes = (int) ((millis / 1000) % 3600) / 60;
        int seconds = (int) (millis / 1000) % 60;

        if (hours > 0) {
            return String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
    }

    /**
     * Pause the notification
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}


