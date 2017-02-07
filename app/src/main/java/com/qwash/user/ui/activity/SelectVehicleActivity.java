package com.qwash.user.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.adapter.VehicleAdapter;
import com.qwash.user.model.VehicleUser;
import com.qwash.user.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aan on 12/21/16.
 */

public class SelectVehicleActivity extends AppCompatActivity implements VehicleAdapter.OnVehicleItemClickListener {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindBool(R.bool.is_tablet)
    boolean isTablet;
    private List<VehicleUser> data = new ArrayList<>();
    private VehicleAdapter adapter;
    private MenuItem acOk;
    private VehicleUser vehicle = null;
    private String selected_id =null;

    @OnClick(R.id.btn_add_vehicle)
    public void onClickAddVehicle() {
        //new activity
        Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleActivity.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_vehicle_user);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        vehicle = (VehicleUser) intent.getParcelableExtra(Sample.VEHICLE_OBJECT);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.select_vehicle));

        data = VehicleUser.listAll(VehicleUser.class);
        //inisial adapter
        adapter = new VehicleAdapter(this, data);
        adapter.setOnVehicleItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getNumberOfColumns()));

        // set adapter
        recyclerView.setAdapter(adapter);

        setPosisitionSelected();

    }

    private void setPosisitionSelected() {
        if (vehicle != null) {
            adapter.setSelectionByIdVehicle(vehicle.getvCustomersId());
            selected_id =vehicle.getvCustomersId();
        }
        else
            adapter.setSelection(0);
    }


    public int getNumberOfColumns() {
        // Get screen width
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float widthPx = displayMetrics.widthPixels;
        if (isTablet) {
            widthPx = widthPx / 3;
        }
        // Calculate desired width

        float desiredPx = getResources().getDimensionPixelSize(R.dimen.list_card_width);
        int columns = Math.round(widthPx / desiredPx);
        return columns > 1 ? columns : 2;
    }


    @Override
    public void onActionClick(View v, int position) {

    }

    @Override
    public void onRootClick(View v, int position) {
        adapter.setSelection(position);
        getSelectedId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ok_menu, menu);
        acOk = menu.findItem(R.id.action_ok);
        acOk.setIcon(
                new IconDrawable(this, MaterialCommunityIcons.mdi_check)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        acOk.setVisible(false);
        ShowHideAcOk();
        return true;
    }

    private void ShowHideAcOk() {
        try {

            if (adapter.getSelection() == -1) {
                acOk.setVisible(false);
            } else {
                acOk.setVisible(true);
            }
            supportInvalidateOptionsMenu();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_ok:

                Intent intent = new Intent();
                intent.putExtra(Sample.VEHICLE_OBJECT, adapter.data.get(adapter.getSelection()));
                setResult(Activity.RESULT_OK, intent);
                finish();

                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                VehicleUser vehicle = (VehicleUser) data.getParcelableExtra(Sample.VEHICLE_OBJECT);
                if (vehicle != null) {
                    this.data.add(vehicle);
                    adapter.notifyDataSetChanged();
                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.delete_all();
        adapter.data = VehicleUser.listAll(VehicleUser.class);
        adapter.notifyDataSetChanged();
        if(!TextUtils.isNullOrEmpty(selected_id)){
            adapter.setSelectionByIdVehicle(selected_id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getSelectedId() {
        selected_id = adapter.getSelectedId();
    }
}
