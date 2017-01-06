package com.ad.sample.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;

/**
 * Created by binderbyte on 24/12/16.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder> {

    String [] title = {"Promo 2015","Promo 2017","Promo 2018"};
    String[] deskripsi = {"Berlaku pada tgl 12 januari 2015, Berlaku pada tgl 12 januari 2015, Berlaku pada tgl 12 januari 2015, Berlaku pada tgl 12 januari 2015", "Berlaku pada tgl 12 januari 2017", "Berlaku pada tgl 12 januari 2018"};

    Context context;
    LayoutInflater inflater;
    View view;

    public NotificationAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_notification, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        holder.title5.setText(title[position]);
        holder.deskripsi1.setText(deskripsi[position]);
        holder.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title5, deskripsi1;
        CardView cardView5;
        ImageView image1;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title5 = (TextView) itemView.findViewById(R.id.title_notification);
            deskripsi1 = (TextView) itemView.findViewById(R.id.deskripsi_notification);
            cardView5 = (CardView) itemView.findViewById(R.id.card_view_notification);
            image1 = (ImageView) itemView.findViewById(R.id.image_notification);

        }
    }
}
