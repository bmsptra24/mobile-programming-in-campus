package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

////    hello world
//public class MainActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.helloworld);
//    }
//}

//    relative layout
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relativelayout);
    }
}

////    toast
//public class MainActivity extends AppCompatActivity {
//    Context context; // menyimpan appContext
//    Toast toast; // instance dari kelas Toast
//    Button b1; // instance dari komponen Button di XML
//    int duration; // menyimpan durasi toast ini tampil
//    String myToast; // menyimpan teks untuk ditampilkan di toast
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.toast);
//
//        b1 =(Button)findViewById(R.id.btnToast);
//        context = getApplicationContext();
//        myToast= "Hello World";
//        duration = Toast.LENGTH_LONG;
//        toast = Toast.makeText(context,myToast,duration);
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                toast.show();
//            }
//        });
//    }
//}