package com.mark.xvso;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView tvPlayer1;
    private TextView tvPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("X's Vs O's");
        tvPlayer1 = findViewById(R.id.tv_01);
        tvPlayer2 = findViewById(R.id.tv_02);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btnID = "btn_" + i + j;
                int resID = getResources().getIdentifier(btnID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button btn_reset = findViewById(R.id.brn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        resetBoard();
        player1Points = 0;
        player2Points = 0;
        roundCount = 0;
        player1Turn = true;
        updatePoints();
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return; //string not empty, so cant be edited
        }
        if (player1Turn == true) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;

        if (checkForWin() == true) {
            if (player1Turn == true) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            tieGame();
        }
        player1Turn = !player1Turn; //switch turns
    }

    private void tieGame() {
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins", Toast.LENGTH_LONG).show();
        updatePoints();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void updatePoints() {
        tvPlayer1.setText("Player 1: " + player1Points);
        tvPlayer2.setText("Player 2: " + player2Points);
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins", Toast.LENGTH_LONG).show();
        updatePoints();
        resetBoard();
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) { //rows
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) { //columns
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        return field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("");
    }

    /*
        private int player1Points;
        private int player2Points;
        */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}