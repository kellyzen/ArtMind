package com.example.artmind.model;

import java.util.HashMap;
import java.util.Map;

public class HistoryModel {
    private String imagePath;
    private int percentage;
    private String category;
    private String desc;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("imagePath", imagePath);
        map.put("percentage", percentage);
        map.put("category", category);
        map.put("desc", desc);
        return map;
    }

    public static HistoryModel fromMap(Map<String, Object> map) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.setImagePath((String) map.get("imagePath"));
        historyModel.setPercentage(((Long) map.get("percentage")).intValue());
        historyModel.setCategory((String) map.get("category"));
        historyModel.setDesc((String) map.get("desc"));
        return historyModel;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
