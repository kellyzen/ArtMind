package com.example.artmind;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.artmind.model.SharedViewModel;
import com.example.artmind.model.UserModel;
import com.example.artmind.utils.AndroidUtil;
import com.example.artmind.utils.FirebaseUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ScanFragment extends Fragment {
    Button scanBtn;
    EditText authorInput;
    SharedViewModel sharedViewModel;
    ResultFragment resultFragment;
    UserModel userModel;
    private static final int REQUEST_CAMERA_CODE = 100;

    public ScanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        scanBtn = view.findViewById(R.id.scan_btn);
        authorInput = view.findViewById(R.id.author_input);
        resultFragment = new ResultFragment();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Set username as author's name
        getUsername();

        // Request android camera's permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
        }

        scanBtn.setOnClickListener(v -> startCropActivity());

        // Inflate the layout for this fragment
        return view;
    }

    // Call this method to start the cropping activity
    private void startCropActivity() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(getContext(), this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (writeStorageAccepted) {
                    startCropActivity();
                } else {
                    AndroidUtil.showToast(getActivity(), "Please Enable Storage Permission");
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                // Set the cropped image URI to the ViewModel
                sharedViewModel.setCroppedImageUri(resultUri);

                // Navigate to ResultFragment
                navigateToResultFragment();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("CropError", "Error during image cropping", error);
            }
        }
    }

    private void navigateToResultFragment() {
        String authorName = authorInput.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("authorName", authorName);
        resultFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, resultFragment).commit();
    }

    private void getUsername() {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userModel = task.getResult().toObject(UserModel.class);
                if (userModel != null) {
                    authorInput.setText(userModel.getUsername());
                }
            }
        });
    }

}