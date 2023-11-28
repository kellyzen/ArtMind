package com.example.artmind.model;

import java.util.List;

/**
 * Interface to callback History Model
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public interface HistoryCallback {
    void onHistoryRead(List<HistoryModel> historyModels);
}
