package com.example.artmind;

import static androidx.test.espresso.Espresso.onView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.artmind.component.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    //Test if bottom navigation menu loads the correct fragment
    @Test
    public void testNavigationMenu() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(ViewMatchers.withId(R.id.menu_setting)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.fragment_setting)).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
        );
    }
}
