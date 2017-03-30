package com.qwash.user.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.widget.IconButton;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.adapter.HistoryAdapter;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.history.HistoryService;
import com.qwash.user.api.model.history.HistoryListResponse;
import com.qwash.user.model.History;
import com.qwash.user.ui.activity.HistoryActivity;
import com.qwash.user.ui.activity.HistoryDetailActivity;
import com.qwash.user.utils.Prefs;
import com.qwash.user.utils.TextUtils;
import com.qwash.user.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by binderbyte on 10/01/17.
 */
public class HistoryFragment extends Fragment implements HistoryAdapter.OnHistoryItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_MORE = "TAG_MORE";
    private static final String TAG_AWAL = "TAG_AWAL";
    private static final String TAG_NEW = "TAG_NEW";

    private static final String TAG_TOP = "top";
    private static final String TAG_BOTTOM = "bottom";
    public HistoryAdapter adapter;
    @BindBool(R.bool.is_tablet)
    boolean isTablet;
    @BindView(R.id.btn_search)
    IconButton btnSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progress_more_data)
    ProgressBar progressMoreData;
    @BindView(R.id.no_data)
    IconButton noData;
    @BindView(R.id.scroll_up)
    ImageView scrollUp;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fab_action)
    FloatingActionButton fabAction;
    //error
    @BindView(R.id.error_message)
    View errorMessage;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.text_error)
    TextView textError;
    @BindView(R.id.try_again)
    TextView tryAgain;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.parent_search)
    CardView parentSearch;
    private ArrayList<History> data = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private Integer position_delete;
    private ProgressDialog dialogProgress;
    private FragmentActivity activity;
    private Unbinder butterknife;
    private boolean isFinishLoadingAwalData = true;
    private boolean isLoadingMoreData = false;
    private boolean isFinishMoreData = false;
    private int page = 1;
    private boolean isRefresh = false;
    private int mPreviousVisibleItem;

    public HistoryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        //  args.putInt(Sample.TYPE_WASH_HISTORY, type_wash_history);
        fragment.setArguments(args);
        return fragment;
    }
    //  private String session_key;

    @OnClick(R.id.scroll_up)
    void ScrollUp() {
        recyclerView.smoothScrollToPosition(0);
    }

    @OnClick(R.id.btn_search)
    void btn_search() {
        Search();
    }

    @OnClick(R.id.try_again)
    void TryAgain() {
        RefreshData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //  type_wash_history = getArguments().getInt(Sample.TYPE_WASH_HISTORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        butterknife = ButterKnife.bind(this, rootView);


        // Configure the swipe refresh layout
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.blue_light,
                R.color.green_light, R.color.orange_light, R.color.red_light);
        TypedValue typed_value = new TypedValue();
        activity.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        swipeContainer.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));

        //search
        parentSearch.setVisibility(View.GONE);
        //search.setHint("Cari alamat...");
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String val_search = search.getText().toString().trim();
                Search();
                return false;
            }
        });

        Utils.hideSoftKeyboard(getActivity());

        //inisial adapter
        adapter = new HistoryAdapter(activity, data, isTablet);
        adapter.setOnHistoryItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(activity, Utils.getNumberOfColumns(getActivity(), isTablet));

        // set layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        // set adapter
        recyclerView.setAdapter(adapter);

        //handle ringkas data
        Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                if (isFinishLoadingAwalData
                        && !isFinishMoreData
                        && adapter.getItemCount() > 0) {
                    getDataFromServer(TAG_MORE);
                }
            }

            @Override
            public boolean isLoading() {
                return isLoadingMoreData;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();


        fabAction.setVisibility(View.GONE);

        scrollUp.setImageDrawable(
                new IconDrawable(getActivity(), MaterialCommunityIcons.mdi_arrow_up)
                        .colorRes(R.color.blue_1E87DA));


        noData.setText(Html.fromHtml("<center><h1>{mdi-calendar}</h1></br> Tidak ada history ...</center>"));
        showNoData(false);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Sample.DATA)) {
            getDataFromServer(TAG_AWAL);
        } else {
            data = savedInstanceState.getParcelableArrayList(Sample.DATA);
            page = savedInstanceState.getInt(Sample.PAGE);
            isLoadingMoreData = savedInstanceState.getBoolean(Sample.IS_LOADING_MORE_DATA);
            isFinishLoadingAwalData = savedInstanceState.getBoolean(Sample.IS_FINISH_LOADING_AWAL_DATA);

            if (!isFinishLoadingAwalData) {
                getDataFromServer(TAG_AWAL);
            } else if (isLoadingMoreData) {
                adapter.notifyDataSetChanged();
                checkData();
                getDataFromServer(TAG_MORE);
            } else {
                adapter.notifyDataSetChanged();
                checkData();
            }
        }

        return rootView;
    }

    private void Search() {
        String val_search = search.getText().toString().trim();
        if (!TextUtils.isNullOrEmpty(val_search)) {
            search.setText("");
          /*  Intent intent = new Intent(activity, CariHistoryActivity.class);
            intent.putExtra(Sample.KEYWORD, val_search);
            startActivity(intent);*/
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mLayoutManager != null && adapter != null) {
            outState.putBoolean(Sample.IS_FINISH_LOADING_AWAL_DATA, isFinishLoadingAwalData);
            outState.putBoolean(Sample.IS_LOADING_MORE_DATA, isLoadingMoreData);
            outState.putInt(Sample.PAGE, page);
            outState.putParcelableArrayList(Sample.DATA, data);
        }
    }

    private void showProgresMore(boolean show) {
        if (show) {
            progressMoreData.setVisibility(View.VISIBLE);
        } else {
            progressMoreData.setVisibility(View.GONE);
        }
    }

    private void showNoData(boolean show) {
        if (show) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }

    private void ProgresRefresh(boolean show) {
        if (show) {
            swipeContainer.setRefreshing(true);
            swipeContainer.setEnabled(false);
        } else {
            swipeContainer.setEnabled(true);
            swipeContainer.setRefreshing(false);
        }
    }

    private void getDataFromServer(final String TAG) {

        onRetrofitStart(TAG);

        HistoryService mService = ApiUtils.getHistory(getActivity());
        mService.getListHistory(Prefs.getUserId(getActivity()), page, Sample.LIMIT_DATA).enqueue(new Callback<HistoryListResponse>() {
            @Override
            public void onResponse(Call<HistoryListResponse> call, Response<HistoryListResponse> response) {

                if (response.isSuccessful()) {
                    onRetrofitSuccessResponse(TAG, response);
                } else {
                    int statusCode = response.code();
                    onRetrofitErrorResponse(TAG, statusCode);
                }
                onRetrofitEnd(TAG);
            }

            @Override
            public void onFailure(Call<HistoryListResponse> call, Throwable t) {
                onRetrofitErrorResponse(TAG, 0);
                onRetrofitEnd(TAG);
            }

        });
    }

    protected void DrawDataAllData(String position, String tag, Response<HistoryListResponse> response) {

        if (isRefresh) {
            adapter.delete_all();
        }

        Boolean isSuccess = response.body().getStatus();
        if (isSuccess) {
            List<History> h = response.body().getHistory();
            int jumlah_list_data = h.size();
            if (jumlah_list_data > 0) {
                for (int i = 0; i < jumlah_list_data; i++) {
                    History obj = h.get(i);
                    setDataObject(position, obj);
                }
                adapter.notifyDataSetChanged();
            } else {
                switch (tag) {
                    case TAG_MORE:
                        isFinishMoreData = true;
                        //       Toast.makeText(activity, "tidak ada data lama...", Toast.LENGTH_LONG, Toast.INFO);
                        break;
                    case TAG_AWAL:
                        //      Toast.makeText(activity, "tidak ada data...", Toast.LENGTH_LONG, Toast.INFO);
                        break;
                    case TAG_NEW:
                        //     Toast.makeText(activity, "tidak ada data baru...", Toast.LENGTH_LONG, Toast.INFO);
                        break;
                }
            }


            page = page + 1;
        }
        checkData();


    }

    private void checkData() {
        if (adapter.getItemCount() > 0) {

            showNoData(false);
        } else {
            showNoData(true);
        }
    }



    private void setDataObject(String position, History history) {

        String ordersId = history.getOrdersId();
        String userIdFk = history.getUserIdFk();
        String washerIdFk = history.getWasherIdFk();
        String vCustomersIdFk = history.getVCustomersIdFk();
        String createAt = history.getCreateAt();
        String pickDate = history.getPickDate();
        String pickTime = history.getPickTime();
        String lat = history.getLat();
        String lng = history.getLng();
        String nameAddress = history.getNameAddress();
        String address = history.getAddress();
        String price = history.getPrice();
        String perfumed = history.getPerfumed();
        String vacuum = history.getVacuum();
        String status = history.getStatus();
        String description = history.getDescription();
        String ordersRef = history.getOrdersRef();
        String name = history.getName();
        String photo = history.getPhoto();
        String vBrand = history.getVBrand();

        //parse object

        //set map object
        AddAndSetMapData(
                position,
                ordersId,
                userIdFk,
                washerIdFk,
                vCustomersIdFk,
                createAt,
                pickDate,
                pickTime,
                lat,
                lng,
                nameAddress,
                address,
                price,
                perfumed,
                vacuum,
                status,
                description,
                ordersRef,
                name,
                photo,
                vBrand
        );

    }

    private void AddAndSetMapData(String position, String ordersId, String userIdFk, String washerIdFk, String vCustomersIdFk, String createAt, String pickDate, String pickTime, String lat, String lng, String nameAddress, String address, String price, String perfumed, String vacuum, String status, String description, String ordersRef, String name, String photo, String vBrand) {

        History history = new History(
                ordersId,
                userIdFk,
                washerIdFk,
                vCustomersIdFk,
                createAt,
                pickDate,
                pickTime,
                lat,
                lng,
                nameAddress,
                address,
                price,
                perfumed,
                vacuum,
                status,
                description,
                ordersRef,
                name,
                photo,
                vBrand);


        if (position.equals(TAG_BOTTOM)) {
            data.add(history);
        } else {
            data.add(0, history);
        }
    }


    @Override
    public void onRefresh() {
        RefreshData();
    }

    public void RefreshData() {
        // if (adapter.getItemCount() > 1) {
        isRefresh = true;
        isLoadingMoreData = false;
        isFinishLoadingAwalData = true;
        isFinishMoreData = false;
        page = 1;
        showNoData(false);
       /* } else {
            isRefresh = false;
        }*/
        getDataFromServer(TAG_AWAL);
    }


    public void onRetrofitStart(String TAG) {
            showProgresMore(false);
            if (TAG.equals(TAG_AWAL)) {
                ProgresRefresh(true);
                isFinishLoadingAwalData = false;
                errorMessage.setVisibility(View.GONE);
                if (adapter.getItemCount() == 0) {
                    loading.setVisibility(View.VISIBLE);
                }

            } else {
                if (TAG.equals(TAG_MORE)) {
                    isLoadingMoreData = true;
                    isFinishMoreData = false;
                    showProgresMore(true);
                }
            }
    }

    public void onRetrofitEnd(String TAG) {
            ProgresRefresh(false);
            if (TAG.equals(TAG_AWAL)) {
                loading.setVisibility(View.GONE);
            }
        }

    public void onRetrofitSuccessResponse(String TAG, Response<HistoryListResponse> response) {

            if (TAG.equals(TAG_AWAL)) {
                errorMessage.setVisibility(View.GONE);
                DrawDataAllData(TAG_BOTTOM, TAG, response);
                isFinishLoadingAwalData = true;
            }
            if (TAG.equals(TAG_MORE)) {
                DrawDataAllData(TAG_BOTTOM, TAG, response);
                isLoadingMoreData = false;
            }
            if (TAG.equals(TAG_NEW)) {
                DrawDataAllData(TAG_TOP, TAG, response);
            }

            isRefresh = false;
            showProgresMore(false);
    }


    void onRetrofitErrorResponse(String TAG, int statusCode) {
            if (TAG.equals(TAG_AWAL)) {
                isFinishLoadingAwalData = false;
                if (adapter.getItemCount() == 0) {
                    errorMessage.setVisibility(View.VISIBLE);
                } else {
                    errorMessage.setVisibility(View.GONE);
                }
            }
            if (TAG.equals(TAG_MORE)) {
                isFinishMoreData = false;
                isLoadingMoreData = false;
                showProgresMore(false);
            }
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknife.unbind();

    }


    @Override
    public void onRootClick(View v, int position) {
        if (isTablet) {
            adapter.setSelected(position);
            ((HistoryActivity) getActivity()).loadDetailHistoryFragmentWith(adapter.data.get(position).getOrdersRef());
        } else {
            Intent intent = new Intent(activity, HistoryDetailActivity.class);
            intent.putExtra(Sample.WASHERS_ID, adapter.data.get(position).getOrdersRef());
            startActivity(intent);
        }

    }

}
