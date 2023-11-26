package com.example.artmind.api;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RoboflowApi {

    public static void getPrediction(Uri uri) {
        new AsyncTask<Uri, Void, String>() {
            @Override
            protected String doInBackground(Uri... uris) {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .build();

                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                    // Replace *base54* with the actual image URL
                    String imageUrl = uris[0].toString();

                    RequestBody body = new FormBody.Builder()
                            .add("image", imageUrl)
                            .build();

                    Request request = new Request.Builder()
                            .url("https://classify.roboflow.com/mental-health-drawing/1?api_key=EP2kiEX6FQq9RXkV4WdZ")
                            .post(body)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .build();

                    Response response = client.newCall(request).execute();

                    // Check if the request was successful
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return "Error: " + response.code() + " - " + response.message();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
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
}