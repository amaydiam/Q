package com.ad.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ad.sample.R;
import com.ad.sample.adapter.RecyclerAdapter1;
import com.ad.sample.adapter.RecyclerAdapter2;
import com.ad.sample.adapter.RecyclerAdapter4;

/**
 * Created by binderbyte on 24/12/16.
 */

public class SelectYearActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_year_user);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view_select_year);

        RecyclerAdapter4 adapter = new RecyclerAdapter4(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //Layout manager for Recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
