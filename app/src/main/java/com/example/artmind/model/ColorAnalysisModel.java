package com.example.artmind.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ColorAnalysisModel {
    private String resultCategory;
    private String resultColor;
    private int resultPercentage;

    public void analyzeImage(Context context, Uri resultImage) {
        // Convert Uri to Bitmap
        Bitmap bitmap = getBitmapFromUri(context, resultImage);

        // Use Palette library to extract colors from the bitmap
        Palette palette = Palette.from(bitmap).generate();

        // Determine the dominant color
        int dominantColor = getDominantColor(palette);
        resultColor = "Dominant Color: " + Integer.toHexString(dominantColor);

        // Calculate the darkness and lightness percentages
        double darknessPercentage = calculateDarknessPercentage(dominantColor);
        double lightnessPercentage = calculateLightnessPercentage(dominantColor);

        // Determine whether the image is healthy or unhealthy based on color analysis
        resultCategory = determineCategory(darknessPercentage, lightnessPercentage);
    }

    private Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private double calculateDarknessPercentage(int color) {
        return ColorUtils.calculateLuminance(color) * 100; // Luminance value ranges from 0 to 1
    }

    private double calculateLightnessPercentage(int color) {
        return 100 - calculateDarknessPercentage(color);
    }

    private int getDominantColor(Palette palette) {
        int dominantColor = Color.BLACK;
        int maxPopulation = 0;

        List<Palette.Swatch> swatches = palette.getSwatches();
        for (Palette.Swatch swatch : swatches) {
            if (swatch.getPopulation() > maxPopulation) {
                maxPopulation = swatch.getPopulation();
                dominantColor = swatch.getRgb();
            }
        }

        return dominantColor;
    }

    private String determineCategory(double darknessPercentage, double lightnessPercentage) {
        // Adjust the conditions based on your criteria for healthy and unhealthy percentages
        if (darknessPercentage < 50 && lightnessPercentage < 30) {
            resultPercentage = (int) darknessPercentage;
            return "Unhealthy";
        } else {
            resultPercentage = (int) lightnessPercentage;
            return "Healthy";
        }
    }

    public String getResultCategory() {
        return resultCategory;
    }

    public String getResultColor() {
        return resultColor;
    }

    public int getResultPercentage() {
        return resultPercentage;
    }
}
