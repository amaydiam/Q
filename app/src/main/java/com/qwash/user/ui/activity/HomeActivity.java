package com.qwash.user.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsIcons;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.addressfromgoogleapi.AddressMapsFromGoogleApi;
import com.qwash.user.api.client.order.OrderService;
import com.qwash.user.api.client.washer.WasherService;
import com.qwash.user.api.model.Address;
import com.qwash.user.api.model.AddressFromMapsResponse;
import com.qwash.user.api.model.order.CancelOrder;
import com.qwash.user.api.model.order.Washer;
import com.qwash.user.api.model.washer.ShowWasherOn;
import com.qwash.user.model.AddressUser;
import com.qwash.user.model.FindWasher;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.model.WasherAccepted;
import com.qwash.user.service.MessageFireBase;
import com.qwash.user.service.PushNotification;
import com.qwash.user.ui.fragment.DialogSelectAddressFragment;
import com.qwash.user.ui.fragment.PrepareOrder.NewOrder;
import com.qwash.user.ui.fragment.PrepareOrder.PrepareOrderFragment;
import com.qwash.user.ui.fragment.WasherOrderFragment;
import com.qwash.user.ui.widget.RobotoLightTextView;
import com.qwash.user.utils.Prefs;
import com.qwash.user.utils.ProgressDialogBuilder;
import com.qwash.user.utils.TextUtils;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.wefor.circularanim.CircularAnim;

