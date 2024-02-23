package com.example.tugasactivity;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleActivity extends AppCompatActivity {
    //1. Pendeklarasian Atribut
    private EditText inputName, inputNim, inputKelas, inputHobi, inputAlamat;
    private TextView outputText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        //2. Pengenalan Komponen
        inputName = findViewById(R.id.input_name);
        inputNim = findViewById(R.id.input_nim);
        inputKelas = findViewById(R.id.input_hobi);
        inputHobi = findViewById(R.id.input_hobi);
        inputAlamat = findViewById(R.id.input_alamat);

        outputText = findViewById(R.id.output_name);
        button = findViewById(R.id.button_submit);
    }

    public void handleSubmit(View view) {
        String name = inputName.getText().toString();
        String nim = inputNim.getText().toString();
        String kelas = inputKelas.getText().toString();
        String hobi = inputHobi.getText().toString();
        String alamat = inputAlamat.getText().toString();

        outputText.setText("Data yang anda masukan:" +
                "\n1. Nama : " + name +
                "\n2. Nim : " + nim +
                "\n3. Kelas : " + kelas +
                "\n4. Hobi : " + hobi +
                "\n5. Alamat : " + alamat);
    }
}