package com.example.WordMaster.WordQuizMaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class End extends AppCompatActivity {

    TextView textScore;
    Button pab;

    private String showScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        textScore = findViewById(R.id.textScore);

        // get the newest score from the MainActivity
        Bundle extras = getIntent().getExtras();
        showScore = extras.getString("score");
        textScore.setText("Score: " + showScore);

        // button for move back to the first page
        pab = findViewById(R.id.pab);
        pab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(End.this, MainMenu.class);
                startActivity(i);
            }

        });

    }
}

