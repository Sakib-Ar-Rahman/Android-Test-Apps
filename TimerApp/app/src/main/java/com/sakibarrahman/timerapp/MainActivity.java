package com.sakibarrahman.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button button;
    CountDownTimer countDownTimer;

    public void updateTimer (int i){
        int minutes = i/60;
        int seconds = i- minutes*60;
        String secondsString = Integer.toString(seconds);
        if (seconds<10){
            secondsString = "0"+Integer.toString(seconds);
        }

        timerTextView.setText(Integer.toString(minutes)+":"+secondsString);
    }

    boolean buttonCheck = false;        //false: timer stopped, button says start
    public void onClick (View view){

        if (!buttonCheck){
            button = (Button) findViewById(R.id.button);
            button.setText("Stop");
            buttonCheck =true;
            timerSeekBar.setEnabled(false);

            countDownTimer= new CountDownTimer(timerSeekBar.getProgress()*1000 + 100, 1000){
                @Override
                public void onTick(long l) {
                    updateTimer((int) l/1000);

                }
                @Override
                public void onFinish() {
                    Log.i ("fini", "Timer has finished");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();
                    countDownTimer.cancel();
                    button.setText("Start");
                    buttonCheck = false;
                    timerSeekBar.setEnabled(true);
                    timerTextView.setText("0:00");
                }
            }.start();

        }else{
            buttonCheck = false;
            button = (Button) findViewById(R.id.button);
            button.setText("Start");
            timerSeekBar.setEnabled(true);
            timerTextView.setText("0:00");
            countDownTimer.cancel();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.seekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(0);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minutes = i/60;
                int seconds = i- minutes*60;
                String secondsString = Integer.toString(seconds);
                if (seconds<10){
                    secondsString = "0"+Integer.toString(seconds);
                }

                timerTextView.setText(Integer.toString(minutes)+":"+secondsString);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
