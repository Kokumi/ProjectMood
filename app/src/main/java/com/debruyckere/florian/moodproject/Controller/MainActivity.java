package com.debruyckere.florian.moodproject.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(MainActivity.this);
    private DateFormat dateFormat;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormat =android.text.format.DateFormat.getDateFormat(MainActivity.this);
        mSharedPreferences = getSharedPreferences("EmoteSave", MODE_PRIVATE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mCommentImage = findViewById(R.id.main_comment_image);
        mHistoryImage = findViewById(R.id.main_history_image);
        mImageView = findViewById(R.id.main_emote_image);
        mLayout = findViewById(R.id.main_Layout);

        firstLoad();

        //add reaction to the comment button
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

        //add reaction to the history button
        mHistoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
                Intent historyActivity = new Intent(MainActivity.this, Historique_Activity.class);
                startActivity(historyActivity);
            }
        });

        //add slide reaction
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
        //save data with the current day as key
        editor.putString(todayToString() + "_Type", mType.toString());
        editor.putString(todayToString() + "_com", mEmotion.getComment());
        editor.putString(todayToString() + "_date", todayToString());
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

    int index = 2;      //index of emotion
    /**
     * change emoteType, background and imageView according to the parameter pState
     * @param pState true if the user slide to top
     */
    private void emoteTypeChanger(boolean pState) {
        if (pState) {
            //if the user slide to top the emotion change to worse
            index--;
        } else {
            // if the user slide to bottom the emotion change to better
            index++;
        }

        //apply the emotion change
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

    /**
     * save current data when the activity is destroy
     */
    @Override
    public void onDestroy(){
        addEmotion();
        super.onDestroy();
    }

    /**
     * Load data of the current day
     */
    public void firstLoad(){
        Log.i("MAIN","FirstLoad");
        //load data save of the current day
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

        //apply the emotion
        switch (mType){
            case VeryBad:   mLayout.setBackgroundColor(getResources().getColor(R.color.badRed));
                            mImageView.setImageResource(R.drawable.smiley_sad);
                            index = 0;
                break;
            case Bad:       mLayout.setBackgroundColor(getResources().getColor(R.color.dissapointGray));
                            mImageView.setImageResource(R.drawable.smiley_disappointed);
                            index = 1;
                break;
            case Normal:    mLayout.setBackgroundColor(getResources().getColor(R.color.normalBlue));
                            mImageView.setImageResource(R.drawable.smiley_normal);
                            index = 2;
                break;
            case Good:      mLayout.setBackgroundColor(getResources().getColor(R.color.goodGreen));
                            mImageView.setImageResource(R.drawable.smiley_happy);
                            index = 3;
                break;
            case Great:     mLayout.setBackgroundColor(getResources().getColor(R.color.greatYellow));
                            mImageView.setImageResource(R.drawable.smiley_super_happy);
                            index = 4;
                break;

        }
    }
}