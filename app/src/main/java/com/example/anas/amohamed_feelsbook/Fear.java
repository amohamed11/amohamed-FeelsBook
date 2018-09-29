package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Fear extends Emotion {
    private int count = 0;
    public Fear(Date date, String comment){
        setDate(date);
        setComment(comment);
        count++;
    }

    public Fear(String comment){
        setComment(comment);
        count++;
    }

    public Fear(){
        count++;
    }

    public String getFeel(){
        return "Fear";
    }
}
