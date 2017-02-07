package com.qwash.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qwash.user.R;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.model.AddressUser;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.ui.widget.RobotoRegularTextView;

import java.util.List;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 24/12/16.
 */

public class AddressUserAdapter extends RecyclerView.Adapter<AddressUserAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    private final GestureDetector gestureDetector;
    private final PicassoLoader imageLoader;
    public List<AddressUser> data = null;
    private Activity activity;
    private OnAddressUserItemClickListener OnAddressUserItemClickListener;


    public AddressUserAdapter(Activity activity, List<AddressUser> items) {
        this.activity = activity;
        this.data = items;
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());
        imageLoader = new PicassoLoader();
    }




    public void setOnAddressUserItemClickListener(OnAddressUserItemClickListener onAddressUserItemClickListener) {
        this.OnAddressUserItemClickListener = onAddressUserItemClickListener;
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
        if (OnAddressUserItemClickListener != null) {
            OnAddressUserItemClickListener.onRootClick(v, (Integer) v.getTag());
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
                .inflate(R.layout.item_list_address_user, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        AddressUser dataObject = data.get(position);
        imageLoader.loadImage(holder.photo, ApiUtils.getImageAddressMapsFromGoogleApi(dataObject.getLatlong()), dataObject.getNameAddress());
        holder.nameAddress.setText(dataObject.getNameAddress());
        holder.address.setText(dataObject.getAddress());
        holder.rootParent.setTag(position);
    }

    public int getItemCount() {
        return data.size();
    }

    public void remove(int position) {
        data.remove(data.get(position));
        notifyDataSetChanged();
    }

    public interface OnAddressUserItemClickListener {
        void onRootClick(View v, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo)
        AvatarView photo;
        @BindView(R.id.name_address)
        RobotoBoldTextView nameAddress;
        @BindView(R.id.address)
        RobotoRegularTextView address;
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
