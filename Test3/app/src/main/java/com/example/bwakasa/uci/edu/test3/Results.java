package com.example.bwakasa.uci.edu.test3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Iterator;

public class Results extends AppCompatActivity {

    private ImageView pic1_1;
    private ImageView pic1_2;
    private TextView text1_1;
    private TextView text1_2;

    private ImageView pic2_1;
    private ImageView pic2_2;
    private TextView text2_1;
    private TextView text2_2;

    private ImageView pic3_1;
    private ImageView pic3_2;
    private TextView text3_1;
    private TextView text3_2;

    private Button next;

    StorageReference storageRef;
    DatabaseReference DBref;

    private HashMap<String, HashMap<String, String>> table;

    private Iterator it;

    private HashMap.Entry<String, HashMap<String, String>> current_match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setTitle("Results");
        pic1_1 = (ImageView) findViewById(R.id.pic1_1);
        pic1_2 = (ImageView) findViewById(R.id.pic1_2);
        text1_1 = (TextView) findViewById(R.id.text1_1);
        text1_2 = (TextView) findViewById(R.id.text1_2);

        pic2_1 = (ImageView) findViewById(R.id.pic2_1);
        pic2_2 = (ImageView) findViewById(R.id.pic2_2);
        text2_1 = (TextView) findViewById(R.id.text2_1);
        text2_2 = (TextView) findViewById(R.id.text2_2);

        pic3_1 = (ImageView) findViewById(R.id.pic3_1);
        pic3_2 = (ImageView) findViewById(R.id.pic3_2);
        text3_1 = (TextView) findViewById(R.id.text3_1);
        text3_2 = (TextView) findViewById(R.id.text3_2);

        storageRef = FirebaseStorage.getInstance().getReference().child("Photos");
        DBref = FirebaseDatabase.getInstance().getReference();

        table = (HashMap<String, HashMap<String, String>>) getIntent().getSerializableExtra("table");

        int matches = table.size();

        it = table.entrySet().iterator();
        if (it.hasNext()) {
            current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();
        }


        String first_img_loc = current_match.getValue().get("image_key1");
        String second_img_loc = current_match.getValue().get("image_key2");

        storageRef = storageRef.child(first_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic1_1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic1_2);

        text1_1.setText(current_match.getValue().get("score1"));
        text1_2.setText(current_match.getValue().get("score2"));

        storageRef = storageRef.getParent();
        current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();

        first_img_loc = current_match.getValue().get("image_key1");
        second_img_loc = current_match.getValue().get("image_key2");

        storageRef = storageRef.child(first_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic2_1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic2_2);

        text2_1.setText(current_match.getValue().get("score1"));
        text2_2.setText(current_match.getValue().get("score2"));

        storageRef = storageRef.getParent();
        current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();

        first_img_loc = current_match.getValue().get("image_key1");
        second_img_loc = current_match.getValue().get("image_key2");

        storageRef = storageRef.child(first_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic3_1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic3_2);

        text3_1.setText(current_match.getValue().get("score1"));
        text3_2.setText(current_match.getValue().get("score2"));

        storageRef = storageRef.getParent();
        current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();
    }

    public void onNext(View view) {
        String first_img_loc = current_match.getValue().get("image_key1");
        String second_img_loc = current_match.getValue().get("image_key2");

        storageRef = storageRef.child(first_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic1_1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic1_2);

        text1_1.setText(current_match.getValue().get("score1"));
        text1_2.setText(current_match.getValue().get("score2"));

        storageRef = storageRef.getParent();
        if(!it.hasNext()) { return; }
        current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();

        first_img_loc = current_match.getValue().get("image_key1");
        second_img_loc = current_match.getValue().get("image_key2");

        storageRef = storageRef.child(first_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic2_1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic2_2);

        text2_1.setText(current_match.getValue().get("score1"));
        text2_2.setText(current_match.getValue().get("score2"));

        storageRef = storageRef.getParent();
        if(!it.hasNext()) { return; }
        current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();

        first_img_loc = current_match.getValue().get("image_key1");
        second_img_loc = current_match.getValue().get("image_key2");

        storageRef = storageRef.child(first_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic3_1);

        storageRef = storageRef.getParent().child(second_img_loc);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(pic3_2);

        text3_1.setText(current_match.getValue().get("score1"));
        text3_2.setText(current_match.getValue().get("score2"));

        storageRef = storageRef.getParent();
        if(!it.hasNext()) { return; }
        current_match = (HashMap.Entry<String, HashMap<String, String>>) it.next();
    }

}