package com.debruyckere.florian.moodproject.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.debruyckere.florian.moodproject.Model.EmotionAdapter;
import com.debruyckere.florian.moodproject.Model.NoDataReaction;
import com.debruyckere.florian.moodproject.R;

public class Historique_Activity extends AppCompatActivity implements NoDataReaction{

    RecyclerView mEmoteRecycler;
    Button mStatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_);

        mStatButton = findViewById(R.id.Histo_Button);
        mEmoteRecycler = findViewById(R.id.Histo_emote_Recycler);
        mEmoteRecycler.setLayoutManager(new LinearLayoutManager(this));
        mEmoteRecycler.setAdapter(new EmotionAdapter(this, this));

        //add Reaction to the Statistic button
        mStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StatisticsActivity = new Intent(Historique_Activity.this, StatisticsActivity.class);
                startActivity(StatisticsActivity);
            }
        });
    }

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
