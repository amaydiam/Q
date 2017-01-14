package com.ad.sample.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ad.sample.R;

/**
 * Created by binderbyte on 24/12/16.
 */

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.RecyclerViewHolder> {

    String [] year = {"2016","2015","2014","2013",
            "2012","2011","2010","2009","2008","2007"};

    Context context;
    LayoutInflater inflater;
    View view;

    public YearAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_select_year, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        int j = 1;
        for(int i = 0; i < year.length; i++){

            i = i + j;
            if (position == i){
                holder.cardView4.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
            }

        }

        holder.title4.setText(year[position]);
        holder.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("vehicle_year", year[position]);
                edit.commit();
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return year.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title4;
        CardView cardView4;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title4 = (TextView) itemView.findViewById(R.id.list_year);
            cardView4 = (CardView) itemView.findViewById(R.id.card_view_year);

        }
    }
}
