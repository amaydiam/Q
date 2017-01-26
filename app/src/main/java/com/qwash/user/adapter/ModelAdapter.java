package com.qwash.user.adapter;

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

import com.qwash.user.R;

/**
 * Created by binderbyte on 24/12/16.
 */

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.RecyclerViewHolder> {

    String[] model = {"Ayla", "Ceria", "Espass"};

    Context context;
    LayoutInflater inflater;
    View view;

    public ModelAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_select_model, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        int j = 1;
        for (int i = 0; i < model.length; i++) {

            i = i + j;
            if (position == i) {
                holder.cardView2.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
            }

        }

        holder.title2.setText(model[position]);
        holder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("models", model[position]);
                edit.commit();
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title2;
        CardView cardView2;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title2 = (TextView) itemView.findViewById(R.id.list_model);
            cardView2 = (CardView) itemView.findViewById(R.id.card_view_model);

        }
    }
}
