package com.debruyckere.florian.moodproject.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.debruyckere.florian.moodproject.Model.Emotion;
import com.debruyckere.florian.moodproject.Model.EmotionType;
import com.debruyckere.florian.moodproject.R;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private ImageView mCommentImage;
    private ImageView mHistoryImage;
    private ImageView mImageView;
    private Emotion mEmotion= new Emotion();
    private EmotionType mType;
    InputMethodManager imm;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mCommentImage =findViewById(R.id.main_comment_image);
        mHistoryImage = findViewById(R.id.main_history_image);
        mImageView = findViewById(R.id.main_emote_image);
        mImageView.setImageResource(R.drawable.smiley_normal);

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
            }
        });


    }
    public void addEmotion(){
        mEmotion.setEmote(mType);
    }
    public void EmotionSetText(String pText){
        mEmotion.setComment(pText);
        Toast.makeText(MainActivity.this, pText,Toast.LENGTH_LONG).show();
    }
}
