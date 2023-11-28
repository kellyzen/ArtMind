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

/**
 * Utility methods to call Firebase
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class FirebaseUtil {
    /**
     * Get current user's ID from Firebase
     *
     * @return String
     */
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    /**
     * Determine whether the user logged in already
     *
     * @return boolean
     */
    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    /**
     * Sign the user out from the app
     */
    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Get current user's detail (userID, username, phone etc.) from Firebase
     *
     * @return DocumentReference
     */
    public static DocumentReference currentUserDetails() {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    /**
     * Get current user's profile picture from Firebase
     *
     * @return StorageReference
     */
    public static StorageReference getCurrentProfilePicStorageRef() {
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.currentUserId());
    }

    /**
     * Get current user's path to history folder from Firebase
     *
     * @return StorageReference
     */
    public static StorageReference getHistoryStorageRef() {
        return FirebaseStorage.getInstance().getReference().child("history")
                .child(FirebaseUtil.currentUserId());
    }

    /**
     * Update new history to Firebase
     */
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

    /**
     * Read the list of history from Firebase
     */
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

                    // Set the list of historyModel to userModel
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

