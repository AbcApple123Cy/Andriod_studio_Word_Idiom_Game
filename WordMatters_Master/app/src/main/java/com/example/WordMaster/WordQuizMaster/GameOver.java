package com.example.WordMaster.WordQuizMaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {
    Button tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        // button for move back to the first page
        tab = findViewById(R.id.tab);
        tab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOver.this, MainMenu.class);
                startActivity(i);
            }

        });
    }
}