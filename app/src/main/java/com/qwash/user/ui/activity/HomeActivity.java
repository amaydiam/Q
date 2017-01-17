package com.qwash.user.ui.activity;

import android.Manifest;
import android.app.Activity;
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

import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.addressfromgoogleapi.AddressMapsFromGoogleApi;
import com.qwash.user.api.client.washer.WasherService;
import com.qwash.user.api.model.Address;
import com.qwash.user.api.model.AddressFromMapsResponse;
import com.qwash.user.api.model.washer.DataNearbyWasher;
import com.qwash.user.api.model.washer.NearbyWasher;
import com.qwash.user.model.FindWasher;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.model.VehicleUser;
import com.qwash.user.model.WasherAccepted;
import com.qwash.user.ui.fragment.PrepareOrderFragment;
import com.qwash.user.ui.fragment.WasherOrderFragment;
import com.qwash.user.ui.widget.RobotoLightTextView;
import com.qwash.user.utils.Prefs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        LocationListener, GoogleMap.OnCameraIdleListener {

    private static final String TAG = "HomeActivity";
    @BindView(R.id.search)
    RobotoLightTextView search;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    private AddressMapsFromGoogleApi mService;

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

    private OkHttpClient mClient = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ArrayList<FindWasher> listWasher = new ArrayList<>();
    private PrepareOrder prepareOrder;
    private int urutan = 0;
    private WasherAccepted washerAccepted;


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
                    LoadAddress(current_latitude + "," + current_longitude);
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
                String firebase_id = FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(this, "" + firebase_id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_home:
                if (!isHidden)
                    ShowMenuHome(imgMenu);
                break;
            case R.id.btn_close:
                isFind = false;
                FindingWasher();
                break;

            default:
                break;
        }
    }


    private boolean hasIdetify = false;
    private boolean isFind = false;

    Fragment current_fragment = null;
    private double current_latitude, current_longitude;
    private View mapView;
    private boolean isHidden = true;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

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

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        //Toast.makeText(this, ""+location, Toast.LENGTH_SHORT).show();

        if (location != null) {
            search.setText("" + location);
        }


        mService = ApiUtils.getAddressMapsFromGoogleApi(this);
        //set Toolbar

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
                        .colorRes(R.color.font_color)
                        .actionBarSize());
        btnHome.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_home)
                        .colorRes(R.color.font_color)
                        .actionBarSize());
        btnMyLocation.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_my_location)
                        .colorRes(R.color.font_color)
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
        prepareOrder.nameAddress = "";
        prepareOrder.address = search.getText().toString();
        prepareOrder.latlong = current_latitude + "," + current_longitude;
        prepareOrder.type = "0";

        mMap.getUiSettings().setScrollGesturesEnabled(false);
        btnPickLocation.setVisibility(View.INVISIBLE);
        current_fragment = new PrepareOrderFragment().newInstance(prepareOrder);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commit();
    }

    private void LoadWasherOrderFragment() {
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        btnPickLocation.setVisibility(View.INVISIBLE);
        current_fragment = new WasherOrderFragment().newInstance(washerAccepted);
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
        setWasher();

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
                        double Lat = location.getLatitude();
                        double Long = location.getLongitude();
                        LoadAddress(Lat + "," + Long);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Long), zoomLevel);
                        mMap.animateCamera(cameraUpdate);
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

        // current location
        current_latitude = location.getLatitude();
        current_longitude = location.getLongitude();
        LoadAddress(current_latitude + "," + current_longitude);

        pickLayoutLocationShow(true);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(current_latitude, current_longitude)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

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


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                prepareOrder = (PrepareOrder) data.getSerializableExtra(Sample.PREPARE_ORDER_OBJECT);
                if (prepareOrder != null) {
                    prepareOrder.userId = Prefs.getUserId(this);
                    prepareOrder.username = Prefs.getUsername(this);
                    prepareOrder.email = Prefs.getEmail(this);
                    prepareOrder.name = Prefs.getName(this);
                    prepareOrder.phone = Prefs.getPhone(this);
                    prepareOrder.authLevel = Prefs.getAuthLevel(this);
                    prepareOrder.firebase_id = Prefs.getFirebaseId(this);


                  /*  Map<String, String> params = new HashMap<>();
                    // customer order
                    params.put(Sample.ORDER_USERID, prepareOrder.userId);
                    params.put(Sample.ORDER_USERNAME, prepareOrder.username);
                    params.put(Sample.ORDER_EMAIL, prepareOrder.email);
                    params.put(Sample.ORDER_NAME, prepareOrder.name);
                    params.put(Sample.ORDER_PHONE, prepareOrder.phone);
                    params.put(Sample.ORDER_AUTHLEVEL, prepareOrder.authLevel);
                    params.put(Sample.ORDER_FIREBASE_ID, prepareOrder.firebase_id);

                    // address order
                    params.put(Sample.ORDER_USERSDETAILSID, prepareOrder.usersDetailsId);
                    params.put(Sample.ORDER_USERIDFK, prepareOrder.userIdFk);
                    params.put(Sample.ORDER_NAMEADDRESS, prepareOrder.nameAddress);
                    params.put(Sample.ORDER_ADDRESS, prepareOrder.address);
                    params.put(Sample.ORDER_LATLONG, prepareOrder.latlong);
                    params.put(Sample.ORDER_TYPE, prepareOrder.type);

                    //vehicle order
                    params.put(Sample.ORDER_VCUSTOMERSID, prepareOrder.vCustomersId);
                    params.put(Sample.ORDER_VNAME, prepareOrder.vName);
                    params.put(Sample.ORDER_VBRAND, prepareOrder.vBrand);
                    params.put(Sample.ORDER_MODELS, prepareOrder.models);
                    params.put(Sample.ORDER_VTRANSMISION, prepareOrder.vTransmision);
                    params.put(Sample.ORDER_YEARS, prepareOrder.years);
                    params.put(Sample.ORDER_VID, prepareOrder.vId);
                    params.put(Sample.ORDER_VBRANDID, prepareOrder.vBrandId);
                    params.put(Sample.ORDER_VMODELID, prepareOrder.vModelId);
                    params.put(Sample.ORDER_VTRANSID, prepareOrder.vTransId);
                    params.put(Sample.ORDER_VYEARSID, prepareOrder.vYearsId);

                    //order detail
                    params.put(Sample.ORDER_PRICE, String.valueOf(prepareOrder.price));
                    params.put(Sample.ORDER_PERFUMED, String.valueOf(prepareOrder.perfumed));
                    params.put(Sample.ORDER_INTERIOR_VACCUM, String.valueOf(prepareOrder.interior_vaccum));
                    params.put(Sample.ORDER_ESTIMATED_PRICE, String.valueOf(prepareOrder.estimated_price));
                    params.put(Sample.ORDER_DATETIME, prepareOrder.datetime);

                    for (Map.Entry entry : params.entrySet()) {
                        System.out.println(entry.getKey() + ", " + entry.getValue());
                    }

                    OrderService mService = ApiUtils.OrderService(this);
                    mService.getOrderLink(params).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Log.w("response", new Gson().toJson(response));
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess() == 1) {
                                    isFind = false;
                                    FindingWasher();
                                    LoadWasherOrderFragment();
                                }
                                Log.d(TAG, "Send Order");
                            } else {
                                int statusCode = response.code();
                                try {
                                    Log.d(TAG, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            String message = t.getMessage();
                            Log.d(TAG, message);
                        }
                    });*/


                    getNearbyWasher(prepareOrder.latlong);

                    RemoveBottomFragment();
                    isFind = true;
                    FindingWasher();
                }

                VehicleUser vehicle = (VehicleUser) data.getParcelableExtra(Sample.VEHICLE_OBJECT);
                if (vehicle != null) {
                    PrepareOrderFragment fragment = (PrepareOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bottom);
                    if (fragment != null) {
                        fragment.setSelectedVehicle(vehicle);
                    }
                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    void getNearbyWasher(String latlong) {
        WasherService mService = ApiUtils.WasherService(this);
        mService.getNearbyWasherLink().enqueue(new Callback<NearbyWasher>() {
            @Override
            public void onResponse(Call<NearbyWasher> call, Response<NearbyWasher> response) {
                Log.w("response", new Gson().toJson(response));
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        List<DataNearbyWasher> washer = response.body().getWashers();

                        if (washer.size() > 0) {
                            for (int i = 0; i < washer.size(); i++) {
                                String userId = washer.get(i).getUserId();
                                String username = washer.get(i).getUsername();
                                String email = washer.get(i).getEmail();
                                String name = washer.get(i).getName();
                                String phone = washer.get(i).getPhone();
                                String city = washer.get(i).getCity();
                                String authLevel = washer.get(i).getAuthLevel();
                                String firebaseId = washer.get(i).getFirebaseId();
                                FindWasher findWasher = new FindWasher(userId, username, email, name, phone, city, authLevel, firebaseId, false);
                                listWasher.add(findWasher);

                            }
                            SearchWasher();
                        }
                    }
                    Log.d(TAG, "Send Order");
                } else {
                    int statusCode = response.code();
                    try {
                        Log.d(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NearbyWasher> call, Throwable t) {
                String message = t.getMessage();
                Log.d(TAG, message);
            }
        });

    }

    private void SearchWasher() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(listWasher.get(urutan).firebaseId);

                    JSONObject notification = new JSONObject();
                    notification.put("body", "GET NOW !!!");
                    notification.put("title", "Qwash - NEW ORDER");

                    JSONObject data = new JSONObject();
                    data.put("action", "1");

                    JSONObject order = new JSONObject();

                    JSONObject customer = new JSONObject();
                    customer.put(Sample.ORDER_USERID, prepareOrder.userId);
                    customer.put(Sample.ORDER_USERNAME, prepareOrder.username);
                    customer.put(Sample.ORDER_EMAIL, prepareOrder.email);
                    customer.put(Sample.ORDER_NAME, prepareOrder.name);
                    customer.put(Sample.ORDER_PHONE, prepareOrder.phone);
                    customer.put(Sample.ORDER_AUTHLEVEL, prepareOrder.authLevel);
                    customer.put(Sample.ORDER_FIREBASE_ID, prepareOrder.firebase_id);
                    order.put("customer", customer);

                    JSONObject address = new JSONObject();
                    address.put(Sample.ORDER_USERSDETAILSID, prepareOrder.usersDetailsId);
                    address.put(Sample.ORDER_USERIDFK, prepareOrder.userIdFk);
                    address.put(Sample.ORDER_NAMEADDRESS, prepareOrder.nameAddress);
                    address.put(Sample.ORDER_ADDRESS, prepareOrder.address);
                    address.put(Sample.ORDER_LATLONG, prepareOrder.latlong);
                    address.put(Sample.ORDER_TYPE, prepareOrder.type);
                    order.put("address", address);

                    JSONObject vehicle = new JSONObject();
                    vehicle.put(Sample.ORDER_VCUSTOMERSID, prepareOrder.vCustomersId);
                    vehicle.put(Sample.ORDER_VNAME, prepareOrder.vName);
                    vehicle.put(Sample.ORDER_VBRAND, prepareOrder.vBrand);
                    vehicle.put(Sample.ORDER_MODELS, prepareOrder.models);
                    vehicle.put(Sample.ORDER_VTRANSMISION, prepareOrder.vTransmision);
                    vehicle.put(Sample.ORDER_YEARS, prepareOrder.years);
                    vehicle.put(Sample.ORDER_VID, prepareOrder.vId);
                    vehicle.put(Sample.ORDER_VBRANDID, prepareOrder.vBrandId);
                    vehicle.put(Sample.ORDER_VMODELID, prepareOrder.vModelId);
                    vehicle.put(Sample.ORDER_VTRANSID, prepareOrder.vTransId);
                    vehicle.put(Sample.ORDER_VYEARSID, prepareOrder.vYearsId);
                    order.put("vehicle", vehicle);


                    JSONObject details = new JSONObject();
                    details.put(Sample.ORDER_PRICE, String.valueOf(prepareOrder.price));
                    details.put(Sample.ORDER_PERFUMED, String.valueOf(prepareOrder.perfumed));
                    details.put(Sample.ORDER_INTERIOR_VACCUM, String.valueOf(prepareOrder.interior_vaccum));
                    details.put(Sample.ORDER_ESTIMATED_PRICE, String.valueOf(prepareOrder.estimated_price));
                    details.put(Sample.ORDER_DATETIME, prepareOrder.datetime);
                    order.put("details", details);

                    data.put("order", order);
                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", jsonArray);

                    String result = postToFCM(root.toString());
                    Log.d("RESULT", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    //Toast.makeText(LoginUserActivity.this, "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(Sample.FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + Sample.SERVER_KEY_FIREBASE)
                .build();
        okhttp3.Response response = mClient.newCall(request).execute();
        return response.body().string();
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

    public void LoadAddress(String LatLong) {
        hasIdetify = false;
        search.setText("");
        btnPickLocation.setText(Html.fromHtml("<i>{fa-spinner spin} Indentify Address...</i>"));
        mService.getAddress(LatLong).enqueue(new Callback<AddressFromMapsResponse>() {
            @Override
            public void onResponse(Call<AddressFromMapsResponse> call, Response<AddressFromMapsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("OK")) {
                        List<Address> address = response.body().getResults();
                        search.setText(address.get(0).getFormattedAddress());
                        btnPickLocation.setText(getResources().getString(R.string.btn_pick_location));
                    } else {
                        search.setText("");
                        btnPickLocation.setText(Html.fromHtml("<i>Not Identified...</i>"));
                    }
                    hasIdetify = true;
                    Log.d("MainActivity", "posts loaded from API");

                } else {
                    search.setText("");
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Log.d("MainActivity", "error loading from API, status: " + statusCode);
                    search.setText("");
                    btnPickLocation.setText(Html.fromHtml("<i>Not Identified...</i>"));
                    hasIdetify = false;
                }
            }

            @Override
            public void onFailure(Call<AddressFromMapsResponse> call, Throwable t) {
                btnPickLocation.setText(getResources().getString(R.string.retry));
                hasIdetify = false;
                Log.d("MainActivity", "error loading from API");

            }


        });
    }


    @Override
    public void onCameraIdle() {
        current_latitude = mMap.getCameraPosition().target.latitude;
        current_longitude = mMap.getCameraPosition().target.longitude;
        LoadAddress(current_latitude + "," + current_longitude);
    }


    void setWasher() {

        MarkerOptions marker = new MarkerOptions().position(new LatLng(-6.338405, 106.709180)).title("Washer").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_washer));
        mMap.addMarker(marker);
        marker = new MarkerOptions().position(new LatLng(-6.341807, 106.710146)).title("Washer").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_washer));
        mMap.addMarker(marker);
        marker = new MarkerOptions().position(new LatLng(-6.344006, 106.713008)).title("Washer").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_washer));
        mMap.addMarker(marker);

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
            if (action == Sample.CODE_DEACLINE) {
                //   Log.v("action","deacline");
                //  Toast.makeText(this, "ditolak", Toast.LENGTH_SHORT).show();
            } else if (action == Sample.CODE_ACCEPT) {
                Toast.makeText(this, "Washer found", Toast.LENGTH_SHORT).show();
                JSONObject jsonWasher = new JSONObject(json.getString(Sample.WASHER));
                String firebase_id = jsonWasher.getString(Sample.WASHER_FIREBASE_ID);
                String userId = jsonWasher.getString(Sample.WASHER_USER_ID);
                String email = jsonWasher.getString(Sample.WASHER_EMAIL);
                String name = jsonWasher.getString(Sample.WASHER_NAME);
                String phone = jsonWasher.getString(Sample.WASHER_PHONE);
                String rating = jsonWasher.getString(Sample.WASHER_RATING);

                washerAccepted = new WasherAccepted(firebase_id, userId, email, name, phone, rating, prepareOrder.datetime, String.valueOf(prepareOrder.estimated_price));

                isFind = false;
                FindingWasher();
                LoadWasherOrderFragment();
            } else if (action == Sample.CODE_START) {
                Toast.makeText(this, "Your washer is start working", Toast.LENGTH_SHORT).show();
            } else if (action == Sample.CODE_FINISH_WORKING) {
                JSONObject jsonWasher = new JSONObject(json.getString(Sample.WASHER));
                String firebase_id = jsonWasher.getString(Sample.WASHER_FIREBASE_ID);
                String userId = jsonWasher.getString(Sample.WASHER_USER_ID);
                String email = jsonWasher.getString(Sample.WASHER_EMAIL);
                String name = jsonWasher.getString(Sample.WASHER_NAME);
                String phone = jsonWasher.getString(Sample.WASHER_PHONE);
                String rating = jsonWasher.getString(Sample.WASHER_RATING);

                washerAccepted = new WasherAccepted(firebase_id, userId, email, name, phone, rating, prepareOrder.datetime, String.valueOf(prepareOrder.estimated_price));

                isFind = true;
                FindingWasher();
                RemoveBottomFragment();
                washerAccepted = null;

                OpenActionRating();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void OpenActionRating() {
        startActivity( new Intent(this,RatingActivity.class));
        
    }

}
