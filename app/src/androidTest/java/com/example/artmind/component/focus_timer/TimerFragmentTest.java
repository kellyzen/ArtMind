package com.example.artmind.component.focus_timer;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.artmind.R;
import com.example.artmind.component.MainActivity;

@RunWith(AndroidJUnit4.class)
public class TimerFragmentTest {

    @Test
    public void setTimer_updatesCountdownText() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.menu_timer)).perform(ViewActions.click());

        // Type the timer value into the edit text
        Espresso.onView(ViewMatchers.withId(R.id.edit_text_input))
                .perform(ViewActions.typeText("1"));
        Espresso.onView(withId(R.id.edit_text_input)).perform(closeSoftKeyboard());

        // Click the "Set" button
        Espresso.onView(withId(R.id.button_set)).perform(ViewActions.click());

        // Check if the countdown text is displayed
        Espresso.onView(withId(R.id.text_view_countdown)).check(matches(isDisplayed()));
    }

    @Test
    public void resetTimer_updatesCountdownText() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.menu_timer)).perform(ViewActions.click());

        // Type the timer value into the edit text
        Espresso.onView(withId(R.id.edit_text_input))
                .perform(ViewActions.typeText("1"));
        Espresso.onView(withId(R.id.edit_text_input)).perform(closeSoftKeyboard());

        // Click button
        Espresso.onView(withId(R.id.button_set)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.button_start_pause)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.button_start_pause)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.button_reset)).perform(ViewActions.click());

        // Check if the countdown text is displayed
        Espresso.onView(withId(R.id.text_view_countdown)).check(matches(isDisplayed()));
    }
}
