package com.example.anas.amohamed_feelsbook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EditEmotion extends Activity {
    private int year, month, day, hour, second;
    protected TextView emotionText;
    protected TextView editDate;
    protected EditText editComment;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.CANADA);

    Emotion selectedEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_emotion);
        selectedEmotion = (Emotion) getIntent().getSerializableExtra("selected");


        Button saveButton = findViewById(R.id.saveButton);
        Button dateButton = findViewById(R.id.dateButton);

        emotionText = findViewById(R.id.emotionText);
        emotionText.setText(selectedEmotion.getFeel());

        editDate = findViewById(R.id.editDate);
        editDate.setText(selectedEmotion.getDate().toString());

        editComment = findViewById(R.id.editComment);;
        editComment.setText(selectedEmotion.getComment());



        String newComment = editComment.getText().toString();
        selectedEmotion.setComment(newComment);


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(getApplicationContext());
                TimePicker timePicker = new TimePicker(getApplicationContext());
                Calendar calendar = new GregorianCalendar(
                        datePicker.getDayOfMonth(),
                        datePicker.getMonth(),
                        datePicker.getYear(),
                        timePicker.getHour(),
                        timePicker.getMinute());
                selectedEmotion.setDate(calendar.getTime());
                editDate.setText(calendar.getTime().toString());
            }
        });

    }
}