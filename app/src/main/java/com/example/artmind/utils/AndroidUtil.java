package com.example.artmind.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.artmind.R;

/**
 * Utility methods to repeated public functions
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class AndroidUtil {
    /**
     * Pops out toast message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Set image to round shape
     */
    public static void setRoundImage(Context context, Uri imageUri, ImageView imageView) {
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    /**
     * Set visibility for progress icon when updating
     */
    public static void setInProgress(boolean inProgress, ProgressBar progressBar, Button button) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Quit the app when back button is pressed
     */
    public static void setupOnQuitPressed(FragmentActivity requireActivity) {
        requireActivity.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isEnabled()) {
                    setEnabled(false);
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Loads another fragment when back button is pressed
     */
    public static void setupOnBackPressed(FragmentActivity requireActivity, Fragment fragment) {
        requireActivity.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isEnabled()) {
                    setEnabled(false);

                    // Navigate to another fragment here
                    FragmentManager fragmentManager = requireActivity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
                }
            }
        });
    }
}
