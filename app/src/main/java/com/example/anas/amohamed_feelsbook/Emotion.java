package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public abstract class Emotion {
    public Date date = new Date();
    private String comment = "";
    private static int counter;

    public int getCount(){
        return counter;
    }

    public void incrementCount(){
        counter++;
    }

    public void decrementCount(){
        counter = counter - 1;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return this.date;
    }

    public void setComment (String comment){
        this.comment = comment;
    }

    public String getComment(){
        return this.comment;
    }

    @Override
    public String toString() {
        return getFeel() + "\n" + comment + "\n" + date.toString();
    }

    public abstract String getFeel();

}
