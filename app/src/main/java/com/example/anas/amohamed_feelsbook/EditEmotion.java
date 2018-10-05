package com.example.anas.amohamed_feelsbook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EditEmotion extends Activity {
    private int year, month, day, hour, second;
    private Date newDate;
    protected TextView emotionText;
    protected TextView editDate;
    protected EditText editComment;

    Emotion selectedEmotion;

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



        String newComment = editComment.getText().toString();
        selectedEmotion.setComment(newComment);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                selectedEmotion.setComment("remove me");
                Intent intent = new Intent();
                intent.putExtra("updateEmotion", selectedEmotion);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                selectedEmotion.setComment(editComment.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("updateEmotion", selectedEmotion);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditDate.class);
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