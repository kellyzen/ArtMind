package com.example.artmind.component.settings.user_account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.artmind.R;
import com.example.artmind.component.settings.SettingFragment;
import com.example.artmind.model.UserModel;
import com.example.artmind.utils.AndroidUtil;
import com.example.artmind.utils.FirebaseUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;

/**
 * User Profile page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class UserFragment extends Fragment {
    ImageView profilePic;
    EditText usernameInput;
    EditText phoneInput;
    Button updateProfileBtn;
    ProgressBar progressBar;
    UserModel currentUserModel;
    SettingFragment settingFragment;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    /**
     * Constructor method for User Fragment
     */
    public UserFragment() {
    }

    /**
     * Update profile image on create
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            AndroidUtil.setRoundImage(getContext(), selectedImageUri, profilePic);
                        }
                    }
                }
        );
    }

    /**
     * Create view for User Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        profilePic = view.findViewById(R.id.profile_image_view);
        usernameInput = view.findViewById(R.id.profile_username);
        phoneInput = view.findViewById(R.id.profile_phone);
        updateProfileBtn = view.findViewById(R.id.edit_btn);
        progressBar = view.findViewById(R.id.profile_progress_bar);

        getUserData();

        updateProfileBtn.setOnClickListener((v -> updateBtnClick()));

        profilePic.setOnClickListener((v) -> {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                    .createIntent(intent -> {
                        imagePickLauncher.launch(intent);
                        return null;
                    });
        });
        settingFragment = new SettingFragment();
        AndroidUtil.setupOnBackPressed(requireActivity(), settingFragment);
        return view;
    }

    /**
     * Update user's new information (username, profile picture) to Firebase
     */
    void updateBtnClick() {
        String newUsername = usernameInput.getText().toString();
        // Username input validation
        if (currentUserModel.verifyUsername(newUsername)) {
            usernameInput.setError(String.valueOf(R.string.username_error));
            return;
        }
        // Update the user model
        currentUserModel.setUsername(newUsername);
        AndroidUtil.setInProgress(true, progressBar, updateProfileBtn);

        // If user updated new image, save the new profile pic to Firebase
        if (selectedImageUri != null) {
            FirebaseUtil.getCurrentProfilePicStorageRef().putFile(selectedImageUri)
                    .addOnCompleteListener(task -> {
                        updateToFirestore();
                    });
        } else {
            updateToFirestore();
        }
    }

    /**
     * Update current user information to Firebase
     */
    void updateToFirestore() {
        FirebaseUtil.currentUserDetails().set(currentUserModel)
                .addOnCompleteListener(task -> {
                    AndroidUtil.setInProgress(false, progressBar, updateProfileBtn);
                    if (task.isSuccessful()) {
                        AndroidUtil.showToast(getContext(), "Updated successfully");
                    } else {
                        AndroidUtil.showToast(getContext(), "Updated failed");
                    }
                });
    }


    /**
     * Retrieve user's information from Firebase
     */
    void getUserData() {
        AndroidUtil.setInProgress(true, progressBar, updateProfileBtn);

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        AndroidUtil.setRoundImage(getContext(), uri, profilePic);
                    }
                });

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            AndroidUtil.setInProgress(false, progressBar, updateProfileBtn);
            currentUserModel = task.getResult().toObject(UserModel.class);
            usernameInput.setText(currentUserModel.getUsername());
            phoneInput.setText(currentUserModel.getPhone());
        });
    }
}