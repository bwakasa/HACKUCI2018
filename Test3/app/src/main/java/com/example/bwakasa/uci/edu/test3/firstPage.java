package com.example.bwakasa.uci.edu.test3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class firstPage extends AppCompatActivity implements View.OnClickListener, Serializable {

    ImageButton b1;
    ImageButton b2;
    TextView score;
    TextView score2;
    Button rate;
    Button compete;
    Button voted;
    int counter;
    int counter2;
    String strcounter;
    String first_img_loc;
    String second_img_loc;

    FirebaseDatabase FDB;
    DatabaseReference DBref;
    DatabaseReference DBref2;

    StorageReference storageRef;

    private HashMap<String,HashMap<String,String>> table;

    private HashMap.Entry<String, HashMap<String,String>> current_match;

    static Iterator it;

    ImageButton image1;
    ImageButton image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setTitle("PetCompare");
        FDB = FirebaseDatabase.getInstance();
        //  DBref = FDB.getReference("image_key1");
        // DBref2 = FDB.getReference("image_key2");
        DBref = FDB.getReference();

        storageRef = FirebaseStorage.getInstance().getReference().child("Photos");

        table = (HashMap<String,HashMap<String,String>>) getIntent().getSerializableExtra("table");


        first_img_loc = "1608253266";
        second_img_loc = "1987731159";

        if(table != null)
        {
            //   Log.i("table: ", table.toString());
            //  Log.i("First item: ", table.get("-L4VYSamY8qmoyGBo-fu").get("image_key1"));
            it = table.entrySet().iterator();
            current_match = (HashMap.Entry<String, HashMap<String,String>>) it.next();
            first_img_loc = current_match.getValue().get("image_key1");
            second_img_loc = current_match.getValue().get("image_key2");
        }

        storageRef = storageRef.child(first_img_loc);

        image1 = (ImageButton) findViewById(R.id.imageB1);
        image2 = (ImageButton) findViewById(R.id.imageB2);
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(image1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(image2);

        storageRef = storageRef.getParent();

        b1 = findViewById(R.id.imageB1);
        b2 = findViewById(R.id.imageB2);
        score = findViewById(R.id.integer_number1);
        score2 =  findViewById(R.id.integer_number2);
        rate = findViewById(R.id.button);
        compete = findViewById(R.id.button2);
        voted = findViewById(R.id.button3);

        rate.setOnClickListener(this);
        compete.setOnClickListener(this);
        voted.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        DBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                table = (HashMap<String, HashMap<String,String>>) snapshot.getValue();
                it = table.entrySet().iterator();
                current_match = (HashMap.Entry<String, HashMap<String,String>>) it.next();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Cancelled: ", "Failed to read value.");
            }
        });
    }

    public void display(HashMap.Entry<String, HashMap<String,String>> match) {
        String first_image_loc = match.getValue().get("image_key1");
        String second_image_loc = match.getValue().get("image_key2");

        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        storageRef = storageRef.child(first_image_loc);
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(image1);

        storageRef = storageRef.getParent().child(second_image_loc);
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(image2);
        storageRef = storageRef.getParent();
    }

    public void onClick(View view) {

        if (view == b1)
        {
            String key = "-L4V-0TAYMg3jQ1dLXdj";
            if(current_match != null) {
                key = current_match.getKey();
            }
            // Log.i("key is: ", key);
            String score1_str = current_match.getValue().get("score1");
            int score_1 = Integer.parseInt(score1_str);
            score_1 += 1;
            //give score1 back to cloud

            DBref.child(key).child("score1").setValue(Integer.toString(score_1));

            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            if(it.hasNext()) {
                current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();
                display(current_match);
            }
        }
        else if (view == b2)
        {
            String key = "-L4V-0TAYMg3jQ1dLXdj";
            if(current_match != null) {
                key = current_match.getKey();
            }
            // Log.i("key is: ", key);
            String score1_str = current_match.getValue().get("score2");
            int score_2 = Integer.parseInt(score1_str);
            score_2 += 1;
            //give score1 back to cloud

            DBref.child(key).child("score2").setValue(Integer.toString(score_2));

            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            if(it.hasNext()) {
                current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();
                display(current_match);
            }
        }

        else if(view == rate)
        {
//            rate.setVisibility(View.GONE);

            //     myIntent.putExtra(VARIABLE NAME, INFO GOES HERE); <- if we need to pass in variables

        }
        else if(view == compete)
        {
            Intent myIntent = new Intent(this, UploadScreen.class);
            startActivity(myIntent);
//            compete.setVisibility(View.GONE);
        }
        else if(view == voted)
        {
//            voted.setVisibility(View.GONE);
        }
    }


}


