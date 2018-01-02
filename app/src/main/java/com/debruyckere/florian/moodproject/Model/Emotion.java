package com.debruyckere.florian.moodproject.Model;

/**
 * Created by Debruyckère Florian on 01/01/2018.
 */

public class Emotion {
    private String mComment;
    private EmotionType mEmote;

    public Emotion(EmotionType pEmote){
        mEmote = pEmote;
    }
    public Emotion(){ }

    public void setComment(String pText){
        mComment = pText;
    }
    public void setEmote(EmotionType pEmote){
        mEmote = pEmote;
    }

    public String getComment() {
        return mComment;
    }

    public EmotionType getEmote() {
        return mEmote;
    }

    @Override
    public String toString() {
        return "Emotion{" +
                "mComment='" + mComment + '\'' +
                ", mEmote=" + mEmote +
                '}';
    }
}
