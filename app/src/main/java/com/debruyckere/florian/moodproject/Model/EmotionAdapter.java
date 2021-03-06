package com.debruyckere.florian.moodproject.Model;

/**
 * Created by Debruyckère Florian on 01/01/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debruyckere.florian.moodproject.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.MyViewHolder> {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private List<Emotion> mEmotions=new ArrayList<>();
    private NoDataReaction mListener;

    DateFormat dateFormat ;//= android.text.format.DateFormat.getDateFormat(mContext);

    /**
     * Builder of adapter
     * @param pContext  context the activity who called adapter
     * @param pListener  NoDataReaction interface
     */
    public EmotionAdapter(Context pContext, NoDataReaction pListener){
        mContext = pContext;
        mListener = pListener;
        dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
        mSharedPreferences = mContext.getSharedPreferences("EmoteSave",MODE_PRIVATE);
        LoadEmote();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Emotion emotion;
        Log.i("ADAPTER",""+mEmotions.size());

        //verify if there data loaded
        if(mEmotions.size()!=0) {
            try {
                emotion = mEmotions.get(position);
                holder.display(emotion);
            } catch(IndexOutOfBoundsException oob){
                // in case there don't 7 emotions loaded
                Log.i("ADAPTER","no more Data");
            }
        }
        else {
            Log.w("ADAPTER","NO DATA");

            mListener.noDataReaction();
        }
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

        // Load data from 7 days before
        for(int i=1;i<8;i++){
            //verify if sharedpreferences have data of the previous days
            if(mSharedPreferences.contains(dateFormat.format(d.getTime()- (1000*60*60*24)*i)+"_date")){
                Log.d("ADAPTER","There data to "+dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i));
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
            else{
                Log.d("ADAPTER","NO data to "+dateFormat.format(d.getTime() - (1000 * 60 * 60 * 24) * i));
            }
        }
    }

    /**
     * convert a String parameter to a date
     * @param pString Date to convert
     * @return date in Date type
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

    /**
     * create cells
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.emote_cell, parent, false);
        return new MyViewHolder(view);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mCommentImage;
        private TextView mDayText;
        private FrameLayout cl;
        private String emoteComment = "";

        /**
         * constructor of cells
         * @param itemView cell
         */
        public MyViewHolder(final View itemView) {
            super(itemView);

            cl= itemView.findViewById(R.id.cell_EmoteContenaire);
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

        /**
         * display in the cell the emotion in params
         * @param pEmotion emotion to be displayed
         */
        public void display(Emotion pEmotion){
            // display the comment image if the emotion have a comment
            if(pEmotion.getComment()!=null){
                mCommentImage.setVisibility(View.VISIBLE);
                emoteComment = pEmotion.getComment();
            }
            mDayText.setText(dateFormat.format(pEmotion.getDate()));

            // apply the correct visual to cell
            switch (pEmotion.getEmote()){
                case VeryBad: cl.setBackgroundColor(mContext.getResources().getColor(R.color.badRed));
                            cl.setLayoutParams(new FrameLayout.LayoutParams(188,FrameLayout.LayoutParams.MATCH_PARENT));
                    break;
                case Bad: cl.setBackgroundColor(mContext.getResources().getColor(R.color.dissapointGray));
                            cl.setLayoutParams(new FrameLayout.LayoutParams(336,FrameLayout.LayoutParams.MATCH_PARENT));
                    break;
                case Normal: cl.setBackgroundColor(mContext.getResources().getColor(R.color.normalBlue));
                            cl.setLayoutParams(new FrameLayout.LayoutParams(700,FrameLayout.LayoutParams.MATCH_PARENT));
                    break;
                case Good: cl.setBackgroundColor(mContext.getResources().getColor(R.color.goodGreen));
                            cl.setLayoutParams(new FrameLayout.LayoutParams(1036,FrameLayout.LayoutParams.MATCH_PARENT));
                    break;
                case Great: cl.setBackgroundColor(mContext.getResources().getColor(R.color.greatYellow));
                    cl.setLayoutParams(new FrameLayout.LayoutParams(1400,FrameLayout.LayoutParams.MATCH_PARENT));
                    break;
                case NoData: cl.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    }

}
