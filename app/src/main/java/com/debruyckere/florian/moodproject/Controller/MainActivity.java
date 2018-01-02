package com.debruyckere.florian.moodproject.Controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.debruyckere.florian.moodproject.Model.Emotion;
import com.debruyckere.florian.moodproject.Model.EmotionType;
import com.debruyckere.florian.moodproject.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    Button mCommentButton ;
    Button mHistoryButton ;
    Emotion mEmotion= new Emotion();
    EmotionType mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addEmotion(){
        mEmotion.setEmote(mType);
    }
    public void EmotionSetText(String pText){
        mEmotion.setComment(pText);
    }
}
