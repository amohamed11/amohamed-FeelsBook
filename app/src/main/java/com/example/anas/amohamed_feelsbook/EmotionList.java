package com.example.anas.amohamed_feelsbook;

import java.util.ArrayList;

/**
 * Created by anas on 9/28/18.
 */

public class EmotionList {
    private ArrayList<Emotion> emotionList = new ArrayList<Emotion>();

    public void add(Emotion emotion){
        emotionList.add(emotion);
        emotion.incrementCount();
        notifyAll();
    }

    public ArrayList<Emotion> getList(){
        return emotionList;
    }

    public int getEmotionCount(Emotion emotion){
        return emotion.getCount();
    }

    public void deleteEmotion(int index){
        emotionList.remove(index);
        Emotion e = emotionList.get(index);
        e.decrementCount();
        notifyAll();
    }



}
