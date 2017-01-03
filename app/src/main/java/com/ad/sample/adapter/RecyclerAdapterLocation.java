package com.ad.sample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.model.ListLocation;
import com.ad.sample.ui.activity.HomeActivity;


import java.util.List;

import retrofit2.Callback;

/**
 * Created by binderbyte on 29/12/16.
 */

public class RecyclerAdapterLocation extends RecyclerView.Adapter<RecyclerAdapterLocation.MyViewHolder> {

    private List<ListLocation.Prediction> itemsEntities;

    public RecyclerAdapterLocation(List<ListLocation.Prediction> itemsEntities) {
        this.itemsEntities = itemsEntities;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_location, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterLocation.MyViewHolder holder, final int position) {

        holder.title.setText(itemsEntities.get(position).getStructuredFormatting().getMainText());
        holder.deskripsi.setText(itemsEntities.get(position).getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), HomeActivity.class);
                intent.putExtra("location", itemsEntities.get(position).getDescription());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsEntities.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, deskripsi;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_location);
            deskripsi = (TextView) view.findViewById(R.id.deskripsi_location);
            cardView = (CardView) view.findViewById(R.id.card_location);
        }
    }
}
