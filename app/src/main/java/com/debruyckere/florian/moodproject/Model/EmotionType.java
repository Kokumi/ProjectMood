package com.debruyckere.florian.moodproject.Model;

/**
 * Created by Debruyckère Florian on 05/01/2018.
 */

public enum EmotionType {
    VeryBad ("VeryBad"),
    Bad ("Bad"),
    Normal("Normal"),
    Good("Good"),
    Great("Great"),
    NoData("NoData");

    private String emoteName = "";

    EmotionType(String pName){
        this.emoteName = pName;
    }
    public String toString(){
        return emoteName;
    }
}
