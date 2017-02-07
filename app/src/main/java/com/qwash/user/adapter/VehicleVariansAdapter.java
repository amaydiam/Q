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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.model.vehicle.VehicleBrand;
import com.qwash.user.model.vehicle.VehicleModel;
import com.qwash.user.model.vehicle.VehicleTransmission;
import com.qwash.user.model.vehicle.VehicleYear;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 24/12/16.
 */

public class VehicleVariansAdapter extends RecyclerView.Adapter<VehicleVariansAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    private final int TAG;
    private final GestureDetector gestureDetector;
    public List<?> data = null;
    private Activity activity;
    private VehicleVariansAdapter.OnVehicleVariansItemClickListener OnVehicleVariansItemClickListener;


    public VehicleVariansAdapter(Activity activity, int tag, List<?> items) {
        this.activity = activity;
        this.data = items;
        this.TAG = tag;
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());
    }


    public void setOnVehicleVariansItemClickListener(VehicleVariansAdapter.OnVehicleVariansItemClickListener onVehicleVariansItemClickListener) {
        this.OnVehicleVariansItemClickListener = onVehicleVariansItemClickListener;
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
        if (OnVehicleVariansItemClickListener != null) {
            OnVehicleVariansItemClickListener.onRootClick(v, (Integer) v.getTag());
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
                .inflate(R.layout.item_list_vehicle_varians, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (TAG == Sample.TAG_V_BRAND) {
            VehicleBrand dataObject = (VehicleBrand) data.get(position);
            holder.listVehicleVarians.setText(dataObject.getvBrand());
        } else if (TAG == Sample.TAG_V_MODEL) {
            VehicleModel dataObject = (VehicleModel) data.get(position);
            holder.listVehicleVarians.setText(dataObject.getModels());
        } else if (TAG == Sample.TAG_V_TRANSMISSION) {
            VehicleTransmission dataObject = (VehicleTransmission) data.get(position);
            holder.listVehicleVarians.setText(dataObject.getvTransmission());
        } else if (TAG == Sample.TAG_V_YEAR) {
            VehicleYear dataObject = (VehicleYear) data.get(position);
            holder.listVehicleVarians.setText(dataObject.getYears());
        } else {
            holder.listVehicleVarians.setText("");
        }
        holder.rootParent.setTag(position);

    }

    public int getItemCount() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(data.get(position));
        notifyDataSetChanged();
    }

    public interface OnVehicleVariansItemClickListener {
        void onRootClick(View v, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.list_vehicle_varians)
        TextView listVehicleVarians;
        @BindView(R.id.root_parent)
        LinearLayout rootParent;

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
