package com.example.anas.amohamed_feelsbook;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeelsBookActivity extends Activity implements View.OnClickListener {
    // https://github.com/amohamed11/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
//    private static final String FILENAME = "file.sav";
    protected ListView listView;
    protected ArrayList<Emotion> emotionList;
    protected ArrayAdapter<Emotion> emotionAdapter;
    protected EditText editComment;
    protected String comment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);
        emotionList = new ArrayList<Emotion>();
        listView = (ListView) findViewById(R.id.emotion_list);

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
                emotionAdapter.notifyDataSetChanged();
                break;
            case R.id.joyButton:
                Joy joy = new Joy();
                if (!comment.matches("")){
                    joy.setComment(comment);
                }
                emotionList.add(joy);
                joy.incrementCount();
                emotionAdapter.notifyDataSetChanged();
                break;
            case R.id.loveButton:
                Love love = new Love();
                if (!comment.matches("")){
                    love.setComment(comment);
                }
                emotionList.add(love);
                love.incrementCount();
                emotionAdapter.notifyDataSetChanged();
                break;
            case R.id.angerButton:
                Anger anger= new Anger();
                if (!comment.matches("")){
                    anger.setComment(comment);
                }
                emotionList.add(anger);
                anger.incrementCount();
                emotionAdapter.notifyDataSetChanged();
                break;
            case R.id.sadButton:
                Sad sad= new Sad();
                if (!comment.matches("")){
                    sad.setComment(comment);
                }
                emotionList.add(sad);
                sad.incrementCount();
                emotionAdapter.notifyDataSetChanged();
                break;
            case R.id.surpriseButton:
                Surprise surprise= new Surprise();
                if (!comment.matches("")){
                    surprise.setComment(comment);
                }
                emotionList.add(surprise);
                surprise.incrementCount();
                emotionAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        emotionAdapter = new ArrayAdapter<Emotion>(this, android.R.layout.simple_list_item_1, emotionList);
        listView.setAdapter(emotionAdapter);
    }

//    private void loadFromFile() {
//        try {
//            FileInputStream fis = openFileInput(FILENAME);
//            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//            Gson gson = new Gson();
//            // Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 2015-09-22
//            Type listType = new TypeToken<ArrayList<Emotion>>() {}.getType();
//            emotionList = gson.fromJson(in, listType);
//        } catch (FileNotFoundException e) {
//            emotionList = new ArrayList<Emotion>();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void saveInFile() {
//        try {
//            FileOutputStream fos = openFileOutput(FILENAME, 0);
//            OutputStreamWriter writer = new OutputStreamWriter(fos);
//            Gson gson = new Gson();
//            gson.toJson(emotionList);
//            writer.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
