package com.sakibarrahman.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.EditText;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    int playerNum = 1;
    int[] gameState = {0,0,0,0,0,0,0,0,0};

    public static boolean isDraw (int[] gameState){
        boolean check = true;
        for (int x: gameState){
            if (x==0){
                check = false;
            }
        }
        if (check == true)
            return true;
        return false;
    }

    public static boolean isWinner (int[] gameState, int playerNum){
        for (int x=0; x<9; x= x+3){
            if (gameState[x]==playerNum && gameState[x+1]==playerNum && gameState[x+2]==playerNum){
                return true;
            }
        }
        for (int x=0; x<3; x++) {
            if (gameState[x] == playerNum && gameState[x + 3] == playerNum && gameState[x + 6] == playerNum) {
                return true;
            }
        }

        if ((gameState[0]==playerNum && gameState[4]==playerNum && gameState[8]==playerNum)
                || (gameState[2] == playerNum && gameState[4] == playerNum && gameState[6] == playerNum)) {
                return true;
        }
        else return false;
    }



    public void dropIn (View view){
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState [tappedCounter-1] == 0) {
            gameState[tappedCounter - 1] = playerNum;
            counter.setTranslationY(-1000f);
            if (playerNum == 1) {
                counter.setImageResource(R.drawable.red);
                counter.animate().translationYBy(1000f).setDuration(500);
                if (isWinner(gameState,playerNum)){

                    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
                    GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
                    TextView winningMessage = (TextView) findViewById(R.id.winningMessage);
                    winningMessage.setText("The winner is Player 1!");
                    layout.setVisibility(View.VISIBLE);
                    gridLayout.setAlpha(0.3f);
                    Toast.makeText(getApplicationContext(), "Player 1 is the winner!!!", Toast.LENGTH_LONG).show();
                }
                if (!isWinner(gameState,playerNum) && isDraw(gameState)){
                    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
                    GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
                    TextView winningMessage = (TextView) findViewById(R.id.winningMessage);
                    winningMessage.setText("The Game was a DRAW!!!");
                    layout.setVisibility(View.VISIBLE);
                    gridLayout.setAlpha(0.3f);
                    Toast.makeText(getApplicationContext(), "TIE!!!!", Toast.LENGTH_LONG).show();
                }
                playerNum = 2;
                System.out.println(counter.getTag().toString());


            } else {
                counter.setImageResource(R.drawable.yellow);
                counter.animate().translationYBy(1000f).setDuration(500);
                if (isWinner(gameState,playerNum)){

                    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
                    GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
                    TextView winningMessage = (TextView) findViewById(R.id.winningMessage);
                    winningMessage.setText("The winner is Player 2!");
                    layout.setVisibility(View.VISIBLE);
                    gridLayout.setAlpha(0.3f);
                    Toast.makeText(getApplicationContext(), "Player 2 is the winner!!!", Toast.LENGTH_LONG).show();
                }
                if (!isWinner(gameState,playerNum) && isDraw(gameState)){
                    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
                    GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
                    TextView winningMessage = (TextView) findViewById(R.id.winningMessage);
                    winningMessage.setText("The Game was a DRAW!!!");
                    layout.setVisibility(View.VISIBLE);
                    gridLayout.setAlpha(0.3f);
                    Toast.makeText(getApplicationContext(), "TIE!!!", Toast.LENGTH_LONG).show();
                }
                playerNum = 1;
                System.out.println(counter.getTag().toString());
            }
        } else{
            Toast.makeText(getApplicationContext(), "Tap somewhere else!", Toast.LENGTH_LONG).show();
        }
    }

    public void playAgain (View view){
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
        layout.setVisibility(View.INVISIBLE);
        for (int i =0; i<9; i++){
            gameState [i] = 0;
        }
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        gridLayout.setAlpha(1f);
        for (int i =0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
