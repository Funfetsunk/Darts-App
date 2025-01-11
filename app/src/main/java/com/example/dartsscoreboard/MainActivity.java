package com.example.dartsscoreboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvPointsLeft, tvCheckout, tvScoreInput;
    private int currentScore = 501; // Starting score
    private StringBuilder scoreInputBuilder = new StringBuilder();
    private int previousScore = currentScore; // To store the previous score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPointsLeft = findViewById(R.id.tv_points_left);
        tvCheckout = findViewById(R.id.tv_checkout);
        tvScoreInput = findViewById(R.id.tv_score_input);

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

        Button btnSubmit = findViewById(R.id.btn_submit);
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

                    // Calculate new score
                    int newScore = currentScore - score;

                    // Check for bust (score < 2 but not 0)
                    if (newScore < 2 && newScore != 0) {
                        Toast.makeText(MainActivity.this, "Bust!", Toast.LENGTH_SHORT).show();
                        newScore = previousScore; // Revert to previous score
                    } else {
                        previousScore = currentScore; // Save current score before updating
                        currentScore = newScore; // Update current score
                    }

                    scoreInputBuilder.setLength(0); // Clear the input
                    tvScoreInput.setText("");

                    // Update the display
                    tvPointsLeft.setText("Points Left: " + currentScore);

                    // Update checkout
                    String checkout = getBestCheckout(currentScore);
                    tvCheckout.setText("Best Checkout: " + checkout);
                }
            }
        });
    }

    private String getBestCheckout(int pointsLeft) {
        return CheckoutMap.checkoutMap.getOrDefault(pointsLeft, "");
    }
}
