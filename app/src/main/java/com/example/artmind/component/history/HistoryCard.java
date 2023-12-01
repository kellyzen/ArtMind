package com.example.artmind.component.history;

/**
 * History page (card)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class HistoryCard {
    private String historyImage;
    private String historyCategory;
    private String historyPercentage;
    private String historyDesc;

    /**
     * Constructor method for History Card
     *
     * @param imageUri uri of the history image
     * @param category category of the history image (healthy/ unhealthy)
     * @param percentage percentage of the history image (%)
     * @param desc description of the history image (author's name, date, time)
     */
        public HistoryCard(String imageUri, String category, String percentage, String desc) {
        historyImage = imageUri;
        historyCategory = category;
        historyPercentage = percentage;
        historyDesc = desc;
    }

    /**
     * Getter method for history image
     */
    public String getHistoryImage() {
        return historyImage;
    }

    /**
     * Getter method for history category
     */
    public String getHistoryCategory() {
        return historyCategory;
    }

    /**
     * Getter method for history percentage
     */
    public String getHistoryPercentage() {
        return historyPercentage;
    }

    /**
     * Getter method for history description
     */
    public String getHistoryDesc() {
        return historyDesc;
    }
}
