package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Sad extends Emotion {
    private static int count = 0;
    public Sad(Date date, String comment){
        setDate(date);
        setComment(comment);
        count++;
    }

    public Sad(String comment){
        setComment(comment);
        count++;
    }

    public Sad(){
        count++;
    }

    public String getFeel(){
        return "Sad";
    }
}
