package com.example.openai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.openai.utils.OpenAIAPIUtil;

public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnGenerate = findViewById(R.id.btnGenerate);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GenerateAnswerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }


    private class GenerateAnswerTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String prompt = input.getText().toString();
            Log.d("promp", prompt);
            return OpenAIAPIUtil.generateAnswer(prompt);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                Log.d("Hasil", response);
                output.setText(response);
            }
        }
    }
}