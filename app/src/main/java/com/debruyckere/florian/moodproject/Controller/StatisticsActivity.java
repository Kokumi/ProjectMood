package com.debruyckere.florian.moodproject.Controller;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.debruyckere.florian.moodproject.Model.EmotionType;
import com.debruyckere.florian.moodproject.R;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StatisticsActivity extends AppCompatActivity {

    private com.github.mikephil.charting.charts.PieChart mPieChart;
    private List colorsList = new ArrayList();
    private SharedPreferences mSharedPreferences;
    private DateFormat dateFormat ;
    private List emotionEntries = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mPieChart = findViewById(R.id.stat_piechart);
        mSharedPreferences = getSharedPreferences("EmoteSave", MODE_PRIVATE);
        dateFormat= android.text.format.DateFormat.getDateFormat(StatisticsActivity.this);

        loadData();

        //Create the Colors Set
        colorsList.add(getResources().getColor(R.color.badRed));
        colorsList.add(getResources().getColor(R.color.dissapointGray));
        colorsList.add(getResources().getColor(R.color.normalBlue));
        colorsList.add(getResources().getColor(R.color.goodGreen));
        colorsList.add(getResources().getColor(R.color.greatYellow));

        //create the Chart
        PieDataSet dataSet = new PieDataSet(emotionEntries,"numbers of emotion of 7 days before");
        dataSet.setColors(colorsList);
        mPieChart.setData(new PieData(dataSet));
        mPieChart.setEntryLabelTextSize(20);
        mPieChart.invalidate(); //refresh
    }

    /**
     * Load data from Shared preference
     */
    private void loadData(){
        Log.i("STATACTIVITY","Emote Loading");
        Date d= Calendar.getInstance().getTime();

        // instanciate emotion counter
        int nbVeryBad = 0;
        int nbBad = 0;
        int nbNormal = 0;
        int nbGood = 0;
        int nbGreat = 0;

        for(int i=1;i<8;i++){
            //verify if sharedpreferences have data here
            if(mSharedPreferences.contains(dateFormat.format(d.getTime()- (1000*60*60*24)*i)+"_date")){
                Log.d("ADAPTER","There data to "+dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i));

                EmotionType emotionType = (EmotionType.valueOf(
                        mSharedPreferences.getString(dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i) + "_Type",
                                "Normal")));

                // add one to the correct emotion counter
                switch (emotionType){
                    case VeryBad: nbVeryBad++;
                        break;
                    case Bad:  nbBad++;
                        break;
                    case Normal: nbNormal++;
                        break;
                    case Good: nbGood++;
                        break;
                    case Great: nbGreat++;
                        break;
                }
            }
            else{
                Log.d("ADAPTER","NO data to "+dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i));
            }
        }

        // set Entry of the pie chart
        emotionEntries.add(new PieEntry(nbVeryBad,"VeryBad"));
        emotionEntries.add(new PieEntry(nbBad,"Bad"));
        emotionEntries.add(new PieEntry(nbNormal,"Normal"));
        emotionEntries.add(new PieEntry(nbGood,"Good"));
        emotionEntries.add(new PieEntry(nbGreat,"Great"));
    }

}
