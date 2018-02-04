package com.example.bwakasa.uci.edu.test3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class UploadScreen extends AppCompatActivity implements Serializable{

    Button yourPet;
    Button theirPet;

    Button Submit;

    private DatabaseReference mDatabase;

    private StorageReference mStorage;

    static StorageReference yourPic;
    static StorageReference theirPic;

    static boolean picking_yours;
    static boolean picking_theirs;

    static Uri your_uri;
    static Uri their_uri;

    static boolean picked_yours;
    static boolean picked_theirs;

    private String key;

    private static final String TAG = "Testing: ";

    private FirebaseAuth mAuth;

    private HashMap<String,HashMap<String,String>> table;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_screen);
        Submit = (Button) findViewById(R.id.submit);
        yourPet = (Button) findViewById(R.id.yourpetPic);
        theirPet = (Button) findViewById(R.id.theirpetPic);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();

        key = mDatabase.child("Matches").push().getKey(); // .child("Matches")
        //Log.i("key_is", key);
        picked_yours = false;
        picked_theirs = false;

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                table = (HashMap<String, HashMap<String,String>>) snapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Cancelled: ", "Failed to read value.");
            }
        });

        yourPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                picking_yours = true;
                startActivityForResult(photoPickerIntent, 2);
            }
        });

        theirPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                picking_theirs = true;
                startActivityForResult(photoPickerIntent, 2);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //must check if both pictures are uploaded
                if (picked_yours && picked_theirs) {
                    yourPic.putFile(your_uri);
                    theirPic.putFile(their_uri);
                    if(mAuth != null) {
                        mDatabase.child(key).child("image_key1").setValue(your_uri.getLastPathSegment());
                        mDatabase.child(key).child("image_key2").setValue(their_uri.getLastPathSegment());
                        mDatabase.child(key).child("score1").setValue("0");
                        mDatabase.child(key).child("score2").setValue("0");
                        Intent myIntent = new Intent(v.getContext(), firstPage.class);
                        myIntent.putExtra("table", table);
                        startActivity(myIntent);
                    }
                }
                else {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Please upload both pictures first.", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if(picking_yours) {
                your_uri = data.getData();
                yourPic = mStorage.child("Photos").child(your_uri.getLastPathSegment());
                picking_yours = false;
                picked_yours = true;
            }
            if(picking_theirs) {
                their_uri = data.getData();
                theirPic = mStorage.child("Photos").child(their_uri.getLastPathSegment());
                picking_theirs = false;
                picked_theirs = true;
            }

        }
    }
}

