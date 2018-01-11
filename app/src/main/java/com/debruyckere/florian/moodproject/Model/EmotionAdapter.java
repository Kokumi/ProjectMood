package com.debruyckere.florian.moodproject.Model;

/**
 * Created by Debruyckère Florian on 01/01/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debruyckere.florian.moodproject.Controller.Historique_Activity;
import com.debruyckere.florian.moodproject.Controller.MainActivity;
import com.debruyckere.florian.moodproject.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.MyViewHolder> {

    private Context mContext;
    private SharedPreferences mSharedPreferences= mContext.getSharedPreferences("EmoteSave",MODE_PRIVATE);
    private List<Emotion> mEmotions=new ArrayList<>();

    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);

    public EmotionAdapter(Context pContext){
        mContext = pContext;
        LoadEmote();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Emotion emotion = mEmotions.get(position);
        holder.display(emotion);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
    * Load emote starred in preferences
     */
    public void LoadEmote(){
        Log.i("ADAPTER","Emote Loading");
        Date d= Calendar.getInstance().getTime();
        for(int i=1;i<8;i++){
            Emotion mEmote = new Emotion();
            mEmote.setEmote(EmotionType.valueOf(
                    mSharedPreferences.getString(dateFormat.format(d.getTime()-(1000*60*60*24)*i)+"_Type",
                    "Normal")));
            mEmote.setComment(
                    mSharedPreferences.getString(dateFormat.format(d.getTime()-(1000*60*60*24)*i)+"_com",
                    "Normal"));
            mEmote.setDate(stringToDate(
                    mSharedPreferences.getString(dateFormat.format(d.getTime()-(1000*60*60*24)*i)+"_date",
                            "")));
            mEmotions.add(mEmote);
        }
    }

    private Date stringToDate(String pString){
        Date retour;
       try{
            SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yy");
            retour = simpleFormat.parse(pString);
            return retour;
        }catch (ParseException e){
            Log.i("ADAPTER","String to Date parse problem \n"+e);
        }
       return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.emote_cell, parent, false);
        return new MyViewHolder(view);
    }

    private ImageView mCommentImage;
    private TextView mDayText;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private String emoteComment = "";
        public MyViewHolder(final View itemView) {
            super(itemView);

            mCommentImage = itemView.findViewById(R.id.cell_Image);
            mDayText = itemView.findViewById(R.id.cell_Text);
            mCommentImage.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), emoteComment, Toast.LENGTH_LONG).show();
                }
            });

        }

        public void display(Emotion pEmotion){
            if(pEmotion.getComment()!=null){
                mCommentImage.setVisibility(View.VISIBLE);
                emoteComment = pEmotion.getComment();
            }
            mDayText.setText(dateFormat.format(pEmotion.getDate()));
        }
    }

}
