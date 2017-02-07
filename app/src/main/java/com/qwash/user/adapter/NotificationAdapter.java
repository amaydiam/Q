package com.qwash.user.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qwash.user.R;
import com.qwash.user.model.Notification;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    public final ArrayList<Notification> data;
    private final GestureDetector gestureDetector;

    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnNotificationItemClickListener OnNotificationItemClickListener;


    public NotificationAdapter(Activity activity, ArrayList<Notification> histories, boolean isTable) {
        this.activity = activity;
        this.data = histories;
        mSelectedItemsIds = new SparseBooleanArray();
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());
        this.isTablet = isTable;

    }

    public void setOnNotificationItemClickListener(OnNotificationItemClickListener onNotificationItemClickListener) {
        this.OnNotificationItemClickListener = onNotificationItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnNotificationItemClickListener != null) {
            OnNotificationItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
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
                .inflate(R.layout.item_list_notification, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Notification notification = data.get(position);

        holder.titleNotification.setText(notification.getTitle());
        holder.deskripsiNotification.setText(notification.getDescription());

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (isTablet) {
            if (selected == position) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.rootParent.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.border_set_blue));
                } else {
                    holder.rootParent.setBackground(ContextCompat.getDrawable(activity, R.drawable.border_set_blue));
                }

                holder.titleNotification.setTextColor(ContextCompat.getColor(activity, R.color.white));
                holder.deskripsiNotification.setTextColor(ContextCompat.getColor(activity, R.color.white));
            } else {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.rootParent.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.border_set_green));
                } else {
                    holder.rootParent.setBackground(ContextCompat.getDrawable(activity, R.drawable.border_set_green));
                }
                holder.titleNotification.setTextColor(ContextCompat.getColor(activity, R.color.black_424242));
                holder.deskripsiNotification.setTextColor(ContextCompat.getColor(activity, R.color.black_424242));
            }
        } else {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.rootParent.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.border_set_green));
            } else {
                holder.rootParent.setBackground(ContextCompat.getDrawable(activity, R.drawable.border_set_green));
            }
            holder.titleNotification.setTextColor(ContextCompat.getColor(activity, R.color.black_424242));
            holder.deskripsiNotification.setTextColor(ContextCompat.getColor(activity, R.color.black_424242));
        }

        holder.rootParent.setTag(position);

    }

    public int getItemCount() {
        return data.size();
    }

    /**
     * Here is the key method to apply the animation
     */

    public void remove(int position) {
        data.remove(data.get(position));
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public interface OnNotificationItemClickListener {

        void onRootClick(View v, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_notification)
        ImageView imageNotification;
        @BindView(R.id.title_notification)
        TextView titleNotification;
        @BindView(R.id.deskripsi_notification)
        TextView deskripsiNotification;
        @BindView(R.id.root_parent)
        LinearLayout rootParent;

        public ViewHolder(View vi) {
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
