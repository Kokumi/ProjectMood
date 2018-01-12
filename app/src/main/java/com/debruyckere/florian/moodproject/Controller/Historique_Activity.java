package com.debruyckere.florian.moodproject.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.debruyckere.florian.moodproject.Model.EmotionAdapter;
import com.debruyckere.florian.moodproject.Model.NoDataReaction;
import com.debruyckere.florian.moodproject.R;

public class Historique_Activity extends AppCompatActivity implements NoDataReaction{

    RecyclerView mEmoteRecycler;
    //private SharedPreferences mSharedPreferences = getSharedPreferences("EmoteSave",MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_);

        mEmoteRecycler = findViewById(R.id.Histo_emote_Recycler);
        mEmoteRecycler.setLayoutManager(new LinearLayoutManager(this));
        mEmoteRecycler.setAdapter(new EmotionAdapter(this, this));
    }
    /*public SharedPreferences loadPreference(){
        return mSharedPreferences;
    }*/

    /**
     * Return to the mainActivity if there no data in mSharedPreferences
     */
    public void noDataReaction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Historique_Activity.this);
        builder.setTitle("NO DATA")
                .setMessage("NO DATA SAVED")
                .setNeutralButton("Return", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent historyActivity = new Intent(Historique_Activity.this,MainActivity.class);
                        startActivity(historyActivity);
                    }
                });

    }
}
