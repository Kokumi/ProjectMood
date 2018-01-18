package com.debruyckere.florian.moodproject.Model;

/**
 * Created by Debruyckère Florian on 01/01/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.style.BackgroundColorSpan;
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
    private SharedPreferences mSharedPreferences;
    private List<Emotion> mEmotions=new ArrayList<>();
    private NoDataReaction mListener;

    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);

    //Builder
    public EmotionAdapter(Context pContext, NoDataReaction pListener){
        mContext = pContext;
        mListener = pListener;
        mSharedPreferences = mContext.getSharedPreferences("EmoteSave",MODE_PRIVATE);
        LoadEmote();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Emotion emotion = new Emotion();
        Log.i("ADAPTER",""+mEmotions.size());
        if(mEmotions.size()!=0) {
            emotion = mEmotions.get(position);
        }
        else {
            Log.w("ADAPTER","NO DATA");
            emotion.setDate(Calendar.getInstance().getTime());
            emotion.setEmote(EmotionType.VeryBad);
            emotion.setComment("no data");

            mListener.noDataReaction();
        }
        holder.display(emotion);

    }


    @Override
    public int getItemCount() {
        return 7;
    }

    /**
    * Load emote starred in preferences
     */
    public void LoadEmote(){
        Log.i("ADAPTER","Emote Loading");
        Date d= Calendar.getInstance().getTime();
        Log.d("ADAPTER",""+mSharedPreferences.contains(d.getTime()+"_Type"));
        for(int i=1;i<8;i++){
            //verify if sharedpreferences have data here
            if(mSharedPreferences.getString(dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i), null)!=null) {
                Emotion mEmote = new Emotion();
                mEmote.setEmote(EmotionType.valueOf(
                        mSharedPreferences.getString(dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i) + "_Type",
                                "Normal")));
                mEmote.setComment(
                        mSharedPreferences.getString(dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i) + "_com",
                                null));
                mEmote.setDate(stringToDate(
                        mSharedPreferences.getString(dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i) + "_date",
                                null)));
                mEmotions.add(mEmote);
            }
        }
    }

    /**
     * convert a String parameter to a date
     * @param pString
     * @return
     */
    private Date stringToDate(String pString){
        Date retour;
       try{
            SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yy");
            retour = simpleFormat.parse(pString);
            return retour;
        }catch (ParseException e){
            Log.i("ADAPTER","String to Date parse error \n"+e);
        }
       return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.emote_cell, parent, false);
        return new MyViewHolder(view);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mCommentImage;
        private TextView mDayText;
        private ConstraintLayout cl;
        private String emoteComment = "";

        public MyViewHolder(final View itemView) {
            super(itemView);

            cl= itemView.findViewById(R.id.cell_Layout);
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
            // display the comment image if the emotion have a comment
            if(pEmotion.getComment()!=null){
                mCommentImage.setVisibility(View.VISIBLE);
                emoteComment = pEmotion.getComment();
            }
            mDayText.setText(dateFormat.format(pEmotion.getDate()));

            switch (pEmotion.getEmote()){
                case VeryBad: cl.setBackgroundColor(mContext.getResources().getColor(R.color.badRed));
                    break;
                case Bad: cl.setBackgroundColor(mContext.getResources().getColor(R.color.dissapointGray));
                    break;
                case Normal: cl.setBackgroundColor(mContext.getResources().getColor(R.color.normalBlue));
                    break;
                case Good: cl.setBackgroundColor(mContext.getResources().getColor(R.color.goodGreen));
                    break;
                case Great: cl.setBackgroundColor(mContext.getResources().getColor(R.color.greatYellow));
                    break;
            }
        }
    }

}
