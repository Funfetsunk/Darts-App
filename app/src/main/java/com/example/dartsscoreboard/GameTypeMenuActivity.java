package com.example.dartsscoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GameTypeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type_menu);

        Button btn301 = findViewById(R.id.btn_301);
        Button btn501 = findViewById(R.id.btn_501);

        btn301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPlayers(301);
            }
        });

        btn501.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPlayers(501);
            }
        });
    }

    private void selectPlayers(int gameType) {
        Intent intent = new Intent(GameTypeMenuActivity.this, PlayerSelectionMenuActivity.class);
        intent.putExtra("GAME_TYPE", gameType);
        startActivity(intent);
    }
}
