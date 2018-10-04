package com.example.anas.amohamed_feelsbook;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FeelsBookActivity extends Activity implements View.OnClickListener {
    // https://github.com/amohamed11/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
//    private static final String FILENAME = "file.sav";
    protected ListView listView;
    protected ArrayList<Emotion> emotionList;
    protected ArrayAdapter<Emotion> emotionAdapter;
    protected EditText editComment;
    protected String comment = "";

    protected ListView drawerView;
    protected ArrayList<String> stringCount;
    protected ArrayAdapter<String> drawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);
        stringCount = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.emotion_list);
        drawerView = (ListView) findViewById(R.id.count_bar);

        stringCount.add("Fear: 0");
        stringCount.add("Joy: 0");
        stringCount.add("Love: 0");
        stringCount.add("Anger: 0");
        stringCount.add("Sad: 0");
        stringCount.add("Surprised: 0");

        emotionList = new ArrayList<Emotion>();
        emotionAdapter = new ArrayAdapter<Emotion>(this, android.R.layout.simple_list_item_1, emotionList);
        listView.setAdapter(emotionAdapter);

        drawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringCount);
        drawerView.setAdapter(drawerAdapter);


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

        editComment = (EditText) findViewById(R.id.editComment);

    }

    @Override
    public void onClick(View v){
        comment = editComment.getText().toString();
        editComment.getText().clear();
        switch (v.getId()){

            case R.id.fearButton:
                Fear fear = new Fear();
                if (!comment.matches("")){
                    fear.setComment(comment);
                }
                emotionList.add(fear);
                fear.incrementCount();
                stringCount.set(0, "Fear: " + Integer.toString(fear.getCount()));
                break;

            case R.id.joyButton:
                Joy joy = new Joy();
                if (!comment.matches("")){
                    joy.setComment(comment);
                }
                emotionList.add(joy);
                joy.incrementCount();
                stringCount.set(1, "Joy: " + Integer.toString(joy.getCount()) );
                break;

            case R.id.loveButton:
                Love love = new Love();
                if (!comment.matches("")){
                    love.setComment(comment);
                }
                emotionList.add(love);
                love.incrementCount();
                stringCount.set(2, "Love: " + Integer.toString(love.getCount()) );
                break;

            case R.id.angerButton:
                Anger anger= new Anger();
                if (!comment.matches("")){
                    anger.setComment(comment);
                }
                emotionList.add(anger);
                anger.incrementCount();
                stringCount.set(3, "Anger: " + Integer.toString(anger.getCount()) );
                break;

            case R.id.sadButton:
                Sad sad= new Sad();
                if (!comment.matches("")){
                    sad.setComment(comment);
                }
                emotionList.add(sad);
                sad.incrementCount();
                stringCount.set(4, "Sad: " + Integer.toString(sad.getCount()) );
                break;

            case R.id.surpriseButton:
                Surprise surprise= new Surprise();
                if (!comment.matches("")){
                    surprise.setComment(comment);
                }
                emotionList.add(surprise);
                surprise.incrementCount();
                stringCount.set(5, "Surprise: " + Integer.toString(surprise.getCount()));
                break;

            default:
                break;
        }
        emotionAdapter.notifyDataSetChanged();
        drawerAdapter.notifyDataSetChanged();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        loadFromFile();
//        emotionAdapter = new ArrayAdapter<Emotion>(this, android.R.layout.simple_list_item_1, emotionList);
//        listView.setAdapter(emotionAdapter);
//    }


//    private void loadFromFile() {
//        try {
//            FileInputStream fis = openFileInput(FILENAME);
//            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//            Gson gson = new Gson();
//            Type listType = new TypeToken<ArrayList<Emotion>>() {}.getType();
//            emotionList = gson.fromJson(in, listType);
//        } catch (FileNotFoundException e) {
//            emotionList = new ArrayList<Emotion>();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveInFile() {
//        try {
//            FileOutputStream fos = openFileOutput(FILENAME, 0);
//            OutputStreamWriter writer = new OutputStreamWriter(fos);
//            Gson gson = new Gson();
//            gson.toJson(emotionList, writer);
//            writer.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
