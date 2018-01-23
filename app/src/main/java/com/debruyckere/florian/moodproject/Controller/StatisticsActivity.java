package com.debruyckere.florian.moodproject.Controller;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.debruyckere.florian.moodproject.Model.Emotion;
import com.debruyckere.florian.moodproject.Model.EmotionType;
import com.debruyckere.florian.moodproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StatisticsActivity extends AppCompatActivity {

    private LinearLayout mStatChart;
    private com.github.mikephil.charting.charts.PieChart mPieChart;
    private static int[] colors = new int[] {R.color.badRed, R.color.dissapointGray,R.color.normalBlue,
                                             R.color.goodGreen, R.color.greatYellow};
    private List colorsList = new ArrayList();
    private static double[] values = new double[]{10,11,12,13,14};
    private static String[] nameList = new String[]{"bad" , "dissapoint","normal","good","great"};
    private SharedPreferences mSharedPreferences;
    private List emotionLabels = new ArrayList();
    DateFormat dateFormat ;
    List emotionEntries = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mStatChart = findViewById(R.id.stat_Chart);
        mPieChart = findViewById(R.id.stat_piechart);
        mSharedPreferences = getSharedPreferences("EmoteSave", MODE_PRIVATE);
        dateFormat= android.text.format.DateFormat.getDateFormat(StatisticsActivity.this);

        loadData();
        emotionLabels.add("VeryBad");
        emotionLabels.add("Bad");
        emotionLabels.add("Normal");
        emotionLabels.add("Good");
        emotionLabels.add("Great");

        colorsList.add(getResources().getColor(R.color.badRed));
        colorsList.add(getResources().getColor(R.color.dissapointGray));
        colorsList.add(getResources().getColor(R.color.normalBlue));
        colorsList.add(getResources().getColor(R.color.goodGreen));
        colorsList.add(getResources().getColor(R.color.greatYellow));

        PieDataSet dataSet = new PieDataSet(emotionEntries,"numbers of emotion of 7 days before");
        dataSet.setColors(colorsList);
        mPieChart.setData(new PieData(dataSet));
        mPieChart.invalidate(); //refresh
    }
//new Entry(numberEmoteInData, numberReal);

    private void loadData(){
        Log.i("STATACTIVITY","Emote Loading");
        Date d= Calendar.getInstance().getTime();

        //Table of all emote : VeryBad, Bad, Normal, Good, Great
        int[] emotionTable = new int[]{0,0,0,0,0};

        int nbVeryBad = 0;
        int nbBad = 0;
        int nbNormal = 0;
        int nbGood = 0;
        int nbGreat = 0;

        for(int i=1;i<8;i++){
            //verify if sharedpreferences have data here
            if(mSharedPreferences.contains(dateFormat.format(d.getTime()- (1000*60*60*24)*i)+"_date")){
                Log.d("ADAPTER","There data to "+dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i));
                Emotion mEmote = new Emotion();
                EmotionType emotionType = (EmotionType.valueOf(
                        mSharedPreferences.getString(dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i) + "_Type",
                                "Normal")));

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

        emotionEntries.add(new PieEntry(nbVeryBad,"VeryBad"));
        emotionEntries.add(new PieEntry(nbBad,"Bad"));
        emotionEntries.add(new PieEntry(nbNormal,"Normal"));
        emotionEntries.add(new PieEntry(nbGood,"Good"));
        emotionEntries.add(new PieEntry(nbGreat,"Great"));
    }

}
