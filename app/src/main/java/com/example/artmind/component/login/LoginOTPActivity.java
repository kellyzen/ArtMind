package com.example.artmind.component.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.artmind.R;
import com.example.artmind.utils.AndroidUtil;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Login OTP page (activity)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class LoginOTPActivity extends AppCompatActivity {

    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    EditText otpInput;
    Button nextBtn;
    ProgressBar progressBar;
    TextView resendOtpTextView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * Create view for Login OTP Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        otpInput = findViewById(R.id.login_otp);
        nextBtn = findViewById(R.id.login_next_btn);
        progressBar = findViewById(R.id.login_progress_bar);
        resendOtpTextView = findViewById(R.id.resend_otp_textview);

        phoneNumber = getIntent().getExtras().getString("phone");
        sendOtp(phoneNumber, false);

        // Verify whether entered OTP code is valid
        nextBtn.setOnClickListener(v -> {
            String enteredOtp = otpInput.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enteredOtp);
            signIn(credential);
        });

        // Resend OTP if resend otp text view is clicked
        resendOtpTextView.setOnClickListener((v) -> {
            sendOtp(phoneNumber, true);
        });
    }

    /**
     * Send OTP to mobile phone through messages
     */
    void sendOtp(String phoneNumber, boolean isResend) {
        startResendTimer();
        AndroidUtil.setInProgress(true, progressBar, nextBtn);

        // Add underlines to resend otp text view
        resendOtpTextView.setPaintFlags(resendOtpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Send and verify OTP code
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            // If entered OTP is valid, then sign in the user
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                AndroidUtil.setInProgress(false, progressBar, nextBtn);
                            }

                            // If entered OTP is invalid, then pops out error message
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext(), String.valueOf(R.string.otp_fail));
                                AndroidUtil.setInProgress(false, progressBar, nextBtn);
                            }

                            // Pops out confirmation message when OTP code is sent
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtil.showToast(getApplicationContext(), String.valueOf(R.string.otp_success));
                                AndroidUtil.setInProgress(false, progressBar, nextBtn);
                            }
                        });
        // Resend OTP token
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    /**
     * Authenticate user in Firebase and navigate to login username page
     *
     * @param phoneAuthCredential phone authentication credential
     */
    void signIn(PhoneAuthCredential phoneAuthCredential) {
        //login and go to next activity
        AndroidUtil.setInProgress(true, progressBar, nextBtn);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            AndroidUtil.setInProgress(false, progressBar, nextBtn);
            if (task.isSuccessful()) {
                // Navigate to login username page
                Intent intent = new Intent(LoginOTPActivity.this, LoginUsernameActivity.class);
                intent.putExtra("phone", phoneNumber);
                startActivity(intent);
            } else {
                // Pops out error message
                AndroidUtil.showToast(getApplicationContext(), "OTP verification failed");
            }
        });
    }

    /**
     * Start the timer to resent OTP code
     */
    void startResendTimer() {
        resendOtpTextView.setEnabled(false);
        Timer timer = new Timer();
        // Schedule fixed countdown timer
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText(getResources().getString(R.string.resend_otp2) + timeoutSeconds + "s");
                if (timeoutSeconds <= 0) {
                    timeoutSeconds = 60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resendOtpTextView.setText(getResources().getString(R.string.resend_otp_click));
                        resendOtpTextView.setEnabled(true);
                    });
                }
            }
        }, 0, 1000);
    }
}