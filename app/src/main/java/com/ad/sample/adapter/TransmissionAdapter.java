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

public class TransmissionAdapter extends RecyclerView.Adapter<TransmissionAdapter.RecyclerViewHolder> {


    String [] transmission = {"X 1.5 AT", "X 1.5 MT"};

    Context context;
    LayoutInflater inflater;
    View view;

    public TransmissionAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_select_transmission, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        int j = 1;
        for(int i = 0; i < transmission.length; i++){

            i = i + j;
            if (position == i){
                holder.cardView3.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
            }

        }

        holder.title3.setText(transmission[position]);
        holder.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("vehicle_transmission", transmission[position]);
                edit.commit();
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return transmission.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title3;
        CardView cardView3;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title3 = (TextView) itemView.findViewById(R.id.list_transmission);
            cardView3 = (CardView) itemView.findViewById(R.id.card_view_transmission);

        }
    }
}
