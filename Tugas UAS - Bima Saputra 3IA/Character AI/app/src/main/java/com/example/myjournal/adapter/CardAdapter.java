package com.example.myjournal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myjournal.ChatActivity;
import com.example.myjournal.R;
import com.example.myjournal.utils.OpenAI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final List<JSONObject> cardList;
    private final Context context;

    public CardAdapter(List<JSONObject> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject card = cardList.get(position);

            // Ambil data dari JSON
            String id = card.getString("id");
            String imageUrl = card.getString("image");
            String title = card.getString("title");
            String description = card.getString("description");

            // Set data ke tampilan
            holder.itemView.setTag(id);
            Glide.with(context).load(imageUrl).into(holder.imageView);
            holder.titleTextView.setText(title);
            holder.descriptionTextView.setText(description);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle card click here by navigating to ItemFragment
                    OpenAI.clearChatHistory();
                    navigateToChatActivity(id, imageUrl, title, description);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method to navigate to ItemFragment
    private void navigateToChatActivity(String id,String imageUrl,String title,String description) {
        // Create an Intent to start ChatActivity
        Intent intent = new Intent(context, ChatActivity.class);

        // Pass data to ChatActivity using intent extras
        intent.putExtra("id", id);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("title", title);
        intent.putExtra("description", description);

        // Start ChatActivity
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
