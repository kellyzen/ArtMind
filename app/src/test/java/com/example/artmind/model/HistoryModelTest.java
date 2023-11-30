package com.example.artmind.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HistoryModelTest {

    @Test
    public void testToMap() {
        // Create a sample HistoryModel
        HistoryModel historyModel = new HistoryModel();
        historyModel.setImagePath("sampleImagePath");
        historyModel.setPercentage(75);
        historyModel.setCategory("sampleCategory");
        historyModel.setDesc("sampleDescription");

        // Call toMap method
        Map<String, Object> map = historyModel.toMap();

        // Check if the map contains the expected values
        assertEquals("sampleImagePath", map.get("imagePath"));
        assertEquals(75, map.get("percentage"));
        assertEquals("sampleCategory", map.get("category"));
        assertEquals("sampleDescription", map.get("desc"));
    }

    @Test
    public void testFromMap() {
        // Create a sample map
        Map<String, Object> map = new HashMap<>();
        map.put("imagePath", "sampleImagePath");
        map.put("percentage", 75L);
        map.put("category", "sampleCategory");
        map.put("desc", "sampleDescription");

        // Call fromMap method
        HistoryModel historyModel = HistoryModel.fromMap(map);

        // Check if the HistoryModel object has the expected values
        assertEquals("sampleImagePath", historyModel.getImagePath());
        assertEquals(75, historyModel.getPercentage());
        assertEquals("sampleCategory", historyModel.getCategory());
        assertEquals("sampleDescription", historyModel.getDesc());
    }
}
