package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public class Fear extends Emotion {
    private static int count;
    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void incrementCount(){
        count++;
    }

    public void decrementCount(){
        count--;
    }

    public String getFeel(){
        return "(⊙＿⊙) Fear";
    }
}
