package com.example.anas.amohamed_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditDateActivity extends Activity {
    private int day, month, year, hour, minute, saveClicked = 0;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_date);

        final Button saveButton = findViewById(R.id.dateSaveButton);

        final DatePicker datePicker = findViewById(R.id.date_picker);
        final TimePicker timePicker = findViewById(R.id.time_picker);
        timePicker.setVisibility(View.GONE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (saveClicked == 0){
                    day = datePicker.getDayOfMonth();
                    month = datePicker.getMonth();
                    year = datePicker.getYear();

                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.VISIBLE);
                    timePicker.bringToFront();

                    saveClicked++;
                    saveButton.setText("Set Time");
                }else {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                    calendar.set(year, month, day, hour, minute);

                    Intent intent = new Intent();
                    Date date = calendar.getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm" , Locale.CANADA);
                    intent.putExtra("newDate", df.format(date));
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }
}