package com.example.anas.amohamed_feelsbook;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditEmotionActivity extends Activity {
    private Date newDate;
    protected TextView emotionText;
    protected TextView editDate;
    protected EditText editComment;

    protected Emotion selectedEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_emotion);
        selectedEmotion = (Emotion) getIntent().getSerializableExtra("selected");


        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button dateButton = findViewById(R.id.dateButton);

        emotionText = findViewById(R.id.emotionText);
        emotionText.setText(selectedEmotion.getFeel());

        editDate = findViewById(R.id.editDate);
        editDate.setText(selectedEmotion.getDate().toString());

        editComment = findViewById(R.id.editComment);;
        editComment.setText(selectedEmotion.getComment());

        // Handles going back after deletion
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                selectedEmotion.setComment("remove me");
                Intent intent = new Intent();
                intent.putExtra("updateEmotion", selectedEmotion);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Handles going back after editing the emotion
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String newComment = editComment.getText().toString();
                if (newComment.length() > 100){
                    editComment.setHint("Oops, try again. Maximum: 100 Characters.");
                }else {
                    selectedEmotion.setComment(newComment);
                    Intent intent = new Intent();
                    intent.putExtra("updateEmotion", selectedEmotion);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        //Calls the EditDate activity to handle date and time input
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditDateActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int code, Intent intent) {

        if (requestCode == 1) {
            if(code == RESULT_OK){
                String stringExtra = intent.getStringExtra("newDate");
                try {
                    newDate =new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm", Locale.CANADA).parse(stringExtra);
                    editDate.setText(stringExtra);
                    selectedEmotion.setDate(newDate);
                }catch (ParseException e){
                    e.printStackTrace();
                }

            }
        }
    }
}