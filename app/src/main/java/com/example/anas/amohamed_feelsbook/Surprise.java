package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Surprise extends Emotion {
    private static int count;
    public int getCount(){
        return count;
    }

    public void incrementCount(){
        count++;
    }

    public void decrementCount(){
        count--;
    }

    public String getFeel(){
        return "(・о・) Surprise";
    }
}

