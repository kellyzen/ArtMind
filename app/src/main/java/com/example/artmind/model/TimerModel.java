package com.example.artmind.model;

import java.util.Locale;

/**
 * Model to store timer's information
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class TimerModel implements Timer{
    private boolean timerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis;
    private long endTime;

    /**
     * Constructor for TimerModel class
     */
    public TimerModel() {
    }

    /**
     * Convert milliseconds to hours, minutes, seconds
     *
     * @param timeLeft time left (millis) to complete the timer
     * @return String
     */
    public String convertTimer(long timeLeft) {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        if (hours > 0) {
            return String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
    }

    /**
     * Getter method for time running
     *
     * @return boolean
     */
    public boolean isTimerRunning() {
        return timerRunning;
    }

    /**
     * Setter method for timer running\
     *
     * @param timerRunning true/false is timer running
     */
    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    /**
     * Getter method for start time (millis)
     *
     * @return long
     */
    public long getStartTimeInMillis() {
        return startTimeInMillis;
    }

    /**
     * Setter method for start time (millis)
     *
     * @param startTimeInMillis start time
     */
    public void setStartTimeInMillis(long startTimeInMillis) {
        this.startTimeInMillis = startTimeInMillis;
    }

    /**
     * Getter method for time left (millis)
     *
     * @return long
     */
    public long getTimeLeftInMillis() {
        return timeLeftInMillis;
    }

    /**
     * Setter method for time left (millis)
     *
     * @param timeLeftInMillis time left
     */
    public void setTimeLeftInMillis(long timeLeftInMillis) {
        this.timeLeftInMillis = timeLeftInMillis;
    }

    /**
     * Getter method for end time (millis)
     *
     * @return long
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Setter method for end time (millis)
     *
     * @param endTime end time
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
