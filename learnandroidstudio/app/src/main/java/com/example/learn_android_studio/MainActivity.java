package com.example.learn_android_studio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        final Button btnKirim = findViewById(R.id.submit);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnKirim.setBackgroundColor(Color.BLUE);
                Toast.makeText(getApplicationContext(), "Tombol ditekan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}