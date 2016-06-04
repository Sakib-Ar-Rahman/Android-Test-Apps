package com.sakibarrahman.whatstheweatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityEditText;
    Button findWeather;
    TextView weatherTextView;
    String result;


    public void findWeather (View view){

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityEditText.getWindowToken(), 0);


        if(cityEditText.getText().toString().matches("Enter City Name e.g. London")){
            weatherTextView.setText("Enter a city name and try again.");
        }
        else{

            DownloadTask task = new DownloadTask();
            try {
                String encodedCity = URLEncoder.encode(cityEditText.getText().toString(), "UTF-8");
                result = task.execute("http://api.openweathermap.org/data/2.5/weather?q="+encodedCity+"&APPID=3beaf56e07bd3596c1ef8fb6b4fbbb6c").get();
                //Log.i ("result: ", result);


            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not get weather. Enter a valid city name.",Toast.LENGTH_SHORT);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not get weather. Enter a valid city name.",Toast.LENGTH_SHORT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not get weather. Enter a valid city name.",Toast.LENGTH_SHORT);
            }

        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not get weather. Enter a valid city name.",Toast.LENGTH_SHORT);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                String message = "";
                String main = "";
                String description = "";

                JSONObject jsonObject = new JSONObject(result);
                String weatherResult = jsonObject.getString("weather");
                JSONArray jsonArray = new JSONArray(weatherResult);

                for (int i =0; i<jsonArray.length(); i++){
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");

                    if (main != "" && description != ""){
                        message += main + " : " + description + "\r\n";
                    }
                }

                if(message != ""){
                    weatherTextView.setText(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                weatherTextView.setText("Could not get weather. Enter a valid city name.");
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = (EditText) findViewById(R.id.cityEditText);
        weatherTextView = (TextView) findViewById(R.id.weatherTextView);



    }
}
