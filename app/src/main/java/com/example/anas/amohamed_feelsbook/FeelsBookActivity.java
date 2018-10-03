package com.example.anas.amohamed_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class FeelsBookActivity extends AppCompatActivity implements View.OnClickListener {
    // https://github.com/amohamed11/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    private ArrayList<Emotion> emotions = new ArrayList<Emotion>();
    private ArrayAdapter<Emotion> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);

        // Get all the buttons
        // https://stackoverflow.com/questions/25905086/multiple-buttons-onclicklistener-android
        Button fearButton = (Button) findViewById(R.id.fearButton);
        fearButton.setOnClickListener(this);
        Button joyButton = (Button) findViewById(R.id.joyButton);
        joyButton.setOnClickListener(this);
        Button loveButton = (Button) findViewById(R.id.loveButton);
        loveButton.setOnClickListener(this);
        Button angerButton = (Button) findViewById(R.id.angerButton);
        angerButton.setOnClickListener(this);
        Button sadButton = (Button) findViewById(R.id.sadButton);
        sadButton.setOnClickListener(this);
        Button surpriseButton = (Button) findViewById(R.id.surpriseButton);
        surpriseButton.setOnClickListener(this);

        }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fearButton:
                Fear fear = new Fear();
                emotions.add(fear);
                break;
            case R.id.joyButton:
                Joy joy = new Joy();
                emotions.add(joy);
                break;
            case R.id.loveButton:
                Love love = new Love();
                emotions.add(love);
                break;
            case R.id.angerButton:
                Anger anger= new Anger();
                emotions.add(anger);
                break;
            case R.id.sadButton:
                Sad sad= new Sad();
                emotions.add(sad);
                break;
            case R.id.surpriseButton:
                Surprise surprise= new Surprise();
                emotions.add(surprise);
                break;
        }

    }
}
