package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnToast, btnCount;
    TextView showCount;
    Toast toast;
    Integer number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCount = findViewById(R.id.btnCount);
        btnToast = findViewById(R.id.btnToast);
        showCount = findViewById(R.id.showCount);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = number + 2;
                showCount.setText(number.toString());
            }
        });

        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(MainActivity.this, number.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}