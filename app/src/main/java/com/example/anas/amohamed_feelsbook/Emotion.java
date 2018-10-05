package com.example.anas.amohamed_feelsbook;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public abstract class Emotion implements Serializable {
    public Date date = new Date();
    private String comment = "";

    public Emotion(Date date, String comment){
        setDate(date);
        setComment(comment);
    }

    public Emotion(String comment){
        setComment(comment);
    }

    public Emotion(){};

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return this.date;
    }

    public void setComment (String comment){
        if (comment.length() > 100){
            throw new CommentTooLongException();
        }else {
            this.comment = comment;
        }
    }

    public String getComment() {
        return this.comment;
    }

    public String toString() {
        return getFeel() + " | Count: " + Integer.toString(getCount()) + "\n" + this.date.toString() + " | " +  this.comment;
    }

    public abstract String getFeel();
    public abstract int getCount();
    public abstract void incrementCount();
    public abstract void decrementCount();

}
