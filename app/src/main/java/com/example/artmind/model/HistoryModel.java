package com.example.artmind.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Model to store history's information
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class HistoryModel {
    private String imagePath;
    private int percentage;
    private String category;
    private String desc;

    /**
     * Map all the history string from database into model object
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("imagePath", imagePath);
        map.put("percentage", percentage);
        map.put("category", category);
        map.put("desc", desc);
        return map;
    }

    /**
     * Map all the history model object to HistoryModel
     *
     * @return HistoryModel
     */
    public static HistoryModel fromMap(Map<String, Object> map) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.setImagePath((String) map.get("imagePath"));
        historyModel.setPercentage(((Long) map.get("percentage")).intValue());
        historyModel.setCategory((String) map.get("category"));
        historyModel.setDesc((String) map.get("desc"));
        return historyModel;
    }

    /**
     * Getter method for image path
     *
     * @return String
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Setter method for image path
     *
     * @param imagePath image path of the result image from Firebase
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Getter method for percentage
     *
     * @return int
     */
    public int getPercentage() {
        return percentage;
    }

    /**
     * Setter method for percentage
     *
     * @param percentage percentage of the result image from Firebase
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    /**
     * Getter method for category
     *
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter method for category
     *
     * @param category category of the result image from Firebase
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter method for description
     *
     * @return String
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for description
     *
     * @param desc description of the result image from Firebase
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
