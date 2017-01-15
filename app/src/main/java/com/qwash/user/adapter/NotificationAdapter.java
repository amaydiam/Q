package com.qwash.user.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qwash.user.R;
import com.qwash.user.ui.fragment.DetailNotificationFragment;

/**
 * Created by binderbyte on 24/12/16.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder> {

    String [] title = {"Promo 2015","Promo 2017","Promo 2018"};
    int[] image = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    String[] deskripsi = {"Berlaku pada tgl 12 januari 2015, Berlaku pada tgl 12 januari 2015, Berlaku pada tgl 12 januari 2015, Berlaku pada tgl 12 januari 2015", "Berlaku pada tgl 12 januari 2017", "Berlaku pada tgl 12 januari 2018"};

    Context context;
    LayoutInflater inflater;
    View view;
    FragmentManager fragmentManager;

    public NotificationAdapter(FragmentManager fm, Context context) {
        this.fragmentManager = fm;
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
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.title5.setText(title[position]);
        holder.deskripsi1.setText(deskripsi[position]);
        holder.image1.setImageResource(image[position]);
        holder.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle i = new Bundle();
                i.putString("title", title[position]);
                i.putString("deskripsi", deskripsi[position]);
                i.putInt("image", image[position]);

                DetailNotificationFragment newFragment = new DetailNotificationFragment();
                newFragment.setArguments(i);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
