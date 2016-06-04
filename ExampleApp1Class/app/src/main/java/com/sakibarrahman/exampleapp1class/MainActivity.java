package com.sakibarrahman.exampleapp1class;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class number {
        int num;

        public boolean isTriangular(){
            if (num == 1){
                return true;
            }
            else{
                int y =1;
                int tnum =1;
                while (tnum < num) {
                    y++;
                    tnum = tnum + y;
                }

                if (num==tnum){
                    return true;
                }
                else return false;
            }

        }

        public boolean isSquare(){
            if (num == 1){
                return true;
            }
            else {
                if ((Math.sqrt(num)) % 1 == 0)
                    return true;
                return false;
            }

        }
    }



    public void onClick (View view){
        EditText numberField = (EditText) findViewById(R.id.numberField);
        number inputNumber = new number();
        String message = "";

        if ( numberField.getText().toString().matches("")){
            message = "Enter a number";
        }
        else {
            inputNumber.num = Integer.parseInt(numberField.getText().toString());

            if (inputNumber.isSquare() && inputNumber.isTriangular()){
                message = "Number is both square and triangular";
            }
            else if (inputNumber.isSquare() && !inputNumber.isTriangular()){
                message = "Number is only square";
            }
            else if (!inputNumber.isSquare() && inputNumber.isTriangular()){
                message = "Number is only triangular";
            }
            else{
                message = "Number is neither";
            }
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
