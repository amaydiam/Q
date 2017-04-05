package com.qwash.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qwash.user.ui.activity.BaseActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.qwash.user.R;
import com.qwash.user.adapter.LocationAdapter;
import com.qwash.user.api.client.searchlocation.SearchLocationInterface;
import com.qwash.user.api.model.ListLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectLocationActivity extends BaseActivity {

    public static final String BASE_URL = "https://maps.googleapis.com";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    EditText searchView;
    @BindView(R.id.clear)
    ImageView clear;
    List<com.qwash.user.api.model.ListLocation.Prediction> ListLocation;
    String input;
    private LocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @OnClick(R.id.clear)
    void clearSearch() {
        searchView.setText("");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        input = intent.getStringExtra("input");

        if (!input.equals("")) {
            searchView.setText(input + "");
            searchView.setSelection(input.length());
            showData("" + input);
        }

        autoCompleteSearch();
    }

    public void autoCompleteSearch() {
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showData(searchView.getText().toString());

                    //Toast.makeText(SelectLocationActivity.this, "Tes", Toast.LENGTH_SHORT).show();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.equals("")) {
                    showData("" + s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void showData(String input) {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Accept", "Application/JSON").build();
                                return chain.proceed(request);
                            }
                        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchLocationInterface service = retrofit.create(SearchLocationInterface.class);

        Call<ListLocation> call = service.getInput(input, "geocode", "id", "AIzaSyAFDCJAsZ-vkOJeu11Kw4G2o8zu6NOk_qQ");

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ListLocation>() {

            @Override
            public void onResponse(Call<ListLocation> call, retrofit2.Response<ListLocation> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    progressBar.setVisibility(View.GONE);
                    ListLocation = new ArrayList<>();
                    ListLocation result = response.body();
                    ListLocation = result.getPredictions();

                    // This is where dataBrands loads
                    mAdapter = new LocationAdapter(ListLocation);

                    //attach to recyclerview
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<ListLocation> call, Throwable t) {
                Toast.makeText(SelectLocationActivity.this, "Aktifkan koneksi anda !", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
