package com.example.artmind.model;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Uri> croppedImageUri = new MutableLiveData<>();

    public void setCroppedImageUri(Uri uri) {
        croppedImageUri.setValue(uri);
    }

    public LiveData<Uri> getCroppedImageUri() {
        return croppedImageUri;
    }
}

