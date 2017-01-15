package com.qwash.user.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwash.user.R;
import com.qwash.user.api.model.ListLocation;
import com.qwash.user.ui.activity.HomeActivity;


import java.util.List;

/**
 * Created by binderbyte on 29/12/16.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    private List<ListLocation.Prediction> itemsEntities;

    public LocationAdapter(List<ListLocation.Prediction> itemsEntities) {
        this.itemsEntities = itemsEntities;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_location, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LocationAdapter.MyViewHolder holder, final int position) {

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
