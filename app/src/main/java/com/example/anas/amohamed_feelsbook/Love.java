package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Love extends Emotion {
    private static int count = 0;
    public Love(Date date, String comment){
        setDate(date);
        setComment(comment);
        count++;
    }

    public Love(String comment){
        setComment(comment);
        count++;
    }

    public Love(){
        count++;
    }

    public String getFeel(){
        return "Love";
    }
}