public class HomeActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        PrepareOrderFragment.OnOrderedListener,
        WasherOrderFragment.OnWasherOrderListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraIdleListener, DialogSelectAddressFragment.DialogSelectAddressUserListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String TAG = "HomeActivity";
    @BindView(R.id.search)
    RobotoLightTextView search;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.layout_pick_location)
    RelativeLayout layoutPickLocation;
    @BindView(R.id.btn_pick_location)
    IconTextView btnPickLocation;
    @BindView(R.id.marker_pick_location)
    ImageView markerPickLocation;
    @BindView(R.id.btn_work)
    FloatingActionButton btnWork;
    @BindView(R.id.btn_home)
    FloatingActionButton btnHome;
    @BindView(R.id.btn_my_location)
    FloatingActionButton btnMyLocation;
    @BindView(R.id.layout_menu_home)
    View layoutMenuHome;
    @BindView(R.id.img_close_menu)
    ImageView imgCloseMenu;
    @BindView(R.id.img_menu_home)
    ImageView imgMenuHome;
    @BindView(R.id.img_menu_notification)
    ImageView imgMenuNotification;
    @BindView(R.id.img_menu_my_balance)
    ImageView imgMenuMyBalance;
    @BindView(R.id.img_menu_history)
    ImageView imgMenuHistory;
    @BindView(R.id.img_menu_help)
    ImageView imgMenuHelp;
    @BindView(R.id.img_menu_my_account)
    ImageView imgMenuMyAccount;
    @BindView(R.id.layout_find_washer)
    View layoutFindWasher;
    Fragment current_fragment = null;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private ArrayList<FindWasher> listWasher = new ArrayList<>();
    private PrepareOrder prepareOrder;
    private int urutan = 0;
    private WasherAccepted washerAccepted;
    private ProgressDialogBuilder dialogProgress;
    private boolean hasIdetify = false;
    private boolean isFind = false;
    private double current_latitude, current_longitude;
    private View mapView;
    PermissionListener permissionMapsListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapView = mapFragment.getView();
            mapFragment.getMapAsync(HomeActivity.this);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            String message = String.format(Locale.getDefault(), getString(R.string.message_denied), "ACCESS_FINE_LOCATION");
            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
        }


    };
    private boolean isHidden = true;
    private GoogleMap mMap;
    private boolean isLocationChanged = false;

    @OnClick({
            R.id.img_menu,
            R.id.img_search,
            R.id.search,
            R.id.btn_pick_location,
            // Sub Menu
            R.id.btn_close_menu,
            R.id.menu_home,
            R.id.menu_notification,
            R.id.menu_my_balance,
            R.id.menu_history,
            R.id.menu_help,
            R.id.menu_my_account,
            // quick Menu
            R.id.btn_work,
            R.id.btn_home,
            R.id.btn_close


    })
    void ClickMenu(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_menu:
                ShowMenuHome(imgMenu);
                break;
            case R.id.img_search:
                ToSearchActivity();
                break;

            case R.id.search:
                ToSearchActivity();
                break;
            case R.id.btn_pick_location:
                if (hasIdetify)
                    LoadPrepareOrderFragment();
                else
                    LoadAddress(current_latitude, current_longitude);
                break;

            // sub Menu
            case R.id.btn_close_menu:
                ShowMenuHome(imgCloseMenu);
                break;
            case R.id.menu_home:
                ShowMenuHome(imgMenuHome);
                break;
            case R.id.menu_notification:
                ShowMenuHome(imgMenuNotification);
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.menu_my_balance:
                ShowMenuHome(imgMenuMyBalance);
                startActivity(new Intent(this, MyBalanceActivity.class));
                break;
            case R.id.menu_history:
                ShowMenuHome(imgMenuHistory);
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.menu_help:
                ShowMenuHome(imgMenuHelp);
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.menu_my_account:
                ShowMenuHome(imgMenuMyAccount);
                startActivity(new Intent(this, MyAccountActivity.class));
                break;

            //quick menu
            case R.id.btn_work:
                if (!isHidden)
                    ShowMenuHome(imgMenu);
                OpenData(Sample.CODE_ADRESS_WORK);
                break;
            case R.id.btn_home:
                if (!isHidden)
                    ShowMenuHome(imgMenu);
                OpenData(Sample.CODE_ADRESS_HOME);
                break;
            case R.id.btn_close:
                if (TextUtils.isNullOrEmpty(prepareOrder.washersId)) {
                    isFind = false;
                    FindingWasher();
                    PushCancelOrder();
                } else
                    CancelOrder();
                break;

            default:
                break;
        }
    }

    private void OpenData(int type_address) {
        FragmentManager fm = getSupportFragmentManager();
        DialogSelectAddressFragment dialogSelectAddressFragment = DialogSelectAddressFragment.newInstance(type_address);
        dialogSelectAddressFragment.show(fm, "Select Adreess");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        dialogProgress = new ProgressDialogBuilder(HomeActivity.this);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        if (location != null) {
            search.setText("" + location);
        }

        imgMenu.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_menu)
                        .colorRes(R.color.font_color)
                        .actionBarSize());

        imgSearch.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_search)
                        .colorRes(R.color.font_color)
                        .actionBarSize());

        layoutMenuHome.bringToFront();
        imgCloseMenu.setImageDrawable(new IconDrawable(this, MaterialIcons.md_close).colorRes(R.color.white).actionBarSize());
        imgMenuHome.setImageDrawable(new IconDrawable(this, SimpleLineIconsIcons.icon_home).colorRes(R.color.white).actionBarSize());
        imgMenuNotification.setImageDrawable(new IconDrawable(this, MaterialIcons.md_notifications_none).colorRes(R.color.white).actionBarSize());
        imgMenuMyBalance.setImageDrawable(new IconDrawable(this, EntypoIcons.entypo_wallet).colorRes(R.color.white).actionBarSize());
        imgMenuHistory.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_history).colorRes(R.color.white).actionBarSize());
        imgMenuHelp.setImageDrawable(new IconDrawable(this, MaterialIcons.md_help_outline).colorRes(R.color.white).actionBarSize());
        imgMenuMyAccount.setImageDrawable(new IconDrawable(this, EntypoIcons.entypo_user).colorRes(R.color.white).actionBarSize());

        if (isHidden) {
            layoutMenuHome.setVisibility(View.INVISIBLE);
        }

        markerPickLocation.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_location_pin)
                        .colorRes(R.color.colorAccent)
                        .sizeDp(48));

        int paddingBottomInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (48 / 4) * 3, getResources()
                        .getDisplayMetrics());

        layoutPickLocation.setPadding(0, 0, 0, paddingBottomInDp);

        btnWork.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_bag)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());
        btnHome.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_home)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());
        btnMyLocation.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_my_location)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());

        pickLayoutLocationShow(false);


        new TedPermission(this)
                .setPermissionListener(permissionMapsListener)
                .setDeniedMessage("Jika kamu menolak permission, Anda tidak dapat mendeteksi lokasi Anda \nHarap hidupkan permission ACCESS_FINE_LOCATION pada [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        FindingWasher();

    }

    private void LoadPrepareOrderFragment() {
        PrepareOrder prepareOrder = new PrepareOrder();

        //TODO name adress
        prepareOrder.usersDetailsId = "";
        prepareOrder.userIdFk = Prefs.getUserId(this);
        prepareOrder.nameAddress = "-";
        prepareOrder.address = search.getText().toString();
        prepareOrder.lat = String.valueOf(current_latitude);
        prepareOrder.Long = String.valueOf(current_longitude);
        prepareOrder.type = "0";

        mMap.getUiSettings().setScrollGesturesEnabled(false);
        btnPickLocation.setVisibility(View.INVISIBLE);
        current_fragment = new PrepareOrderFragment().newInstance(prepareOrder);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_bottom, current_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void LoadWasherOrderFragment() {
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        btnPickLocation.setVisibility(View.INVISIBLE);
        current_fragment = new WasherOrderFragment().newInstance(prepareOrder, washerAccepted);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commitAllowingStateLoss();
    }

    private void RemoveBottomFragment() {
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        btnPickLocation.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().remove(current_fragment).commitAllowingStateLoss();
        current_fragment = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnCameraIdleListener(this);
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.GONE);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHidden)
                    ShowMenuHome(imgMenu);
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    float zoomLevel = mMap.getCameraPosition().zoom;
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (location == null) {
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Please activate location")
                                .setMessage("Click ok to goto settings.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                    } else {
                        // current location
                        current_latitude = location.getLatitude();
                        current_longitude = location.getLongitude();
                        LoadAddress(current_latitude, current_longitude);

                        pickLayoutLocationShow(true);

                        //move map camera
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_latitude, current_longitude), isLocationChanged ? zoomLevel : 14), 1500, null);
                        isLocationChanged = true;

                    }
                }
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        if (!isLocationChanged) {

            // current location
            current_latitude = location.getLatitude();
            current_longitude = location.getLongitude();
            LoadAddress(current_latitude, current_longitude);

            pickLayoutLocationShow(true);

            //move map camera
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_latitude, current_longitude), 14), 1500, null);
            isLocationChanged = true;
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void pickLayoutLocationShow(boolean show) {
        if (show)
            layoutPickLocation.setVisibility(View.VISIBLE);
        else
            layoutPickLocation.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
    }

    @Override
    public void onCancelOrder() {
        RemoveBottomFragment();
    }

    private void ShowMenuHome(View v) {

        if (isHidden) {
            CircularAnim.show(layoutMenuHome).duration(300).triggerView(v != null ? v : imgMenu).go();
            isHidden = false;

        } else {
            CircularAnim.hide(layoutMenuHome).duration(300).triggerView(v != null ? v : imgMenu).go();
            isHidden = true;

        }
    }


    // ======== CancelOrder Order
    private void CancelOrder() {
        {
            dialogProgress.show("Cancel Order Wash ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.WASHERS_ID, prepareOrder.washersId);

            OrderService mService = ApiUtils.OrderService(HomeActivity.this);
            mService.getCancelOrderLink(params).enqueue(new Callback<CancelOrder>() {
                @Override
                public void onResponse(Call<CancelOrder> call, Response<CancelOrder> response) {

                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            isFind = false;
                            FindingWasher();
                            PushCancelOrder();
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            // TODO
                            // kalo udah diterima orang finish();
                            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CancelOrder> call, Throwable t) {
                    String message = t.getMessage();
                    dialogProgress.hide();
                    Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void FindingWasher() {
        if (isFind) {
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            btnPickLocation.setVisibility(View.INVISIBLE);
            layoutFindWasher.setVisibility(View.VISIBLE);
        } else {
            layoutFindWasher.setVisibility(View.GONE);
            if (mMap != null)
                mMap.getUiSettings().setScrollGesturesEnabled(true);
            btnPickLocation.setVisibility(View.VISIBLE);
        }

    }

    private void ToSearchActivity() {
        Intent intent = new Intent(this, SelectLocationActivity.class);
        intent.putExtra("input", search.getText().toString());
        startActivity(intent);
    }

    public void LoadAddress(final double Lat, final double Long) {
        hasIdetify = false;
        search.setText("");
        btnPickLocation.setText(Html.fromHtml("<i>{fa-spinner spin} Indentify Address...</i>"));
        AddressMapsFromGoogleApi mService = ApiUtils.getAddressMapsFromGoogleApi(this);
        mService.getAddress(Lat + "," + Long).enqueue(new Callback<AddressFromMapsResponse>() {
            @Override
            public void onResponse(Call<AddressFromMapsResponse> call, Response<AddressFromMapsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("OK")) {
                        List<Address> address = response.body().getResults();
                        search.setText(address.get(0).getFormattedAddress());
                        btnPickLocation.setText(getResources().getString(R.string.btn_pick_location));
                        GetWasherOnline(Lat, Long);
                    } else {
                        search.setText("");
                        btnPickLocation.setText(Html.fromHtml("<i>Not Identified...</i>"));
                    }
                    hasIdetify = true;

                } else {
                    search.setText("");
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    search.setText("");
                    btnPickLocation.setText(Html.fromHtml("<i>Not Identified...</i>"));
                    hasIdetify = false;
                }
            }

            @Override
            public void onFailure(Call<AddressFromMapsResponse> call, Throwable t) {
                btnPickLocation.setText(getResources().getString(R.string.retry));
                hasIdetify = false;

            }


        });
    }

    public void GetWasherOnline(double Lat, double Long) {

        WasherService mService = ApiUtils.WasherService(this);
        mService.getshowWasherOnLink("Bearer " + Prefs.getToken(HomeActivity.this), String.valueOf(Lat), String.valueOf(Long)).enqueue(new Callback<ShowWasherOn>() {
            @Override
            public void onResponse(Call<ShowWasherOn> call, Response<ShowWasherOn> response) {

                if (response.isSuccessful()) {
                    List<Washer> washer = response.body().getWashers();
                    listWasher.clear();

                    for (int i = 0; i < washer.size(); i++) {
                        String user_id_fk = washer.get(i).getUserId();
                        String name = washer.get(i).getFullName();
                        String latlong = washer.get(i).getGeometryLat() + "," + washer.get(i).getGeometryLong();
                        String firebaseId = washer.get(i).getFirebaseId();
                        FindWasher findWasher = new FindWasher(user_id_fk, name, latlong, firebaseId, false);
                        listWasher.add(findWasher);

                    }

                    setWasherOnMap(washer);

                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<ShowWasherOn> call, Throwable t) {
            }


        });
    }


    @Override
    public void onCameraIdle() {
        current_latitude = mMap.getCameraPosition().target.latitude;
        current_longitude = mMap.getCameraPosition().target.longitude;
        LoadAddress(current_latitude, current_longitude);
    }


    void setWasherOnMap(List<Washer> data) {
        if (data != null) {
            mMap.clear();
            for (int i = 0; i < data.size(); i++) {
                double Lat = data.get(i).getGeometryLat();
                double Long = data.get(i).getGeometryLong();
                MarkerOptions marker = new MarkerOptions().position(new LatLng(Lat, Long)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_washer));
                mMap.addMarker(marker);
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isHidden)
            ShowMenuHome(imgCloseMenu);
        else
            finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageFireBase(MessageFireBase MessageFireBase) {

        try {
            JSONObject json = new JSONObject(MessageFireBase.getData());
            int action = json.getInt(Sample.ACTION);
            if (action == Sample.CODE_DEACLINE_ORDER) {
                TastyToast.makeText(getApplicationContext(), "Your washer is deacline order", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                isFind = false;
                FindingWasher();
                RemoveBottomFragment();

            } else if (action == Sample.CODE_ACCEPT_ORDER) {
                TastyToast.makeText(getApplicationContext(), "Washer found", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                JSONObject jsonOrder= new JSONObject(json.getString(Sample.ORDER));

                JSONObject jsonDetails = new JSONObject(jsonOrder.getString(Sample.DETAILS));
                String ordersId = jsonDetails.getString(Sample.ORDERS_ID);

                JSONObject jsonWasher = new JSONObject(jsonOrder.getString(Sample.WASHER));
                String firebase_id = jsonWasher.getString(Sample.WASHER_FIREBASE_ID);
                String washersId = jsonWasher.getString(Sample.WASHERS_ID);
                String email = jsonWasher.getString(Sample.WASHER_EMAIL);
                String name = jsonWasher.getString(Sample.WASHER_NAME);
                String phone = jsonWasher.getString(Sample.WASHER_USERNAME);
                String photo = jsonWasher.getString(Sample.WASHER_PHOTO);
                String rating = jsonWasher.getString(Sample.WASHER_RATING);

                washerAccepted = new WasherAccepted(firebase_id, washersId, email, name, phone, photo, rating);

                isFind = false;
                FindingWasher();
                LoadWasherOrderFragment();
            } else if (action == Sample.CODE_START_WORKING) {
                TastyToast.makeText(getApplicationContext(), "Your washer is start working", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                WasherOrderFragment fragment = (WasherOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bottom);
                if (fragment != null) {
                    fragment.layoutBtnWasherOrder.setVisibility(View.GONE);
                }
            } else if (action == Sample.CODE_FINISH_WORKING) {
                TastyToast.makeText(getApplicationContext(), "Your washer is finish working", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                isFind = false;
                FindingWasher();
                RemoveBottomFragment();
                OpenActionRating();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void OpenActionRating() {
        Intent intent = new Intent(this, RatingActivity.class);
        intent.putExtra(Sample.PREPARE_ORDER_OBJECT, prepareOrder);
        intent.putExtra(Sample.WASHER_ACCEPTED_OBJECT, washerAccepted);
        washerAccepted = null;
        prepareOrder = null;
        startActivity(intent);
    }


    private void SearchWasher() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < listWasher.size(); i++) {
                        jsonArray.put(listWasher.get(i).firebaseId);
                    }

                    JSONObject notification = new JSONObject();
                    notification.put("body", "GET NOW !!!");
                    notification.put("title", "Qwash - NEW ORDER");

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.ACTION_ORDER);

                    JSONObject order = new JSONObject();

                    JSONObject whasher = new JSONObject();
                    whasher.put(Sample.WASHERS_ID, prepareOrder.washersId);
                    order.put(Sample.WASHER, whasher);

                    JSONObject customer = new JSONObject();
                    customer.put(Sample.ORDER_USERID, prepareOrder.customersId);
                    customer.put(Sample.ORDER_USERNAME, prepareOrder.username);
                    customer.put(Sample.ORDER_EMAIL, prepareOrder.email);
                    customer.put(Sample.ORDER_NAME, prepareOrder.name);
                    customer.put(Sample.ORDER_FIREBASE_ID, prepareOrder.firebase_id);
                    order.put(Sample.CUSTOMER, customer);


                    JSONObject address = new JSONObject();
                    address.put(Sample.ORDER_USERSDETAILSID, prepareOrder.usersDetailsId);
                    address.put(Sample.ORDER_USERIDFK, prepareOrder.userIdFk);
                    address.put(Sample.ORDER_NAMEADDRESS, prepareOrder.nameAddress);
                    address.put(Sample.ORDER_ADDRESS, prepareOrder.address);
                    address.put(Sample.ORDER_LAT, prepareOrder.lat);
                    address.put(Sample.ORDER_LONG, prepareOrder.Long);
                    address.put(Sample.ORDER_TYPE, prepareOrder.type);
                    order.put(Sample.ADDRESS, address);

                    JSONObject vehicle = new JSONObject();
                    vehicle.put(Sample.ORDER_VEHICLES_TYPE, prepareOrder.vehicles_type);
                    vehicle.put(Sample.ORDER_VEHICLES, prepareOrder.vehicles);
                    order.put(Sample.VEHICLE, vehicle);

                    JSONObject details = new JSONObject();
                    details.put(Sample.ORDER_PRICE, String.valueOf(prepareOrder.price));

                    details.put(Sample.ORDER_PERFUM_PRICE, String.valueOf(prepareOrder.perfum_price));
                    details.put(Sample.ORDER_PERFUM_STATUS, String.valueOf(prepareOrder.perfum_status));

                    details.put(Sample.ORDER_INTERIOR_VACUUM_PRICE, String.valueOf(prepareOrder.interior_vaccum_price));
                    details.put(Sample.ORDER_INTERIOR_VACUUM_STATUS, String.valueOf(prepareOrder.interior_vaccum_status));

                    details.put(Sample.ORDER_WATERLESS_PRICE, String.valueOf(prepareOrder.waterless_price));
                    details.put(Sample.ORDER_WATERLESS_STATUS, String.valueOf(prepareOrder.waterless_status));

                    details.put(Sample.ORDER_ESTIMATED_PRICE, String.valueOf(prepareOrder.estimated_price));

                    order.put(Sample.DETAILS, details);

                    data.put(Sample.ORDER, order);

                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    Log.v("result", result);

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("Error", ex.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
            }
        }.execute();
    }

    private void PushCancelOrder() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < listWasher.size(); i++) {
                        jsonArray.put(listWasher.get(i).firebaseId);
                    }

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.ACTION_CANCEL_ORDER);
                    data.put(Sample.MESSAGE, "cancel_order");

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    Log.v("result", result);

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("Error", ex.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
            }
        }.execute();
    }

    @Override
    public void onFinishDialogSelectAddressUserDialog(AddressUser addressUser) {
        String LatLong = addressUser.getLatlong();
        String[] X = LatLong.split(",");
        current_latitude = Double.parseDouble(X[0]);
        current_longitude = Double.parseDouble(X[1]);
        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_latitude, current_longitude), 14), 1500, null);
        isLocationChanged = true;
        LoadAddress(current_latitude, current_longitude);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Subscribe
    public void startNewOrder(NewOrder newOrder) {
        prepareOrder = newOrder.getNewOrder();
        if (prepareOrder != null) {

            prepareOrder.washersId = "";

            prepareOrder.customersId = Prefs.getUserId(this);
            prepareOrder.username = Prefs.getUsername(this);
            prepareOrder.email = Prefs.getEmail(this);
            prepareOrder.name = Prefs.getFullName(this);
            prepareOrder.firebase_id = Prefs.getFirebaseId(this);
            SearchWasher();
            RemoveBottomFragment();
            isFind = true;
            FindingWasher();
        }

    }


}
