package com.example.WordMaster.WordQuizMaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EncouragePage extends AppCompatActivity {

    TextView textScore, textGood;
    Button btnContinue, btnBack;
    ImageView cutie;

    private String nextLevel;
    private static final String TAG = "MyApp";

    private String showScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encourage_page);

        // set the animation
        Animation fadeout = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        fadeout.setDuration(400);

        textGood = findViewById(R.id.textGood);
        textScore = findViewById(R.id.textScore);
        btnContinue = findViewById(R.id.Answer);
        btnBack = findViewById(R.id.btnBack);
        cutie = findViewById(R.id.cutie);
        cutie.startAnimation(fadeout);

        // get the newest level and score from the MainActivity
        Bundle extras = getIntent().getExtras();
        nextLevel = extras.getString("newLevel");

        showScore = extras.getString("score");
        textScore.setText("Score: " + showScore);

        // move to MainActivity again
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // move back to MainMenu which is the first page
                Intent i = new Intent(EncouragePage.this, MainMenu.class);
                startActivity(i);
            }

        });
    }
}
