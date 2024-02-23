package com.example.myjournal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myjournal.adapter.CardAdapter;
import com.example.myjournal.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActorFragment extends Fragment {

    public ActorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actor, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String json = JSONUtils.loadJSONFromRawResource(getResources(), R.raw.cards_data);

        List<JSONObject> cardList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cardList.add(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CardAdapter cardAdapter = new CardAdapter(cardList, getActivity());
        recyclerView.setAdapter(cardAdapter);

        return view;
    }
}
