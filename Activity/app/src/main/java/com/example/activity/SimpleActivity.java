package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleActivity extends AppCompatActivity {
    //1. Pendeklarasian Atribut
    private EditText inputName;
    private TextView outputText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        //2. Pengenalan Komponen
        inputName = findViewById(R.id.input_name);
        outputText = findViewById(R.id.output_name);
        button = findViewById(R.id.button_submit);
    }

    public void handleSubmit(View view) {
        String name = inputName.getText().toString();
        outputText.setText("Hello " + name);
    }
}