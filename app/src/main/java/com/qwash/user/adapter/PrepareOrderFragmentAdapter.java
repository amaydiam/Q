package com.qwash.user.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qwash.user.ui.fragment.PrepareOrder.FiturServiceFragment;
import com.qwash.user.ui.fragment.PrepareOrder.SelectVehicleFragment;

public class PrepareOrderFragmentAdapter extends FragmentStatePagerAdapter {
    public PrepareOrderFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new SelectVehicleFragment();
                break;
            case 1:
                frag = new FiturServiceFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }


}
