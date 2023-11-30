package com.example.artmind;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SettingFragmentTest {

    //Test if button click loads the correct fragment
    @Test
    public void testNavigationToAboutFragment() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.menu_setting)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.menu_about)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_about)).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
        );
    }

    @Test
    public void testNavigationToUserProfileFragment() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.menu_setting)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.menu_profile)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_profile)).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
        );
    }

    @Test
    public void testNavigationToEthicalGuidelineFragment() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.menu_setting)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.menu_user_manual)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_guideline)).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
        );
    }
}

