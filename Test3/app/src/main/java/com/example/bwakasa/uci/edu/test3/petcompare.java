package com.example.bwakasa.uci.edu.test3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class petcompare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcompare);
        ImageView myImageView = findViewById(R.id.imaged);
        myImageView.setImageResource(R.drawable.dog2);
        ImageView newImageView = findViewById(R.id.pug2);
        newImageView.setImageResource(R.drawable.pug);
    }

}

