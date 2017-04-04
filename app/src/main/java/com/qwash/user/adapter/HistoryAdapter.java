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
import android.widget.RelativeLayout;

import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.model.History;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.ui.widget.RobotoRegularTextView;
import com.qwash.user.utils.TextUtils;

import java.util.ArrayList;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    public final ArrayList<History> data;
    private final GestureDetector gestureDetector;


    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnHistoryItemClickListener OnHistoryItemClickListener;


    public HistoryAdapter(Activity activity, ArrayList<History> feedbackCustomerList, boolean isTable) {
        this.activity = activity;
        this.data = feedbackCustomerList;
        mSelectedItemsIds = new SparseBooleanArray();
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());
        this.isTablet = isTable;

    }

    public void setOnHistoryItemClickListener(OnHistoryItemClickListener onHistoryItemClickListener) {
        this.OnHistoryItemClickListener = onHistoryItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnHistoryItemClickListener != null) {
            OnHistoryItemClickListener.onRootClick(v, (Integer) v.getTag());
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
                .inflate(R.layout.item_history, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(final ViewHolder holder, int position) {
        History history = data.get(position);

        if (history.getVehicles().equalsIgnoreCase("1")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_citycar));
            holder.vehicle.setText("SMALL");
        } else if (history.getVehicles().equalsIgnoreCase("2")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_minivan));
            holder.vehicle.setText("MEDIUM");
        } else if (history.getVehicles().equalsIgnoreCase("3")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_suv));
            holder.vehicle.setText("BIG");

        } else if (history.getVehicles().equalsIgnoreCase("4")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_under_srp_cc));
            holder.vehicle.setText("SMALL");

        } else if (history.getVehicles().equalsIgnoreCase("5")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_srp_cc));
            holder.vehicle.setText("MEDIUM");

        } else if (history.getVehicles().equalsIgnoreCase("6")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_above_srp_cc));
            holder.vehicle.setText("BIG");

        } else {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.ic_launcher));
            holder.vehicle.setText("-");
        }

        try {
            String[] n = history.getCreatedAt().split(" ");
            String[] d = n[1].split(":");
            String h = TextUtils.getTodayYestFromMilli(activity, n[0], TextUtils.getDate(history.getCreatedAt()).getTime());

            try {
                History history_s = data.get(position - 1);
                String[] s = history_s.getCreatedAt().split(" ");
                if (n[0].equalsIgnoreCase(s[0])) {
                    holder.header.setVisibility(View.GONE);
                } else {
                    holder.header.setVisibility(View.VISIBLE);
                    holder.header.setText(h);
                }

            } catch (Exception e) {
                holder.header.setVisibility(View.VISIBLE);
                holder.header.setText(h);
            }

            holder.dateInProgress.setText(d[0] + ":" + d[1]);

        } catch (Exception x) {
            holder.header.setVisibility(View.VISIBLE);
            holder.header.setText(history.getCreatedAt());
            holder.dateInProgress.setText(history.getCreatedAt());
        }

        final int sdk = Build.VERSION.SDK_INT;

        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_green));
            holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_complete));

        } else {
            holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_green));
            holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_complete));
        }
        holder.status.setTextColor(ContextCompat.getColor(activity, R.color.green_cc09891b));


        if (history.getStatus() == 4 && !TextUtils.isNullOrEmpty(String.valueOf(history.getRatings()))) {
            holder.layoutRating.setVisibility(View.VISIBLE);
            holder.rating.setRating(history.getRatings());
            holder.estimatedPrice.setTextColor(ContextCompat.getColor(activity, R.color.blue_2196F3));
        } else {
            holder.layoutRating.setVisibility(View.GONE);
            holder.estimatedPrice.setTextColor(ContextCompat.getColor(activity, R.color.orange));
        }

        holder.status.setText(history.getDescription());
        holder.estimatedPrice.setText(history.getGrandTotalRupiah());
        PicassoLoader imageLoader = new PicassoLoader();
        holder.address.setText(history.getAddress());

        imageLoader.loadImage(holder.customerPhoto, Sample.BASE_URL_QWASH_PUBLIC + "", history.getWashersName());
        holder.washerName.setText(history.getWashersName());

        holder.status.setVisibility(View.VISIBLE);
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

    public interface OnHistoryItemClickListener {

        void onRootClick(View v, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.header)
        RobotoBoldTextView header;
        @BindView(R.id.vehicle_photo)
        ImageView vehiclePhoto;
        @BindView(R.id.vehicle)
        RobotoRegularTextView vehicle;
        @BindView(R.id.layout_vehicle)
        LinearLayout layoutVehicle;
        @BindView(R.id.date_in_progress)
        RobotoRegularTextView dateInProgress;
        @BindView(R.id.address)
        RobotoRegularTextView address;
        @BindView(R.id.customer_photo)
        AvatarView customerPhoto;
        @BindView(R.id.washer_name)
        RobotoRegularTextView washerName;
        @BindView(R.id.rating)
        ProperRatingBar rating;
        @BindView(R.id.layout_rating)
        LinearLayout layoutRating;
        @BindView(R.id.estimated_price)
        RobotoBoldTextView estimatedPrice;
        @BindView(R.id.status)
        RobotoRegularTextView status;
        @BindView(R.id.content)
        LinearLayout content;
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
