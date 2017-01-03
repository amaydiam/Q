package com.ad.sample.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.ui.activity.AddVehicleActivity;

/**
 * Created by binderbyte on 24/12/16.
 */

public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerViewHolder> {


    String [] brand = {"Audi", "Bentley", "BMW"};

    Context context;
    LayoutInflater inflater;
    View view;
    public RecyclerAdapter1(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_select_brand, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        int j = 1;
        for(int i = 0; i < brand.length; i++){

            i = i + j;
            if (position == i){
                holder.cardView1.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
            }

        }

        holder.title1.setText(brand[position]);
        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("brand", brand[position]);
                edit.commit();
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return brand.length;
    }
}
