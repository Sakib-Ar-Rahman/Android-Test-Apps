package com.sakibarrahman.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String > celebURLs = new ArrayList<String >();
    ArrayList<String > celebNames = new ArrayList<String >();
    int chosenCeleb = 0, chosenButton =0;
    ImageView imageView;
    Button button0, button1, button2, button3;
    String[] answers = new String[4];




    public void newQuestion () {
        Random random = new Random();
        chosenCeleb = random.nextInt(celebURLs.size());

        //SETTING UP IMAGE TASK TO GET THE IMAGES
        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage = null;
        imageView = (ImageView) findViewById(R.id.imageView);

        try {
            celebImage = imageTask.execute(celebURLs.get(chosenCeleb)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(celebImage);


        //SETTING UP BUTTONS
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        chosenButton = random.nextInt(4);

        for (int i =0;  i<4; i++){
            if (i==chosenButton){
                answers[i] = celebNames.get(chosenCeleb);
            }
            else{
                int incorrectIndex = random.nextInt(celebNames.size());
                while (incorrectIndex == chosenCeleb) {
                    incorrectIndex = random.nextInt(celebNames.size());
                }
                answers [i] = celebNames.get(incorrectIndex);
            }
        }

        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);

    }






    public void celebChosen (View view){
        if (view.getTag().toString().equals(Integer.toString(chosenButton))){
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong, the correct answer was "+
                    celebNames.get(chosenCeleb)+"!", Toast.LENGTH_SHORT).show();

        }
        newQuestion();
        Log.i ("Button numeber:", view.getTag().toString());
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);

                return myBitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;                 //check back later. maybe result += result
                    data = reader.read();
                }
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("http://www.posh24.com/celebrities").get();
            // Log.i ("Contents of URL",result);

            String[] splitResult = result.split("<div class=\"sidebarContainer\"");

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebURLs.add(m.group(1));
            }


            p = Pattern.compile("alt=\"(.*?)\"/");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        newQuestion();
    }
}
