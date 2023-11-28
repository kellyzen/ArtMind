package com.example.artmind.model;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Model to store shared view's image
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Uri> croppedImageUri = new MutableLiveData<>();

    /**
     * Setter method for uploaded image uri
     *
     * @param uri uploaded image uri
     */
    public void setCroppedImageUri(Uri uri) {
        croppedImageUri.setValue(uri);
    }

    /**
     * Getter method for uploaded image uri
     *
     * @return  LiveData<Uri>
     */
    public LiveData<Uri> getCroppedImageUri() {
        return croppedImageUri;
    }
}

