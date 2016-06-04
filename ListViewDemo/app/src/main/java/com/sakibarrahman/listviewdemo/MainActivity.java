package com.sakibarrahman.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView myListView = (ListView) findViewById(R.id.myListView);

        final ArrayList <String > friends = new ArrayList<String>();
        friends.add("Ariff");
        friends.add("Zaheen");
        friends.add("Niha");
        friends.add("Saadman");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);

        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.i("PersonTapped", friends.get(i));
                Toast.makeText(getApplicationContext() ,"Hello "+ friends.get(i), Toast.LENGTH_SHORT ).show();
            }
        });

    }
}
