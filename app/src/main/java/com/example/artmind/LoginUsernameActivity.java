package com.example.artmind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.artmind.model.UserModel;
import com.example.artmind.utils.AndroidUtil;
import com.example.artmind.utils.FirebaseUtil;
import com.google.firebase.Timestamp;

/**
 * Login username page (activity)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class LoginUsernameActivity extends AppCompatActivity {

    EditText usernameInput;
    Button letMeInBtn;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;

    /**
     * Create view for Login Username Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);

        usernameInput = findViewById(R.id.login_username);
        letMeInBtn = findViewById(R.id.login_let_me_in_btn);
        progressBar = findViewById(R.id.login_progress_bar);

        phoneNumber = getIntent().getExtras().getString("phone");
        getUsername();

        letMeInBtn.setOnClickListener((v -> {
            setUsername();
        }));
    }

    /**
     * Set/update entered username to Firebase
     */
    void setUsername() {
        String username = usernameInput.getText().toString();
        if (username.isEmpty() || username.length() < 3) {
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }
        AndroidUtil.setInProgress(true, progressBar, letMeInBtn);

        // If user account exist, then use the existing user model
        if (userModel != null) {
            userModel.setUsername(username);
        }
        // if user is new, set new user model
        else {
            userModel = new UserModel(phoneNumber, username, Timestamp.now(), FirebaseUtil.currentUserId(), null);
        }

        // Set the user's information to Firebase
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(task -> {
            AndroidUtil.setInProgress(false, progressBar, letMeInBtn);
            if (task.isSuccessful()) {
                Intent intent = new Intent(LoginUsernameActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    /**
     * Retrieve username from Firebase and set it to input text
     */
    void getUsername() {
        AndroidUtil.setInProgress(true, progressBar, letMeInBtn);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            AndroidUtil.setInProgress(false, progressBar, letMeInBtn);
            if (task.isSuccessful()) {
                userModel = task.getResult().toObject(UserModel.class);
                if (userModel != null) {
                    usernameInput.setText(userModel.getUsername());
                }
            }
        });
    }
}