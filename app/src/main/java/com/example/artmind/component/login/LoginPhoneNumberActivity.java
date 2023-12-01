package com.example.artmind.component.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.artmind.R;
import com.hbb20.CountryCodePicker;

/**
 * Login Phone Number page (activity)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class LoginPhoneNumberActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phoneInput;
    Button sendOtpBtn;
    ProgressBar progressBar;

    /**
     * Create view for Login OTP Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);

        countryCodePicker = findViewById(R.id.login_countrycode);
        phoneInput = findViewById(R.id.login_mobile_number);
        sendOtpBtn = findViewById(R.id.send_otp_btn);
        progressBar = findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.GONE);

        // Get country code picker
        countryCodePicker.registerCarrierNumberEditText(phoneInput);

        sendOtpBtn.setOnClickListener((v) -> {
            // If entered phone number is invalid, pop out error message
            if (!countryCodePicker.isValidFullNumber()) {
                phoneInput.setError("Phone number not valid");
                return;
            }
            // If entered phone number is valid, navigate to Login OTP page
            Intent intent = new Intent(LoginPhoneNumberActivity.this, LoginOTPActivity.class);
            intent.putExtra("phone", countryCodePicker.getFullNumberWithPlus());
            startActivity(intent);
        });
    }
}