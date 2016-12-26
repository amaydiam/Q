package com.ad.sample.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ad.sample.R;

/**
 * Created by binderbyte on 24/12/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView title1, title2, title3, title4;
    CardView cardView1, cardView2, cardView3, cardView4;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        title1 = (TextView) itemView.findViewById(R.id.list_brand);
        cardView1 =(CardView) itemView.findViewById(R.id.card_view_brand);

        title2 = (TextView) itemView.findViewById(R.id.list_model);
        cardView2 =(CardView) itemView.findViewById(R.id.card_view_model);

        title3 = (TextView) itemView.findViewById(R.id.list_transmission);
        cardView3 =(CardView) itemView.findViewById(R.id.card_view_transmission);

        title4 = (TextView) itemView.findViewById(R.id.list_year);
        cardView4 =(CardView) itemView.findViewById(R.id.card_view_year);

    }
}
