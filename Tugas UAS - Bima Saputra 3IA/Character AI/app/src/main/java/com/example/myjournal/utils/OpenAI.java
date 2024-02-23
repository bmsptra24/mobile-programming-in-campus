package com.example.myjournal.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.myjournal.R;
import com.example.myjournal.model.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class OpenAI {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static List<ChatMessage> chatHistory = new ArrayList<>();

    public static String getSystemPrompt(Context context, String id) {
        String systemPrompt = "";
        Resources res = context.getResources();

        switch (id) {
            case "0001": //jokowi
                systemPrompt = res.getString(R.string.jokowi_prompt);
                break;
            case "0002": //prabowo
                systemPrompt = res.getString(R.string.prabowo_prompt);
                break;
            case "0003": //megawati
                systemPrompt = res.getString(R.string.megawati_prompt);
                break;
            case "0004": //elonmusk
                systemPrompt = res.getString(R.string.elonmusk_prompt);
                break;
            case "0005": //tiktoker debm
                systemPrompt = res.getString(R.string.tiktoker_debm_prompt);
                break;
            default:
                // Handle default case if needed
                break;
        }
        return systemPrompt;
    }


    public static String generateAnswer(String userPrompt, String systemPrompt) {
        Log.d("Debug", "Generating answer openai");

        // Set the timeout duration in seconds
        int timeoutInSeconds = 30;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");

        // Convert chat history to JSON string
        String chatHistoryJsonString = convertChatHistoryToJsonString(chatHistory);

        // Construct the list of messages
        String requestBody = "{" +
                "\"messages\": [{" +
                "\"role\": \"system\"," +
                "\"content\": \"" + systemPrompt + "\"" +
                "}," +
                "{" +
                "\"role\": \"user\"," +
                "\"content\": \"" + userPrompt + "\"" +
                "}" +
                chatHistoryJsonString +
                "]," +
                "\"max_tokens\": 300," +
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

            String answer = message.getString("content");

            // Update global chat history
            chatHistory.add(new ChatMessage(userPrompt, true));
            chatHistory.add(new ChatMessage(answer, false));

            Log.d("chatHistory", chatHistory.toString());
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public static void clearChatHistory() {
        chatHistory.clear();
    }

    public static String convertChatHistoryToJsonString(List<ChatMessage> chatHistory) {
        if (chatHistory.isEmpty()) return "";

        JSONArray jsonArray = new JSONArray();

        try {
            for (ChatMessage chatMessage : chatHistory) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("role", chatMessage.isUser() ? "user" : "assistant");
                jsonObject.put("content", chatMessage.getMessage());

                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "," + jsonArray.toString().substring(1, jsonArray.toString().length() - 1);
    }
}
