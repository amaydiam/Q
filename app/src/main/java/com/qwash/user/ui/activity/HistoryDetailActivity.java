package com.qwash.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qwash.user.R;
import com.qwash.user.Sample;
//import com.qwash.user.ui.fragment.HistoryDetailFragment;

public class HistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String mustahiqId = intent.getStringExtra(Sample.ORDERS_ID);
            loadHistoryDetailsOf(mustahiqId);
        }
    }

    private void loadHistoryDetailsOf(String ordersId) {
    /*    HistoryDetailFragment fragment = new HistoryDetailFragment();
        Bundle args = new Bundle();
        args.putString(Sample.ORDERS_ID, ordersId);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_detail_container, fragment).commit();
    */}


}
