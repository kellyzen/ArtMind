package com.example.artmind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.artmind.utils.FirebaseUtil;

/**
 * Splash page (activity)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Create view for Splash Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // If device is logged in, load main activity page
            if (FirebaseUtil.isLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            // If device is not logged in, load login activity page
            else {
                startActivity(new Intent(SplashActivity.this, LoginPhoneNumberActivity.class));
            }

            finish();
        }, 1000);
    }
}