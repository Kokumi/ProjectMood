package com.debruyckere.florian.moodproject.Controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.debruyckere.florian.moodproject.Model.EmotionAdapter;
import com.debruyckere.florian.moodproject.R;

public class Historique_Activity extends AppCompatActivity {

    RecyclerView mEmoteRecycler;
    private SharedPreferences mSharedPreferences = getSharedPreferences("EmoteSave",MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_);

        mEmoteRecycler = findViewById(R.id.Histo_emote_Recycler);
        mEmoteRecycler.setLayoutManager(new LinearLayoutManager(this));
        mEmoteRecycler.setAdapter(new EmotionAdapter(this));
    }
    public SharedPreferences loadPreference(){
        return mSharedPreferences;
    }
}
