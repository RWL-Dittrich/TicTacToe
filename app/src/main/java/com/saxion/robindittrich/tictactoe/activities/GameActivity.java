package com.saxion.robindittrich.tictactoe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saxion.robindittrich.tictactoe.R;
import com.saxion.robindittrich.tictactoe.game.Game;
import com.saxion.robindittrich.tictactoe.managers.HueManager;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private Game game = new Game();

    private ArrayList<Button> buttons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Resources res = getResources();

        int countX = 0, countY = 0;

        //reference all the number inputs for the lightid's and put the value
        for (int i = 0; i < 3*3; i++) {
            if (countX == 3) {
                countY++;
                countX = 0;
            }
            Button button = findViewById(res.getIdentifier("b" + countX + "." + countY, "id", getPackageName()));
            buttons.add(button);
            countX++;
        }
    }

    public void buttonPress(View view) {
        String buttonName = getResources().getResourceEntryName(view.getId());
        String buttonXY = buttonName.split("b")[1];
        String[] xy = buttonXY.split("\\.");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);

        int result = game.nextTurn(x, y);
        switch(result) {
            case 1:
                //red won
                Toast.makeText(this, "Red won!", Toast.LENGTH_LONG).show();
                disableButtons();
                break;
            case 2:
                //blue won
                Toast.makeText(this, "Blue won!", Toast.LENGTH_LONG).show();
                disableButtons();
                break;
            case 3:
                //draw
                Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
                disableButtons();
                break;
        }
        view.setEnabled(false);
    }

    private void disableButtons() {
        for (Button b : buttons) {
            b.setEnabled(false);
        }
    }

    public void resetGame(View view) {
        game.resetBoard();
        for (Button b : buttons) {
            b.setEnabled(true);
        }
    }

}
