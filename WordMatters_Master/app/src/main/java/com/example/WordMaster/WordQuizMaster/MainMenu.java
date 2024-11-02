package com.example.WordMaster.WordQuizMaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart,btnhighest;
    private ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // set the animation
        Animation fadeout = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        fadeout.setDuration(400);

        //getting btnStart, and imageView2,btnHighest
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnhighest = findViewById(R.id.btnHighest);
        btnhighest.setOnClickListener(this);

    }

    //onClick listener
    public void onClick(View v) {
        //if user clicks the start button
        if (v.getId() == R.id.btnStart) {
            Intent i = new Intent(MainMenu.this, MainActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.btnHighest) {
            Intent e = new Intent(MainMenu.this, highestmarks.class);
            startActivity(e);
        }
    }
}