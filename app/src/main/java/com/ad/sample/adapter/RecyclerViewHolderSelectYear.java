package com.ad.sample.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ad.sample.R;

/**
 * Created by binderbyte on 24/12/16.
 */
public class RecyclerViewHolderSelectYear extends RecyclerView.ViewHolder {
    TextView title;
    CardView cardView;

    public RecyclerViewHolderSelectYear(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.list_title);
        cardView =(CardView) itemView.findViewById(R.id.card_view);

    }
}
