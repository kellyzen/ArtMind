package com.example.artmind.model;

import com.google.firebase.Timestamp;

import java.util.List;

/**
 * Model to store user's information
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class UserModel {
    private String phone;
    private String username;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;
    private List<HistoryModel> history;

    /**
     * Constructor for UserModel class
     */
    public UserModel() {
    }

    /**
     * Constructor for UserModel class with parameters
     *
     * @param phone            user's phone number
     * @param username         user's current username
     * @param createdTimestamp timestamp where the user's account is created
     * @param userId           user's ID
     * @param history          user's list of history
     */
    public UserModel(String phone, String username, Timestamp createdTimestamp, String userId, List<HistoryModel> history) {
        this.phone = phone;
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.history = history;
    }

    /**
     * Getter method for phone
     *
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for phone
     *
     * @param phone user's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter method for created time stamp
     *
     * @return String
     */
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * Setter method for created timestamp
     *
     * @param createdTimestamp timestamp when user's account is created
     */
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * Getter method for user id
     *
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for user ID
     *
     * @param userId user's ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for fcm token
     *
     * @return String
     */
    public String getFcmToken() {
        return fcmToken;
    }

    /**
     * Setter method for fcm token
     *
     * @param fcmToken user's fcm token
     */
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    /**
     * Getter method for username
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for username
     *
     * @param username user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for history list
     *
     * @return List<HistoryModel>
     */
    public List<HistoryModel> getHistory() {
        return history;
    }

    /**
     * Setter method for history
     *
     * @param history list of history model
     */
    public void setHistory(List<HistoryModel> history) {
        this.history = history;
    }
}
