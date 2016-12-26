package com.ad.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ad.sample.R;
import com.ad.sample.adapter.RecyclerAdapter1;
import com.ad.sample.adapter.RecyclerAdapter3;

/**
 * Created by binderbyte on 24/12/16.
 */

public class SelectTransmissionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_transmission_user);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_select_transmission);

        RecyclerAdapter3 adapter = new RecyclerAdapter3(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //Layout manager for Recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
