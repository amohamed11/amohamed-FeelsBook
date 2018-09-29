package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Sad extends Emotion {
    public void Sad(Date date, String comment){
        setDate(date);
        setComment(comment);
    }

    public void Sad(String comment){
        setComment(comment);
    }

    public void Sad(){}

    public void setFeel(){
        this.feel = "Sad";
    }
}
