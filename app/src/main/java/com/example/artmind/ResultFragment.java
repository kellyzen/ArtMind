package com.example.artmind;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.artmind.model.ColorAnalysisModel;
import com.example.artmind.model.SharedViewModel;
import com.example.artmind.utils.AndroidUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ResultFragment extends Fragment {
    ImageView resultImage;
    TextView resultCategory;
    TextView resultPercentage;
    TextView resultDesc;
    ProgressBar resultProgress;
    SharedViewModel sharedViewModel;

    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        resultImage = view.findViewById(R.id.result_image);
        resultCategory = view.findViewById(R.id.result_category);
        resultPercentage = view.findViewById(R.id.result_percentage);
        resultProgress = view.findViewById(R.id.result_progress);
        resultDesc = view.findViewById(R.id.result_desc);

//        // Get ML prediction from Roboflow
//        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        uri = task.getResult();
//                        RoboflowApi.getPrediction(uri);
//                    }
//                });

        //Load the selected crop image
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        loadImage();

        //Set image description field with authorName | time | date
        String authorName = getAuthorName();
        String time = getCurrentTime();
        String date = getCurrentDate();
        String desc = authorName + " | " + time + " | " + date;
        resultDesc.setText(desc);

        return view;
    }

    // Load image URI
    private void loadImage() {
        sharedViewModel.getCroppedImageUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                Picasso.get().load(uri).into(resultImage);
                setResult(uri);
            }
        });
    }

    // Retrieve author's name from arguments
    private void setResult(Uri uri) {
        ColorAnalysisModel model = new ColorAnalysisModel();
        model.analyzeImage(getActivity(), uri);
        String category = model.getResultCategory();
        String color = model.getResultColor();
        int percentage = model.getResultPercentage();

        resultCategory.setText(category);
        resultPercentage.setText(percentage + "%");
        resultProgress.setProgress(percentage);
        AndroidUtil.showToast(getActivity(), color);// can delete
    }

    // Retrieve author's name from arguments
    private String getAuthorName() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("authorName")) {
            return arguments.getString("authorName", "");
        }
        return "";
    }

    // Get current time in HH:MM:SS format
    private String getCurrentTime() {
        TimeZone timeZone = TimeZone.getTimeZone("MYT");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    // Get current date in DD/MM/YYYY format
    private String getCurrentDate() {
        TimeZone timeZone = TimeZone.getTimeZone("MYT");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}