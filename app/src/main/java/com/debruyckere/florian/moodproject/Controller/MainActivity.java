package com.debruyckere.florian.moodproject.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.debruyckere.florian.moodproject.Model.Emotion;
import com.debruyckere.florian.moodproject.Model.EmotionType;
import com.debruyckere.florian.moodproject.Model.OnSwipeTouchListener;
import com.debruyckere.florian.moodproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private ImageView mCommentImage;
    private ImageView mHistoryImage;
    private ImageView mImageView;
    private Emotion mEmotion = new Emotion();
    private ConstraintLayout mLayout;
    private EmotionType mType;
    private int emotionSize = 0;
    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(MainActivity.this);
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences("EmoteSave", MODE_PRIVATE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mCommentImage = findViewById(R.id.main_comment_image);
        mHistoryImage = findViewById(R.id.main_history_image);
        mImageView = findViewById(R.id.main_emote_image);
        mLayout = findViewById(R.id.main_Layout);

        firstLoad();
        mCommentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(MainActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Comment")
                        .setMessage("add a comment to the emotion")
                        .setView(input)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EmotionSetText(input.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                input.setText("");
                            }
                        });
                builder.create().show();
                Toast.makeText(getApplicationContext(), "Comentaire", Toast.LENGTH_SHORT).show();
            }
        });
        mHistoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
                Intent historyActivity = new Intent(MainActivity.this, Historique_Activity.class);
                startActivity(historyActivity);
            }
        });

        mLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            public void onSwipeTop() {               //Slide to top
                emoteTypeChanger(true);
                Log.i("MAIN", "Top");
            }

            public void onSwipeBottom() {            //Slide to bottom
                Log.i("MAIN", "Bottom");
                emoteTypeChanger(false);
            }

        });
        todayToString();

    }

    /**
     * save a new emotion in preferences
     */
    public void addEmotion() {
        Log.i("MAIN", "add emotion");
        mEmotion.setEmote(mType);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(todayToString() + "_Type", mType.toString());
        editor.putString(todayToString() + "_com", mEmotion.getComment());
        editor.putString(todayToString() + "_date", todayToString());
        //editor.apply();
        editor.commit();

        }

    /**
     * add comment to a emotion
     *
     * @param pText the comment to add
     */
    public void EmotionSetText(String pText) {
        Log.i("MAIN", "add comment: " + pText);
        mEmotion.setComment(pText);
        Toast.makeText(MainActivity.this, pText, Toast.LENGTH_LONG).show();
    }

    /**
     * transform the today in string
     *
     * @return today in string
     */
    private String todayToString() {
        Log.i("MAIN", "transform today to string");
        String retour;
        Date d = Calendar.getInstance().getTime();
        retour = dateFormat.format(d);
        Log.i("MAIN", retour);
        return retour;
    }

    /**
     * change emoteType, background and imageView according to the parameter pState
     *
     * @param pState
     */
    int index = 2;

    private void emoteTypeChanger(boolean pState) {
        if (pState == true) {
            index++;
        } else {
            index--;
        }

        switch (index) {
            case 0:
                mType = EmotionType.VeryBad;
                mImageView.setImageResource(R.drawable.smiley_sad);
                mLayout.setBackgroundColor(getResources().getColor(R.color.badRed));
                break;
            case 1:
                mType = EmotionType.Bad;
                mImageView.setImageResource(R.drawable.smiley_disappointed);
                mLayout.setBackgroundColor(getResources().getColor(R.color.dissapointGray));
                break;
            case 2:
                mType = EmotionType.Normal;
                mImageView.setImageResource(R.drawable.smiley_normal);
                mLayout.setBackgroundColor(getResources().getColor(R.color.normalBlue));
                break;
            case 3:
                mType = EmotionType.Good;
                mImageView.setImageResource(R.drawable.smiley_happy);
                mLayout.setBackgroundColor(getResources().getColor(R.color.goodGreen));
                break;
            case 4:
                mType = EmotionType.Great;
                mImageView.setImageResource(R.drawable.smiley_super_happy);
                mLayout.setBackgroundColor(getResources().getColor(R.color.greatYellow));
                break;
        }
        Log.i("MAIN", mType.toString());
    }

    @Override
    public void onDestroy(){
        addEmotion();

        super.onDestroy();
    }
    public void firstLoad(){
        Log.i("MAIN","FirstLoad");
        if(mSharedPreferences.contains(todayToString()+"_Type")){
            mType =(EmotionType.valueOf(
                    mSharedPreferences.getString(todayToString() + "_Type",
                            "Normal")));
            EmotionSetText(
                    mSharedPreferences.getString(todayToString() + "_com",
                            null));
            Log.i("MAIN","Load already save");
        } else {
            mType = EmotionType.Normal;
            Log.i("MAIN","First Load of day");
        }

        switch (mType){
            case VeryBad:   mLayout.setBackgroundColor(getResources().getColor(R.color.badRed));
                            mImageView.setImageResource(R.drawable.smiley_sad);
                break;
            case Bad:       mLayout.setBackgroundColor(getResources().getColor(R.color.dissapointGray));
                            mImageView.setImageResource(R.drawable.smiley_disappointed);
                break;
            case Normal:    mLayout.setBackgroundColor(getResources().getColor(R.color.normalBlue));
                            mImageView.setImageResource(R.drawable.smiley_normal);
                break;
            case Good:      mLayout.setBackgroundColor(getResources().getColor(R.color.goodGreen));
                            mImageView.setImageResource(R.drawable.smiley_happy);
                break;
            case Great:     mLayout.setBackgroundColor(getResources().getColor(R.color.greatYellow));
                            mImageView.setImageResource(R.drawable.smiley_super_happy);
                break;

        }
    }
}