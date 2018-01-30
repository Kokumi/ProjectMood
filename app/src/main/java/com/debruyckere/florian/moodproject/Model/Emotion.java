package com.debruyckere.florian.moodproject.Model;

import java.util.Date;

/**
 * Created by Debruyckère Florian on 01/01/2018.
 */

public class Emotion {
    private String mComment;
    private EmotionType mEmote;
    private Date mDate;

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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "Emotion{" +
                "mComment='" + mComment + '\'' +
                ", mEmote=" + mEmote +
                '}';
    }
}
