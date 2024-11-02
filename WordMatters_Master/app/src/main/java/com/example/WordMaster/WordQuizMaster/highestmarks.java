package com.example.WordMaster.WordQuizMaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class highestmarks extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnhome2;
    private TextView highest1;
    private int highestmarks;
    private  int currenthighestscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highestmarks);
        globalhighest highest = (globalhighest) getApplicationContext(); // create a globalhighest
        highestmarks=highest.getHighestmarks(); // highestmarks = first 1
        currenthighestscore=highest.getCurrentHighestmarks(); // = newest current
        highest1 = findViewById(R.id.highest);

        highest1.setText("Score: "+String.valueOf(highestmarks)); // set the first highest marks with nowScore(newest score)
        //if newst score higher
        if(currenthighestscore > highestmarks){
            // if later new score is higher ,set it as highest marks ever
            highest1.setText("Score: "+String.valueOf( currenthighestscore));
        }
        // back to main menu button
        btnhome2=findViewById(R.id.btnHome2);
        btnhome2.setOnClickListener(this);


    }


    //onClick listener
    public void onClick(View v) {
        //if user clicks the homebutton, move back to main menu
        if (v.getId() == R.id.btnHome2) {
            Intent homepage2 = new Intent(highestmarks.this, MainMenu.class);
            startActivity(homepage2);
        }
    }

}
