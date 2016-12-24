package com.ad.sample.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ad.sample.R;

/**
 * Created by binderbyte on 24/12/16.
 */

public class RecyclerAdapterSelectYear extends RecyclerView.Adapter<RecyclerViewHolderSelectYear> {

    String [] name={"2016","2015","2014","2013",
            "2012","2011","2010","2009","2008","2007"};
    Context context;
    LayoutInflater inflater;
    public RecyclerAdapterSelectYear(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolderSelectYear onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.select_model_year, parent, false);

        RecyclerViewHolderSelectYear viewHolder=new RecyclerViewHolderSelectYear(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderSelectYear holder, final int position) {

        int j = 1;
        for(int i = 0; i < name.length; i++){

            i = i + j;
            if (position == i){
                holder.cardView.setCardBackgroundColor(Color.CYAN);
            }

        }

        holder.title.setText(name[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }
}
