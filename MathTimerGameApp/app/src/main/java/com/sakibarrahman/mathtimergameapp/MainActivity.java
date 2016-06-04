package com.sakibarrahman.mathtimergameapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;




public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView mathTextView;;
    int correctAnswerLoc;
    TextView scoreTextView;
    TextView resultsTextView;
    TextView timerTextView;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int score, turns, a, b;
    Random rand = new Random();
    Button button0, button1, button2, button3;
    GridLayout gridLayout;
    boolean isGameActive = true;


    public void setNewMath (){
        a = rand.nextInt(20);
        b = rand.nextInt(20);
        correctAnswerLoc = rand.nextInt(4);
        answers.clear();

        mathTextView.setText(Integer.toString(a)+" + "+ Integer.toString(b));

        for (int i=0; i<4; i++){
            if(i== correctAnswerLoc){
                answers.add (a+b);

            }
            else{
                int incorrectAns = rand.nextInt(40);
                while (incorrectAns == (a+b)){
                    incorrectAns = rand.nextInt(40);
                }
                answers.add(incorrectAns);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        Log.i ("button1:", Integer.toString(answers.get(0)));
        Log.i ("button2:", Integer.toString(answers.get(1)));
        Log.i ("button3:", Integer.toString(answers.get(2)));
        Log.i ("button4:", Integer.toString(answers.get(3)));
    }



    public void chooseAnswer (View view){
        if (isGameActive){
            Log.i ("button number pressed:", view.getTag().toString());
            turns++;

            if (view.getTag().equals(Integer.toString(correctAnswerLoc))){
                score++;
                resultsTextView.setText("Correct!");
            }else{
                resultsTextView.setText("Wrong!");
            }

            scoreTextView.setText(Integer.toString(score)+"/"+ Integer.toString(turns));
            setNewMath();
        }

    }



    public void onStart(View view){
        startButton.setVisibility(View.INVISIBLE);
        isGameActive = true;
        score = 0;
        turns = 0;
        gridLayout.setAlpha(1f);
        mathTextView.setAlpha(1f);
        timerTextView.setAlpha(1f);
        scoreTextView.setAlpha(1f);
        mathTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        resultsTextView.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        setNewMath();
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultsTextView.setText("GO!");
        new CountDownTimer(30000+100, 1000){
            @Override
            public void onTick(long l) {
                if (l <10){
                    timerTextView.setText("0"+Long.toString(l/1000)+"s");
                }
                else {
                    timerTextView.setText(Long.toString(l/1000)+"s");
                }
            }

            @Override
            public void onFinish() {
                resultsTextView.setText("Your final score is "+ Integer.toString(score)+"!!!");
                timerTextView.setText("0s");
                gridLayout.setAlpha(0.3f);
                mathTextView.setAlpha(0.3f);
                timerTextView.setAlpha(0.3f);
                scoreTextView.setAlpha(0.3f);
                startButton.setText("Play Again!");
                startButton.setVisibility(View.VISIBLE);
                isGameActive = false;

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        mathTextView = (TextView) findViewById(R.id.mathTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        score = 0;
        turns = 0;
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        startButton.setVisibility(View.VISIBLE);
        mathTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        resultsTextView.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        isGameActive =false;

        setNewMath();

//        new CountDownTimer(30000+100, 1000){
//            @Override
//            public void onTick(long l) {
//                if (l <10){
//                    timerTextView.setText("0"+Long.toString(l/1000)+"s");
//                }
//                else {
//                    timerTextView.setText(Long.toString(l/1000)+"s");
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                resultsTextView.setText("Your final score is "+ Integer.toString(score)+"!!!");
//                timerTextView.setText("0s");
//                gridLayout.setAlpha(0.3f);
//                mathTextView.setAlpha(0.3f);
//                timerTextView.setAlpha(0.3f);
//                scoreTextView.setAlpha(0.3f);
//                startButton.setText("Play Again!");
//                startButton.setVisibility(View.VISIBLE);
//
//            }
//        }.start();

    }
}