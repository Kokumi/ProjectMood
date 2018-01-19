package com.debruyckere.florian.moodproject.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.debruyckere.florian.moodproject.R;

public class StatisticsActivity extends AppCompatActivity {

    private LinearLayout mStatChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mStatChart = findViewById(R.id.stat_Chart);

    }
}
