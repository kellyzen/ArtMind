package com.example.artmind.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TimerModelTest {
    private TimerModel timerModel;

    @Before
    public void setUp() {
        timerModel = new TimerModel();
    }

    @Test
    public void convertTimer_withHours_returnsFormattedString() {
        long timeLeft = 3661000; // 1 hour, 1 minute, 1 second
        String expected = "1:01:01";

        String result = timerModel.convertTimer(timeLeft);

        assertEquals(expected, result);
    }

    @Test
    public void convertTimer_withoutHours_returnsFormattedString() {
        long timeLeft = 61000; // 1 minute, 1 second
        String expected = "01:01";

        String result = timerModel.convertTimer(timeLeft);

        assertEquals(expected, result);
    }

    @Test
    public void isTimerRunning_defaultValueIsFalse() {
        assertFalse(timerModel.isTimerRunning());
    }

    @Test
    public void setTimerRunning_updatesTimerRunningValue() {
        timerModel.setTimerRunning(true);

        assertTrue(timerModel.isTimerRunning());
    }

    @Test
    public void getStartTimeInMillis_defaultValueIsZero() {
        assertEquals(0, timerModel.getStartTimeInMillis());
    }

    @Test
    public void setStartTimeInMillis_updatesStartTimeInMillis() {
        long startTime = System.currentTimeMillis();
        timerModel.setStartTimeInMillis(startTime);

        assertEquals(startTime, timerModel.getStartTimeInMillis());
    }

    @Test
    public void getTimeLeftInMillis_defaultValueIsZero() {
        assertEquals(0, timerModel.getTimeLeftInMillis());
    }

    @Test
    public void setTimeLeftInMillis_updatesTimeLeftInMillis() {
        long timeLeft = 60000; // 1 minute
        timerModel.setTimeLeftInMillis(timeLeft);

        assertEquals(timeLeft, timerModel.getTimeLeftInMillis());
    }

    @Test
    public void getEndTime_defaultValueIsZero() {
        assertEquals(0, timerModel.getEndTime());
    }

    @Test
    public void setEndTime_updatesEndTime() {
        long endTime = System.currentTimeMillis() + 60000; // 1 minute from now
        timerModel.setEndTime(endTime);

        assertEquals(endTime, timerModel.getEndTime());
    }
}

