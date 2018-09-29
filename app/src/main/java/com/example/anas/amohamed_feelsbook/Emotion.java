package com.example.anas.amohamed_feelsbook;

import java.util.Date;

/**
 * Created by anas on 9/28/18.
 */

public abstract class Emotion {
    public Date date = new Date();
    public String comment = "";
    protected String feel;

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

    public abstract void setFeel();

}
