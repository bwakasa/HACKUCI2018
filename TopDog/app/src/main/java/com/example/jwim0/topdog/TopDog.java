package com.example.jwim0.topdog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TopDog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_dog);

        String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato", "Grape", "Banana", "Monkey"};
        ListAdapter michaelAdapter = new customAdapter(this, foods);
        ListView michaelListView = (ListView) findViewById(R.id.listView);
        michaelListView.setAdapter(michaelAdapter);

    }
}
