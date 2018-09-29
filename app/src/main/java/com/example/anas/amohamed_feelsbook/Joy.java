package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Joy extends Emotion {
    private static int count = 0;
    public Joy(Date date, String comment){
        setDate(date);
        setComment(comment);
        count++;
    }

    public Joy(String comment){
        setComment(comment);
        count++;
    }

    public Joy(){
        count++;
    }

    public String getFeel(){
        return "Joy";
    }
}


