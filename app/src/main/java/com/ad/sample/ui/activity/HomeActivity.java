package com.ad.sample.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.ui.fragment.MenuHomeFragment;
import com.ad.sample.ui.fragment.PrepareOrderFragment;
import com.ad.sample.ui.fragment.WasherOrderFragment;
import com.ad.sample.ui.widget.RobotoRegularEditText;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsIcons;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class HomeActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        MenuHomeFragment.OnSelectedMenuListener,
        PrepareOrderFragment.OnOrderedListener,
        WasherOrderFragment.OnWasherOrderListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.search)
    RobotoRegularEditText search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.card_view_toolbar)
    CardView cardViewToolbar;

    @OnTextChanged(value = R.id.search,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onChanged(Editable editable) {
        ShowHideAcSearch();
    }

    private void ShowHideAcSearch() {
        try {
            String val_search = search.getText().toString().trim();
            if (val_search.length() == 0) {
                acSearch.setVisible(false);
            } else {
                acSearch.setVisible(true);
            }
            supportInvalidateOptionsMenu();
        } catch (Exception e) {

        }
    }

    @BindView(R.id.pick_location)
    ImageView pickLocation;

    @BindView(R.id.btn_work)
    FloatingActionButton btnWork;
    @BindView(R.id.btn_home)
    FloatingActionButton btnHome;
    @BindView(R.id.btn_my_location)
    FloatingActionButton btnMyLocation;

    Fragment current_fragment = null;
    @BindView(R.id.btn_menu_home)
    FloatingActionButton btnMenuHome;
    @BindView(R.id.header_menu_home)
    View headerMenuHome;

    private double current_latitude, current_longitude;
    private View mapView;
    private boolean isShowMenuHome;
    private MenuHomeFragment menu_home_fragment;
    private MenuItem acSearch;


    @OnClick(R.id.btn_menu_home)
    void ActionMenuHome() {
        if (isShowMenuHome)
            ShowMenuHome(false);
        else
            ShowMenuHome(true);
    }

    @OnClick(R.id.btn_work)
    void ActionWork() {
        ShowMenuHome(false);
        startActivity(new Intent(this, SelectLocationActivity.class));
        //Toast.makeText(this, "Work CLikced!!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_home)
    void ActionHome() {
        ShowMenuHome(false);
        LoadPrepareOrderFragment();
        //  Toast.makeText(this, "Home CLikced!!", Toast.LENGTH_SHORT).show();
    }

    /*@OnClick(R.id.btn_my_location)
    void ActionMyLocation() {
        Toast.makeText(this, "My Location CLikced!!", Toast.LENGTH_SHORT).show();
    }*/


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

        //set Toolbar 
        setSupportActionBar(toolbar);/*
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_search)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Menu Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });
*/
        isShowMenuHome = false;
        ShowMenuHome(false);
        //set icon other menu

        pickLocation.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_location_pin)
                        .colorRes(R.color.colorAccent)
                        .sizeDp(48));

        int paddingBottomInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (48 / 4) * 3, getResources()
                        .getDisplayMetrics());
        pickLocation.setPadding(0, 0, 0, paddingBottomInDp);

        btnWork.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_bag)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        btnHome.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_home)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        btnMyLocation.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_my_location)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());

        pickLocationShow(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    private void LoadMenuHomeFragment() {
        menu_home_fragment = new MenuHomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_menu_home, menu_home_fragment).commit();
    }

    private void RemoveMenuHomeFragment() {
        if (menu_home_fragment != null)
            getSupportFragmentManager().beginTransaction().remove(menu_home_fragment).commit();
        menu_home_fragment = null;
    }

    private void LoadPrepareOrderFragment() {
        current_fragment = new PrepareOrderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commit();
    }

    private void LoadWasherOrderFragment() {
        current_fragment = new WasherOrderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commit();
    }

    private void RemoveBottomFragment() {
        getSupportFragmentManager().beginTransaction().remove(current_fragment).commit();
        current_fragment = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMyLocationEnabled(true);
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
                ShowMenuHome(false);
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    float zoomLevel = mMap.getCameraPosition().zoom;
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoomLevel);
                    mMap.animateCamera(cameraUpdate);
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = mMap.getCameraPosition();
                current_latitude = position.target.latitude;
                current_longitude = position.target.longitude;
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

        pickLocationShow(true);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(current_latitude, current_longitude)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void pickLocationShow(boolean show) {
        if (show)
            pickLocation.setVisibility(View.VISIBLE);
        else
            pickLocation.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
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
    public void onOrdered() {
        LoadWasherOrderFragment();
    }

    @Override
    public void onCancelOrder() {
        RemoveBottomFragment();
    }


    @Override
    public void OnSelectedMenu(View view) {
        ShowMenuHome(false);
        int id = view.getId();
        switch (id) {
            case R.id.menu_home:
                break;
            case R.id.menu_notification:
                break;
            case R.id.menu_history:
                break;
            case R.id.menu_help:
                break;
            case R.id.menu_my_account:
                break;
            default:
                break;
        }
    }

    private void ShowMenuHome(boolean show) {
        if (show) {
            HideKeboard();
            headerMenuHome.setVisibility(View.VISIBLE);
            cardViewToolbar.setVisibility(View.GONE);
            LoadMenuHomeFragment();
            isShowMenuHome = true;
            //set icon main menu
            btnMenuHome.setImageDrawable(
                    new IconDrawable(this, MaterialIcons.md_close)
                            .colorRes(R.color.black_424242)
                            .actionBarSize());
        } else {
            headerMenuHome.setVisibility(View.GONE);
            cardViewToolbar.setVisibility(View.VISIBLE);
            RemoveMenuHomeFragment();
            isShowMenuHome = false;
            //set icon main menu
            btnMenuHome.setImageDrawable(
                    new IconDrawable(this, MaterialIcons.md_menu)
                            .colorRes(R.color.black_424242)
                            .actionBarSize());
        }
    }

    private void HideKeboard() {

        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        acSearch = menu.findItem(R.id.action_search);
        acSearch.setIcon(
                new IconDrawable(this, MaterialIcons.md_search)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        acSearch.setVisible(false);
        ShowHideAcSearch();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_search:
                HideKeboard();
                search.setText(null);
                startActivity(new Intent(this, SelectLocationActivity.class));
                break;

            default:
                break;
        }

        return true;
    }

}
