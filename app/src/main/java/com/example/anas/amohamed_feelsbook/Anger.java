package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Anger extends Emotion {
    private static int count = 0;
    public Anger(Date date, String comment){
        setDate(date);
        setComment(comment);
    }

    public Anger(String comment){
        setComment(comment);
    }

    public Anger(){
        count++;
    }

    public String getFeel(){
        return "Anger";
    }
}
