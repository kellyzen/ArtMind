package com.example.artmind.model;

import com.example.artmind.model.HistoryModel;

import java.util.List;

public interface HistoryCallback {
    void onHistoryRead(List<HistoryModel> historyModels);
}
