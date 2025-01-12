package com.example.dartsscoreboard;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvPointsLeftPlayer1, tvPointsLeftPlayer2, tvCheckout, tvScoreInput;
    private int currentScore1, currentScore2; // Scores for both players
    private StringBuilder scoreInputBuilder = new StringBuilder();
    private int previousScore1, previousScore2;
    private int currentPlayer = 1; // Track current player
    private int players; // Number of players
    private Button btnSubmit, btnReturnToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the starting score and player count from the Intent
        int startingScore = getIntent().getIntExtra("STARTING_SCORE", 501);
        players = getIntent().getIntExtra("PLAYERS", 1);

        currentScore1 = startingScore;
        currentScore2 = startingScore;
        previousScore1 = startingScore;
        previousScore2 = startingScore;

        tvPointsLeftPlayer1 = findViewById(R.id.tv_points_left_player1);
        tvPointsLeftPlayer2 = findViewById(R.id.tv_points_left_player2);
        tvCheckout = findViewById(R.id.tv_checkout);
        tvScoreInput = findViewById(R.id.tv_score_input);
        btnSubmit = findViewById(R.id.btn_submit);
        btnReturnToMenu = findViewById(R.id.btn_return_to_menu);

        int[] buttonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2,
                R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9
        };

        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                scoreInputBuilder.append(button.getText().toString());
                tvScoreInput.setText(scoreInputBuilder.toString());
            }
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(numberButtonClickListener);
        }

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoreInputBuilder.length() > 0) {
                    scoreInputBuilder.deleteCharAt(scoreInputBuilder.length() - 1);
                    tvScoreInput.setText(scoreInputBuilder.toString());
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoreInputBuilder.length() > 0) {
                    int score = Integer.parseInt(scoreInputBuilder.toString());

                    // Validate that the score does not exceed 180
                    if (score > 180) {
                        Toast.makeText(MainActivity.this, "Score cannot exceed 180.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (players == 1) {
                        // In single-player mode, always update Player 1's score
                        updateScore(score, 1);
                    } else {
                        // In two-player mode, update the current player's score
                        if (currentPlayer == 1) {
                            updateScore(score, 1);
                        } else {
                            updateScore(score, 2);
                        }
                        togglePlayer();
                    }
                }
            }
        });

        btnReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameTypeMenuActivity.class);
                startActivity(intent);
            }
        });

        // Update the display with the starting scores
        tvPointsLeftPlayer1.setText("Player 1: " + currentScore1);
        if (players == 2) {
            tvPointsLeftPlayer2.setVisibility(View.VISIBLE);
            tvPointsLeftPlayer2.setText("Player 2: " + currentScore2);
        } else {
            tvPointsLeftPlayer2.setVisibility(View.GONE);
        }

        // Initialize the checkout display
        updateCheckout();
    }

    private void updateScore(int score, int player) {
        int newScore;
        int previousScore;
        TextView tvPointsLeft;

        if (player == 1) {
            newScore = currentScore1 - score;
            previousScore = currentScore1;
            tvPointsLeft = tvPointsLeftPlayer1;
        } else {
            newScore = currentScore2 - score;
            previousScore = currentScore2;
            tvPointsLeft = tvPointsLeftPlayer2;
        }

        // Check for bust (score < 2 but not 0)
        if (newScore < 2 && newScore != 0) {
            Toast.makeText(MainActivity.this, "Bust!", Toast.LENGTH_SHORT).show();
            newScore = previousScore; // Revert to previous score
        } else if (newScore == 0) {
            // Player wins
            showWinner(player);
        } else {
            if (player == 1) {
                previousScore1 = currentScore1;
                currentScore1 = newScore;
            } else {
                previousScore2 = currentScore2;
                currentScore2 = newScore;
            }
        }

        scoreInputBuilder.setLength(0); // Clear the input
        tvScoreInput.setText("");

        // Update the display
        tvPointsLeft.setText("Player " + player + ": " + newScore);

        // Update checkout
        updateCheckout();
    }

    private void showWinner(int player) {
        Toast.makeText(MainActivity.this, "Player " + player + " wins!", Toast.LENGTH_LONG).show();
        btnSubmit.setEnabled(false); // Disable the submit button
        btnReturnToMenu.setVisibility(View.VISIBLE); // Show the return to menu button
    }

    private void updateCheckout() {
        int pointsLeft = (currentPlayer == 1) ? currentScore1 : currentScore2;
        String checkout = getBestCheckout(pointsLeft);
        tvCheckout.setText("Best Checkout: " + checkout);
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        Toast.makeText(MainActivity.this, "Player " + currentPlayer + "'s Turn", Toast.LENGTH_SHORT).show();
        // Update the checkout for the new current player
        updateCheckout();
    }

    private String getBestCheckout(int pointsLeft) {
        return CheckoutMap.checkoutMap.getOrDefault(pointsLeft, "");
    }
}
