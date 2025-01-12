package com.example.dartsscoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerSelectionMenuActivity extends AppCompatActivity {

    private int gameType; // To hold the selected game type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection_menu);

        // Get the game type from the intent
        gameType = getIntent().getIntExtra("GAME_TYPE", 301);

        Button btnSinglePlayer = findViewById(R.id.btn_single_player);
        Button btnTwoPlayers = findViewById(R.id.btn_two_players);

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1);
            }
        });

        btnTwoPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(2);
            }
        });
    }

    private void startGame(int players) {
        Intent intent = new Intent(PlayerSelectionMenuActivity.this, MainActivity.class);
        intent.putExtra("STARTING_SCORE", gameType);
        intent.putExtra("PLAYERS", players);
        startActivity(intent);
    }
}
