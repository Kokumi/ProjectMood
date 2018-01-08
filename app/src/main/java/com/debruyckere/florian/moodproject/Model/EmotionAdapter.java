package com.debruyckere.florian.moodproject.Model;

/**
 * Created by Debruyckère Florian on 01/01/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.MyViewHolder> {
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
        public void display(){

        }
    }
}
