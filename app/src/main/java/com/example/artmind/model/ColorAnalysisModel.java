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

/**
 * Model to analyse dominant color on the uploaded image
 * and categorize the image to dark or light
 * to determine whether the image is healthy or unhealthy
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class ColorAnalysisModel {
    private String resultCategory;
    private String resultColor;
    private int resultPercentage;

    /**
     * Main method call to analyse image
     *
     * @param context     context of the activity or fragment
     * @param resultImage uri of the uploaded image
     */
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

    /**
     * Convert the uri to bitmap format
     *
     * @param context context of the activity or fragment
     * @param uri     uri of the uploaded image
     * @return bitmap
     */
    private Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // Convert uri into input stream then to bitmap
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculate the darkness percentage of the color
     *
     * @param color number representing the dominant color
     * @return double
     */
    private double calculateDarknessPercentage(int color) {
        return ColorUtils.calculateLuminance(color) * 100; // Luminance value ranges from 0 to 1
    }

    /**
     * Calculate the lightness percentage of the color
     *
     * @param color representing the dominant color
     * @return double
     */
    private double calculateLightnessPercentage(int color) {
        return 100 - calculateDarknessPercentage(color);
    }

    /**
     * Get the dominant color from a list of colors in palette
     *
     * @param palette color palette found in image
     * @return int
     */
    private int getDominantColor(Palette palette) {
        int dominantColor = Color.BLACK;
        int maxPopulation = 0;

        // Get dominant color from the palette swatch
        List<Palette.Swatch> swatches = palette.getSwatches();
        for (Palette.Swatch swatch : swatches) {
            if (swatch.getPopulation() > maxPopulation) {
                maxPopulation = swatch.getPopulation();
                dominantColor = swatch.getRgb();
            }
        }
        return dominantColor;
    }

    /**
     * Determine the category of the image based on darkness/ lightness of dominant color
     *
     * @param darknessPercentage  calculated darkness percentage of dominant color
     * @param lightnessPercentage calculated lightness percentage of dominant color
     * @return String (Healthy/ Unhealthy)
     */
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

    /**
     * Getter method for result category
     *
     * @return String
     */
    public String getResultCategory() {
        return resultCategory;
    }

    /**
     * Getter method for result color
     *
     * @return String
     */
    public String getResultColor() {
        return resultColor;
    }

    /**
     * Getter method for result percentage
     *
     * @return int
     */
    public int getResultPercentage() {
        return resultPercentage;
    }
}
