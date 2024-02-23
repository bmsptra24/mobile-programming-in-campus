package com.example.myjournal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myjournal.R;
import com.example.myjournal.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private Context context;
    private List<ChatMessage> messages;

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isUser() ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public ChatAdapter(@NonNull Context context, @NonNull List<ChatMessage> messages) {
        super(context, 0, messages);
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatMessage message = messages.get(position);

        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);

            if (viewType == 0) {
                convertView = inflater.inflate(R.layout.item_user_message, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.item_ai_message, parent, false);
            }
        }

        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        messageTextView.setText(message.getMessage());

        return convertView;
    }
}
