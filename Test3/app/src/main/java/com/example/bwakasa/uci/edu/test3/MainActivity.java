package com.example.bwakasa.uci.edu.test3;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;
//import android.R;
//import com.example.bwakasa.uci.edu.test3.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button mFireBaseBtn;
    private DatabaseReference mDatabase;
    private DatabaseReference nDatabase;
    private DatabaseReference rDatabase;
    private EditText mNameField;
    private EditText mEmailField;
    private ArrayList<String> alist;


    private Button mSelectImage;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private String Dkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        setContentView(R.layout.activity_main);
        mFireBaseBtn = (Button) findViewById(R.id.Fire_base_bin);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mNameField = (EditText) findViewById(R.id.name_field);
        mEmailField = (EditText) findViewById(R.id.email_field);

        mStorage = FirebaseStorage.getInstance().getReference();
        nDatabase = FirebaseDatabase.getInstance().getReference("Fights");
        Dkey = nDatabase.child("Fights").push().getKey();
        mSelectImage = (Button) findViewById(R.id.selectimage);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rDatabase = FirebaseDatabase.getInstance().getReference("Fights");
        rDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alist.add(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mFireBaseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mNameField.getText().toString().trim();
                String email = mEmailField.getText().toString().trim();

                HashMap<String,String> dataMap = new HashMap<String,String>();
                dataMap.put("Name",name);
                dataMap.put("Email",email);
                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Stored...",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Error...",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });

        rDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alist.add(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSelectImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,GALLERY_INTENT);

            }
        });


        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT &&resultCode == RESULT_OK){

            Uri uri = data.getData();
            String val = uri.getLastPathSegment();
            StorageReference ref= mStorage.child("Photos").child(val);
            nDatabase.child(Dkey).child("picture1").setValue(val);
            nDatabase.child(Dkey).child("score1").setValue(0);
            nDatabase.child(Dkey).child("score2").setValue(0);

            ref.putFile(uri);

        }
    }


}