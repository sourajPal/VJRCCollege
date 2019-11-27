package com.example.vjrccollege;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void feed(View view) {
        Intent intent=new Intent(MainActivity.this,feedbackActivity.class);
        startActivity(intent);
    }


    public void cms(View view) {

        Intent intent=new Intent(MainActivity.this,CMSYSActivity.class);
        startActivity(intent);
    }

    public void td(View view) {

        Intent intent=new Intent(MainActivity.this,tdActivity.class);
        startActivity(intent);
    }
    public void home(View view) {

        Intent intent=new Intent(MainActivity.this,homeActivity.class);
        startActivity(intent);
    }

}


