package com.example.artmind.component.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.artmind.R;
import com.example.artmind.component.login.LoginPhoneNumberActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginPhoneNumberActivityTest {
    @Test
    public void testInvalidPhoneNumber() {
        ActivityScenario<LoginPhoneNumberActivity> activityScenario = ActivityScenario.launch(LoginPhoneNumberActivity.class);
        // Type a valid phone number
        onView(ViewMatchers.withId(R.id.login_mobile_number)).perform(typeText("12345678"));

        // Close keyboard
        onView(withId(R.id.login_mobile_number)).perform(closeSoftKeyboard());

        // Click the send OTP button
        onView(withId(R.id.send_otp_btn)).perform(click());

        // Check if the error message is displayed
        assertFalse(hasErrorText("Phone number not valid").matches(onView(withId(R.id.login_mobile_number))));
    }

    @Test
    public void testEmptyPhoneNumber() {
        ActivityScenario<LoginPhoneNumberActivity> activityScenario = ActivityScenario.launch(LoginPhoneNumberActivity.class);

        // Type an invalid phone number
        onView(withId(R.id.login_mobile_number)).perform(typeText("123"));

        // Close keyboard
        onView(withId(R.id.login_mobile_number)).perform(closeSoftKeyboard());

        // Click the send OTP button
        onView(withId(R.id.send_otp_btn)).perform(click());

        // Check if the error message is displayed
        assertFalse(hasErrorText("Cannot be blank!").matches(onView(withId(R.id.login_mobile_number))));

    }
}
