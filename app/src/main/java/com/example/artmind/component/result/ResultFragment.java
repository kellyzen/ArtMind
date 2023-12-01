package com.example.artmind.component.result;

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

import com.example.artmind.R;
import com.example.artmind.component.history.HistoryFragment;
import com.example.artmind.model.ColorAnalysisModel;
import com.example.artmind.model.HistoryModel;
import com.example.artmind.model.SharedViewModel;
import com.example.artmind.utils.AndroidUtil;
import com.example.artmind.utils.FirebaseUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Result page (fragment)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class ResultFragment extends Fragment {
    ImageView resultImage;
    TextView resultCategory;
    TextView resultPercentage;
    TextView resultDesc;
    ProgressBar resultProgress;
    SharedViewModel sharedViewModel;
    HistoryFragment historyFragment;
    String desc;
    String category;
    String image;
    int percentage;

    /**
     * Constructor method for Result Fragment
     */
    public ResultFragment() {
    }

    /**
     * Constructor method for Result Fragment with parameters
     *
     * @param percentage percentage of result image
     * @param category category of result image (healthy, unhealthy)
     * @param desc description of result image (author name, date, time)
     * @param image path of result image
     */
    public ResultFragment(int percentage, String category, String desc, String image) {
        // Set the values directly in the constructor
        this.percentage = percentage;
        this.category = category;
        this.desc = desc;
        this.image = image;
    }

    /**
     * Create view for Result Fragment
     */
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

        if (category != null && desc != null && image != null) {
            setHistoryResult(percentage, category, desc, image);
        } else {
            // Load the selected crop image
            sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            loadImage();

            // Set image description field with authorName | time | date
            String authorName = getAuthorName();
            String time = getCurrentTime();
            String date = getCurrentDate();
            desc = authorName + " | " + time + " | " + date;
            resultDesc.setText(desc);
        }

        historyFragment = new HistoryFragment();
        AndroidUtil.setupOnBackPressed(requireActivity(), historyFragment);
        return view;
    }

    /**
     * Get history result from Firebase and display it in fragment
     */
    private void setHistoryResult(int percentage, String category, String desc, String image) {
        // Set the values to the UI elements
        resultCategory.setText(category);
        resultPercentage.setText(percentage + "%");
        resultDesc.setText(desc);
        resultProgress.setProgress(percentage);

        FirebaseUtil.getHistoryStorageRef().child(image).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        Picasso.get().load(uri).into(resultImage);
                    }
                });
    }

    /**
     * Load image uri
     */
    private void loadImage() {
        sharedViewModel.getCroppedImageUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                Picasso.get().load(uri).into(resultImage);
                setResult(uri);
            }
        });
    }

    /**
     * Retrieve author's name from arguments
     *
     * @param uri uri of the result image from Firebase
     */
    private void setResult(Uri uri) {
        ColorAnalysisModel model = new ColorAnalysisModel();
        model.analyzeImage(getActivity(), uri);
        category = model.getResultCategory();
        percentage = model.getResultPercentage();

        resultCategory.setText(category);
        resultPercentage.setText(percentage + "%");
        resultProgress.setProgress(percentage);

        uploadImageToFirestore(uri);
    }

    /**
     * Upload new result image to Firebase
     *
     * @param uri uri of the result image
     */
    private void uploadImageToFirestore(Uri uri) {
        String fileName = uri.getLastPathSegment();
        FirebaseUtil.getHistoryStorageRef().child(fileName).putFile(uri).addOnCompleteListener(task -> {
        });
        updateDetailsToFirestore(fileName);
    }

    /**
     * Update the history information to Firebase
     *
     * @param filePath path to result image stored in Firebase
     */
    private void updateDetailsToFirestore(String filePath) {
        HistoryModel newHistory = new HistoryModel();
        newHistory.setImagePath(filePath);
        newHistory.setPercentage(percentage);
        newHistory.setCategory(category);
        newHistory.setDesc(desc);

        FirebaseUtil.updateHistory(newHistory);
    }

    /**
     * Getter method for author's name
     *
     * @return String
     */
    private String getAuthorName() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("authorName")) {
            return arguments.getString("authorName", "");
        }
        return "";
    }

    /**
     * Retrieve current time when image uploaded
     */
    private String getCurrentTime() {
        TimeZone timeZone = TimeZone.getTimeZone("MYT");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    /**
     * Retrieve current date when image uploaded in DD/MM/YYYY format
     */
    private String getCurrentDate() {
        TimeZone timeZone = TimeZone.getTimeZone("MYT");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}