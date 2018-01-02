package com.debruyckere.florian.moodproject.Controller;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.debruyckere.florian.moodproject.Model.Emotion;
import com.debruyckere.florian.moodproject.Model.EmotionType;
import com.debruyckere.florian.moodproject.R;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private Button mCommentButton ;
    private Button mHistoryButton ;
    private ImageView mImageView;
    private Emotion mEmotion= new Emotion();
    private EmotionType mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCommentButton =findViewById(R.id.main_comment_button);
        mHistoryButton = findViewById(R.id.main_history_button);
        mImageView = findViewById(R.id.main_emote_image);
        mImageView.setImageResource(R.drawable.smiley_normal);
    }
    public void addEmotion(){
        mEmotion.setEmote(mType);
    }
    public void EmotionSetText(String pText){
        mEmotion.setComment(pText);
    }
}
