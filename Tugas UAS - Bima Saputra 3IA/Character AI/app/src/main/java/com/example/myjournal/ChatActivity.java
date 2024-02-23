package com.example.myjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjournal.adapter.ChatAdapter;
import com.example.myjournal.model.ChatMessage;
import com.example.myjournal.utils.OpenAI;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView messageListView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private ChatAdapter chatAdapter;
    private String id, imageUrl, title, description;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageListView = findViewById(R.id.messageListView);
        messageEditText = findViewById(R.id.messageEditText);

        sendButton = findViewById(R.id.sendButton);
        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        chatAdapter = new ChatAdapter(this, OpenAI.getChatHistory().isEmpty() ? new ArrayList<>() : OpenAI.getChatHistory());
        messageListView.setAdapter(chatAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("id") && intent.hasExtra("imageUrl") && intent.hasExtra("title") && intent.hasExtra("description")) {
            id = intent.getStringExtra("id");
            imageUrl = intent.getStringExtra("imageUrl");
            title = intent.getStringExtra("title");
            description = intent.getStringExtra("description");

            MaterialToolbar materialToolbar = findViewById(R.id.topAppBar);
            materialToolbar.setTitle(title);
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class GenerateAnswerTask extends AsyncTask<Void, Void, String> {
        private String userMessage;

        public GenerateAnswerTask(String userMessage) {
            this.userMessage = userMessage;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String userPrompt = userMessage;
            String systemPrompt = OpenAI.getSystemPrompt(ChatActivity.this ,id);
            Log.d("systemPrompt", systemPrompt);
            return OpenAI.generateAnswer(userPrompt, systemPrompt);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                Log.d("Hasil", response);
                // Update UI with the OpenAI response here
                chatAdapter.add(new ChatMessage(response, false));
                messageListView.setSelection(chatAdapter.getCount() - 1);
            }
        }
    }

    private void sendMessage() {
        String userMessage = messageEditText.getText().toString().trim();
        if (!userMessage.isEmpty()) {
            chatAdapter.add(new ChatMessage(userMessage, true));
            messageEditText.setText("");

            // Start the GenerateAnswerTask to get OpenAI response
            new GenerateAnswerTask(userMessage).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

}
