package com.example.anas.amohamed_feelsbook;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anas on 9/28/18.
 */

public abstract class Emotion implements Serializable, Comparable<Emotion> {
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

    public String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm", Locale.CANADA);
        return df.format(this.date);
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
        return getFeel() + "\n" + getDate() + " | " +  this.comment;
    }

    //Used for sorting comparisons
    @Override
    public int compareTo(Emotion emotion) {
        Date otherDate = new Date();
        try {
            otherDate =new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm", Locale.CANADA).parse(emotion.getDate());
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date.compareTo(otherDate);
    }


    // These are for the extended instances to handle
    public abstract String getFeel();
    public abstract int getCount();
    public abstract void incrementCount();
    public abstract void decrementCount();

}
