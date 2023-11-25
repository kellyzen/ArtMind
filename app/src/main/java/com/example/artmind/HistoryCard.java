package com.example.artmind;

public class HistoryCard {
    private String historyImage;
    private String historyCategory;
    private String historyPercentage;
    private String historyDesc;

        public HistoryCard(String imageUri, String category, String percentage, String desc) {
        historyImage = imageUri;
        historyCategory = category;
        historyPercentage = percentage;
        historyDesc = desc;
    }

    public String getHistoryImage() {
        return historyImage;
    }

    public String getHistoryCategory() {
        return historyCategory;
    }

    public String getHistoryPercentage() {
        return historyPercentage;
    }

    public String getHistoryDesc() {
        return historyDesc;
    }
}
