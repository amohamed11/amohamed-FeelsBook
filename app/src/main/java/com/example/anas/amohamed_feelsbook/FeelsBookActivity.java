package com.example.anas.amohamed_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class FeelsBookActivity extends Activity implements View.OnClickListener {
    // https://github.com/amohamed11/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
//    private static final String FILENAME = "save.gson";
    private int lastPosition;
    private DrawerLayout drawerLayoutView;

    protected ListView listView;
    public ArrayList<Emotion> emotionList = new ArrayList<Emotion>();
    protected ArrayAdapter<Emotion> emotionAdapter;
    protected EditText enterComment;
    protected String comment = "";

    protected ListView drawerView;
    protected ArrayList<String> stringCount = new ArrayList<>();
    protected ArrayAdapter<String> drawerAdapter;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);

        createCounter();

        drawerView = (ListView) findViewById(R.id.count_bar);
        drawerLayoutView = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.emotion_list);


        loadFromFile();


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

        enterComment = (EditText) findViewById(R.id.enterComment);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastPosition = position;
                Emotion selectedEmotion = (Emotion)listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), EditEmotionActivity.class);
                intent.putExtra("selected", selectedEmotion);
                startActivityForResult(intent, 2);
            }
        });

        drawerLayoutView.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset > 0.7){
                    drawerLayoutView.bringToFront();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayoutView.bringToFront();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                listView.bringToFront();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int code, Intent intent) {
        if (requestCode == 2) {
            if(code == RESULT_OK){
                Emotion updatedEmotion = (Emotion) intent.getSerializableExtra("updateEmotion");
                if (!updatedEmotion.getComment().equals("remove me")) {
                    try {
                        Date newDate = new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm", Locale.CANADA).parse(updatedEmotion.getDate());
                        emotionList.get(lastPosition).setDate(newDate);
                        emotionList.get(lastPosition).setComment(updatedEmotion.getComment());
                        emotionAdapter.notifyDataSetChanged();
                        saveInFile(emotionList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    emotionList.get(lastPosition).decrementCount();
                    updateCount();
                    emotionList.remove(lastPosition);
                    emotionAdapter.notifyDataSetChanged();
                    saveInFile(emotionList);
                }
            }
        }
    }


    private void updateCount() {
        createCounter();
        for(int i=0; i < emotionList.size(); i++){
            if (emotionList.get(i) instanceof Fear){
                stringCount.set(0, "Fear: " + Integer.toString(emotionList.get(i).getCount()));
                Fear fear = new Fear();
                fear.setCount(emotionList.get(i).getCount());
            }
            if (emotionList.get(i) instanceof Joy){
                stringCount.set(1, "Joy: " + Integer.toString(emotionList.get(i).getCount()));
                Joy joy = new Joy();
                joy.setCount(emotionList.get(i).getCount());

            }
            if (emotionList.get(i) instanceof Love){
                stringCount.set(2, "Love: " + Integer.toString(emotionList.get(i).getCount()));
                Love love = new Love();
                love.setCount(emotionList.get(i).getCount());

            }
            if (emotionList.get(i) instanceof Anger){
                stringCount.set(3, "Anger: " + Integer.toString(emotionList.get(i).getCount()));
                Anger anger = new Anger();
                anger.setCount(emotionList.get(i).getCount());

            }
            if (emotionList.get(i) instanceof Sad){
                stringCount.set(4, "Sad: " + Integer.toString(emotionList.get(i).getCount()));
                Sad sad = new Sad();
                sad.setCount(emotionList.get(i).getCount());

            }
            if (emotionList.get(i) instanceof Surprise){
                stringCount.set(5, "Surprise: " + Integer.toString(emotionList.get(i).getCount()));
                Surprise surprise = new Surprise();
                surprise.setCount(emotionList.get(i).getCount());

            }
        }
        saveInFile(emotionList);
    }

    private void createCounter() {
        stringCount.clear();
        stringCount.add("Fear: 0");
        stringCount.add("Joy: 0" );
        stringCount.add("Love: 0");
        stringCount.add("Anger: 0");
        stringCount.add("Sad: 0");
        stringCount.add("Surprise: 0");

    }

    @Override
    public void onClick(View v){
        comment = enterComment.getText().toString();
        enterComment.getText().clear();
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
                break;

            case R.id.loveButton:
                Love love = new Love();
                if (!comment.matches("")){
                    love.setComment(comment);
                }
                emotionList.add(love);
                love.incrementCount();
                break;

            case R.id.angerButton:
                Anger anger= new Anger();
                if (!comment.matches("")){
                    anger.setComment(comment);
                }
                emotionList.add(anger);
                anger.incrementCount();
                break;

            case R.id.sadButton:
                Sad sad= new Sad();
                if (!comment.matches("")){
                    sad.setComment(comment);
                }
                emotionList.add(sad);
                sad.incrementCount();
                break;

            case R.id.surpriseButton:
                Surprise surprise= new Surprise();
                if (!comment.matches("")){
                    surprise.setComment(comment);
                }
                emotionList.add(surprise);
                surprise.incrementCount();
                break;

            default:
                break;
        }
        updateCount();
        emotionAdapter.notifyDataSetChanged();
        drawerAdapter.notifyDataSetChanged();
        saveInFile(emotionList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        emotionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, emotionList);
        listView.setAdapter(emotionAdapter);
        listView.bringToFront();
        drawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringCount);
        drawerView.setAdapter(drawerAdapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        emotionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop(){
        super.onStop();
        saveInFile(emotionList);
    }

    private void loadFromFile() {
        try {
            this.sharedPref = getSharedPreferences("EmotionList", MODE_PRIVATE);
            String savedEmotions= this.sharedPref.getString("emotions", "");
            if (savedEmotions.equals("")) {
                emotionList = new ArrayList<>();
            } else {
                ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(savedEmotions, Base64.DEFAULT));
                ObjectInputStream oi = new ObjectInputStream(bi);
                emotionList = (ArrayList<Emotion>) oi.readObject();
                Collections.sort(emotionList);
                updateCount();
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    private void saveInFile(ArrayList<Emotion> emotions){
        Collections.sort(emotionList);
        try {
            this.sharedPref = getSharedPreferences("EmotionList", MODE_PRIVATE);
            editor = this.sharedPref.edit();

            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(emotions);
            byte bytes[] = bo.toByteArray();

            editor.putString("emotions", Base64.encodeToString(bytes,Base64.DEFAULT));
            editor.apply();

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
