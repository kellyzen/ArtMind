package com.example.artmind.utils;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.example.artmind.model.HistoryCallback;
import com.example.artmind.model.HistoryModel;
import com.example.artmind.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FirebaseUtil {
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static DocumentReference currentUserDetails() {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static StorageReference getCurrentProfilePicStorageRef() {
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.currentUserId());
    }

    public static StorageReference getHistoryStorageRef() {
        return FirebaseStorage.getInstance().getReference().child("history")
                .child(FirebaseUtil.currentUserId());
    }

    public static void updateHistory(HistoryModel history) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(currentUserId());

        // Update the user document with a new history item
        userRef.update("history", FieldValue.arrayUnion(history.toMap()))
                .addOnSuccessListener(aVoid -> {
                    // Successfully added history to the user document
                    System.out.println("Success: " + aVoid);
                })
                .addOnFailureListener(e -> {
                    // Handle the failure to add history
                    Log.e(TAG, "Error.", e);
                });
    }

    public static void readHistory(HistoryCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(currentUserId());

        // Listen for changes in the user document
        userRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                // Handle errors
                Log.e(TAG, "Listen failed.", e);
                callback.onHistoryRead(Collections.emptyList()); // Notify callback with empty list
                return;
            }

            List<HistoryModel> historyModels = new ArrayList<>();

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Get the history array from the document
                List<Map<String, Object>> historyList = (List<Map<String, Object>>) documentSnapshot.get("history");

                if (historyList != null) {
                    // Convert each map to HistoryModel
                    for (Map<String, Object> historyMap : historyList) {
                        HistoryModel historyModel = HistoryModel.fromMap(historyMap);
                        historyModels.add(historyModel);
                    }

                    currentUserDetails().get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserModel userModel;
                            userModel = task.getResult().toObject(UserModel.class);
                            if (userModel != null) {
                                userModel.setHistory(historyModels);
                            }
                        }
                    });
                }
            }

            // Notify the callback with the result
            callback.onHistoryRead(historyModels);
        });
    }

}

