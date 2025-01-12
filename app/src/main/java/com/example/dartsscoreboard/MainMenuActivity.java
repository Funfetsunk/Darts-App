package com.example.dartsscoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btn301Single = findViewById(R.id.btn_301_single);
        Button btn301Double = findViewById(R.id.btn_301_double);
        Button btn501Single = findViewById(R.id.btn_501_single);
        Button btn501Double = findViewById(R.id.btn_501_double);

        btn301Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(301, 1);
            }
        });

        btn301Double.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(301, 2);
            }
        });

        btn501Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(501, 1);
            }
        });

        btn501Double.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(501, 2);
            }
        });
    }

    private void startGame(int startingScore, int players) {
        Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
        intent.putExtra("STARTING_SCORE", startingScore);
        intent.putExtra("PLAYERS", players);
        startActivity(intent);
    }
}
