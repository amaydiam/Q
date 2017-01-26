package com.qwash.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwash.user.R;
import com.qwash.user.model.vehicle.VehicleBrand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 24/12/16.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    public final List<VehicleBrand> data;
    private final GestureDetector gestureDetector;
    private Activity activity;
    private OnBrandItemClickListener OnBrandItemClickListener;


    public BrandAdapter(Activity activity, List<VehicleBrand> summaryList) {
        this.activity = activity;
        this.data = summaryList;
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());
    }

    public void setOnBrandItemClickListener(OnBrandItemClickListener onBrandItemClickListener) {
        this.OnBrandItemClickListener = onBrandItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        audioManager.playSoundEffect(SoundEffectConstants.CLICK);
        final int viewId = v.getId();

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnBrandItemClickListener != null) {
            OnBrandItemClickListener.onRootClick(v, (Integer) v.getTag());
        }

    }

    public void delete_all() {
        int count = getItemCount();
        if (count > 0) {
            data.clear();
            notifyDataSetChanged();
        }

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_brand, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        VehicleBrand summary = data.get(position);
        holder.listBrand.setText(summary.vBrand);
        holder.rootParent.setTag(position);

    }

    public int getItemCount() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(data.get(position));
        notifyDataSetChanged();
    }

    public interface OnBrandItemClickListener {

        void onRootClick(View v, int position);


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_brand)
        TextView listBrand;
        @BindView(R.id.root_parent)
        CardView rootParent;

        ViewHolder(View vi) {
            super(vi);
            ButterKnife.bind(this, vi);

        }

    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }


    }

}
