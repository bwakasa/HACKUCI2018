package com.example.bwakasa.uci.edu.test3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class UploadScreen extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_screen);
        Submit = (Button) findViewById(R.id.submit);
        yourPet = (Button) findViewById(R.id.yourpetPic);
        theirPet = (Button) findViewById(R.id.theirpetPic);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Matches");

        mStorage = FirebaseStorage.getInstance().getReference();

        key = mDatabase.child("Matches").push().getKey();
        picked_yours = false;
        picked_theirs = false;

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

                    mDatabase.child(key).child("image_key1").setValue(your_uri.getLastPathSegment());
                    mDatabase.child(key).child("image_key2").setValue(their_uri.getLastPathSegment());
                    mDatabase.child(key).child("score1").setValue(0);
                    mDatabase.child(key).child("score2").setValue(0);
                    Toast myToast = Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG);
                    myToast.show();
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
