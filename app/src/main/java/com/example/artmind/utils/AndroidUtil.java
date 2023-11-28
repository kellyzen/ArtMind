package com.example.artmind.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
    public static void setRoundImage(Context context, Uri imageUri, ImageView imageView){
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
}
