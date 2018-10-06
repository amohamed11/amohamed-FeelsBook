/*      MIT License

        Copyright (c) 2018 Anas Mohamed amohamed@ualberta.ca

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
*/

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
import java.util.HashMap;
import java.util.Locale;

public class FeelsBookActivity extends Activity implements View.OnClickListener {
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

    private HashMap<String, Integer> countSave = new HashMap<>();

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

        // Switch in and out between the drawer and the list otherwise one of them does not work
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
    protected void onActivityResult(int requestCode, int code, Intent intent)  {
        if (requestCode == 2) {
            if(code == RESULT_OK){
                Emotion updatedEmotion = (Emotion) intent.getSerializableExtra("updateEmotion");
                if (!updatedEmotion.getComment().equals("remove me")) {
                    try {
                        Date newDate = new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm", Locale.CANADA).parse(updatedEmotion.getDate());
                        emotionList.get(lastPosition).setDate(newDate);
                        if (updatedEmotion.getComment().length() > 100){
                            enterComment.getText().clear();
                            enterComment.setHint("Oops, try again. Maximum: 100 Characters.");
                        }else{
                            emotionList.get(lastPosition).setComment(updatedEmotion.getComment());
                            emotionAdapter.notifyDataSetChanged();
                            saveInFile(emotionList);
                        }
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


    // Sets the stringCount to the current count of each emotion
    private void updateCount() {
        createCounter();
        Fear fear = new Fear();
        stringCount.set(0, "Fear: " + Integer.toString(fear.getCount()));

        Joy joy = new Joy();
        stringCount.set(1, "Joy: " + Integer.toString(joy.getCount()));

        Love love = new Love();
        stringCount.set(2, "Love: " + Integer.toString(love.getCount()));

        Anger anger = new Anger();
        stringCount.set(3, "Anger: " + Integer.toString(anger.getCount()));

        Sad sad = new Sad();
        stringCount.set(4, "Sad: " + Integer.toString(sad.getCount()));

        Surprise surprise = new Surprise();
        stringCount.set(5, "Surprise: " + Integer.toString(surprise.getCount()));

    }

    //Loads the HashMap from the save into stringCount
    private void reloadCount(){
        Fear fear = new Fear();
        fear.setCount(countSave.get("Fear"));
        Joy joy = new Joy();
        joy.setCount(countSave.get("Joy"));
        Love love = new Love();
        love.setCount(countSave.get("Love"));
        Anger anger = new Anger();
        anger.setCount(countSave.get("Anger"));
        Sad sad = new Sad();
        sad.setCount(countSave.get("Sad"));
        Surprise surprise = new Surprise();
        surprise.setCount(countSave.get("Surprise"));
        updateCount();
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

    //Handles all the button presses
    @Override
    public void onClick(View v) {
        comment = enterComment.getText().toString();
        enterComment.getText().clear();
        if (comment.length() > 100){
            enterComment.setHint("Oops, try again. Maximum: 100 Characters.");
            comment = "";
        }
            switch (v.getId()) {
                case R.id.fearButton:
                    Fear fear = new Fear();
                    fear.setComment(comment);
                    fear.incrementCount();
                    emotionList.add(fear);
                    break;

                case R.id.joyButton:
                    Joy joy = new Joy();
                    joy.setComment(comment);
                    joy.incrementCount();
                    emotionList.add(joy);
                    break;

                case R.id.loveButton:
                    Love love = new Love();
                    love.setComment(comment);
                    love.incrementCount();
                    emotionList.add(love);
                    break;

                case R.id.angerButton:
                    Anger anger = new Anger();
                    anger.setComment(comment);
                    anger.incrementCount();
                    emotionList.add(anger);

                    break;

                case R.id.sadButton:
                    Sad sad = new Sad();
                    sad.setComment(comment);
                    sad.incrementCount();
                    emotionList.add(sad);
                    break;

                case R.id.surpriseButton:
                    Surprise surprise = new Surprise();
                    surprise.setComment(comment);
                    surprise.incrementCount();
                    emotionList.add(surprise);
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

    //Loads all the saved data
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
                loadCount();
                Collections.sort(emotionList);
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // Specifically loads the saved counter
    private void loadCount(){
        try{
            this.sharedPref = getSharedPreferences("counters", MODE_PRIVATE);
            String savedCount= this.sharedPref.getString("count", "");
            if (savedCount.equals("")) {
                createCounter();
                countSave = new HashMap<String, Integer>();
            } else {
                ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(savedCount, Base64.DEFAULT));
                ObjectInputStream oi = new ObjectInputStream(bi);
                countSave = (HashMap<String, Integer>) oi.readObject();
                reloadCount();
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void saveCount(){
        try{
            this.sharedPref = getSharedPreferences("counters", MODE_PRIVATE);
            editor = this.sharedPref.edit();

            Fear fear = new Fear();
            countSave.put("Fear", fear.getCount());
            Joy joy = new Joy();
            countSave.put("Joy", joy.getCount());
            Love love = new Love();
            countSave.put("Love", love.getCount());
            Anger anger = new Anger();
            countSave.put("Anger", anger.getCount());
            Sad sad = new Sad();
            countSave.put("Sad", sad.getCount());
            Surprise surprise = new Surprise();
            countSave.put("Surprise", surprise.getCount());

            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(countSave);
            oo.close();
            byte bytes[] = bo.toByteArray();

            editor.putString("count", Base64.encodeToString(bytes,Base64.DEFAULT));
            editor.apply();

        } catch (IOException e){
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
            oo.close();
            byte bytes[] = bo.toByteArray();

            editor.putString("emotions", Base64.encodeToString(bytes,Base64.DEFAULT));
            editor.apply();
            saveCount();


        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
