package com.example.file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button btnBuat, btnUbah, btnBaca, btnHapus;
    TextView hasilBaca;
    private String fileName = "contoh_file.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBuat = findViewById(R.id.btnBuat);
        btnUbah = findViewById(R.id.btnUbah);
        btnBaca = findViewById(R.id.btnBaca);
        btnHapus = findViewById(R.id.btnHapus);
        hasilBaca = findViewById(R.id.hasilBaca);

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buatFile();
            }
        });
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahFile();
            }
        });
        btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bacaFile();
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusFile();
            }
        });
    }

    private void buatFile() {
        String isiFile = "Isi file yang baru dibuat";
        simpanKeFile(isiFile);
        hasilBaca.setText("File berhasil dibuat!");
    }

    private void ubahFile() {
        String isiFile = "Isi file yang diubah";
        simpanKeFile(isiFile);
        hasilBaca.setText("File berhasil diubah!");
    }

    private void bacaFile() {
        String isiFile = bacaDariFile();
        hasilBaca.setText("Isi file: " + isiFile);
    }

    private void hapusFile() {
        File file = new File(getFilesDir(), fileName);
        if (file.exists()) {
            file.delete();
            hasilBaca.setText("File berhasil dihapus!");
        } else {
            hasilBaca.setText("File tidak ditemukan.");
        }
    }

    public void simpanKeFile(String data) {
        try {
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String bacaDariFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream inputStream = openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}