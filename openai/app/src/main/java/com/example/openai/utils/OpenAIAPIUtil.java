package com.example.openai.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

public class OpenAIAPIUtil {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static String generateAnswer(String prompt) {
        Log.d("Debug", "Generating answer openai");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        MediaType mediaType = MediaType.parse("application/json");

        // Construct the list of messages
        String requestBody = "{" +
                "\"messages\": [{" +
                "\"role\": \"system\"," +
                "\"content\": \"You are a helpful assistant.\"" +
                "}," +
                "{" +
                "\"role\": \"user\"," +
                "\"content\": \"" + prompt + "\"" +
                "}]," +
                "\"max_tokens\": 100," +
                "\"model\": \"gpt-3.5-turbo-1106\"" +
                "}";

        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            String content = message.getString("content");

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
