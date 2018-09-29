package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Surprise extends Emotion {
    private static int count = 0;
    public Surprise(Date date, String comment){
        setDate(date);
        setComment(comment);
        count++;
    }

    public Surprise(String comment){
        setComment(comment);
        count++;
    }

    public Surprise(){
        count++;
    }

    public String getFeel(){
        return "Surprise";
    }
}

