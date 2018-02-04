package com.example.bwakasa.uci.edu.test3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;

public class firstPage extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
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

    }

    @Override
    public void onClick(View view) {

        if (view == b1)
        {
            counter++;
            strcounter = Integer.toString(counter);
            score.setText(strcounter);
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }
        else if (view == b2)
        {
            counter2++;
            strcounter = Integer.toString(counter2);
            score2.setText(strcounter);
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }

        else if(view == rate)
        {
//            rate.setVisibility(View.GONE);
        }
        else if(view == compete)
        {
//            compete.setVisibility(View.GONE);
        }
        else if(view == voted)
        {
//            voted.setVisibility(View.GONE);
        }
    }
}
