package com.example.artmind.api;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RoboflowApi {

    public static void getPrediction(Uri uri) {
        new AsyncTask<Uri, Void, String>() {
            @Override
            protected String doInBackground(Uri... uris) {
                HttpURLConnection connect = null;
                try {
                    URL link = new URL(uris[0].toString());
                    connect = (HttpURLConnection) link.openConnection();
                    InputStream inputStream = connect.getInputStream();
                    byte[] imageData = readBytes(inputStream);

                    // Initialize Inference Server Request with API Key, Model, and Model Version
                    String uploadURL = "https://classify.roboflow.com/mental-health-drawing/1?api_key=1hjoF8Vcp6yqoaoSbFc2&name=YOUR_IMAGE.jpg";
                    URL url = new URL(uploadURL);
                    connect = (HttpURLConnection) url.openConnection();
                    connect.setRequestMethod("POST");
                    connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connect.setRequestProperty("Content-Length", Integer.toString(uploadURL.getBytes().length));
                    connect.setRequestProperty("Content-Language", "en-US");
                    connect.setUseCaches(false);
                    connect.setDoOutput(true);

                    // Send request
                    DataOutputStream wr = new DataOutputStream(connect.getOutputStream());
                    wr.writeBytes(uploadURL);
                    wr.close();

                    // Get Response
                    int responseCode = connect.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        return response.toString();
                    } else {
                        String errorDetails = "Request failed with response code: " + responseCode;
                        errorDetails += "\nResponse Message: " + connect.getResponseMessage();
                        return errorDetails;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                } finally {
                    if (connect != null) {
                        connect.disconnect(); // Close the connection to avoid leaks
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                // Handle the result here, e.g., update UI or perform further processing
                System.out.println(result);
            }
        }.execute(uri);
    }

    private static byte[] readBytes(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream()) {
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }
    }
}

