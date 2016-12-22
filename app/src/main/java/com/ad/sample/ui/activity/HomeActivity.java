package com.ad.sample.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ad.sample.R;
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
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsIcons;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, PrepareOrderFragment.OnOrderedListener, WasherOrderFragment.OnWasherOrderListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.search)
    RobotoRegularEditText search;
    @BindView(R.id.btn_clear_search)
    IconButton btnClearSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.pick_location)
    ImageView pickLocation;

    @BindView(R.id.btn_work)
    FloatingActionButton btnWork;
    @BindView(R.id.btn_home)
    FloatingActionButton btnHome;
    @BindView(R.id.btn_my_location)
    FloatingActionButton btnMyLocation;
    @BindView(R.id.fragment_bottom)
    FrameLayout fragmentBottom;

    Fragment current_fragment = null;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.btn_main_menu)
    FloatingActionButton btnMainMenu;
    @BindView(R.id.header_navigation_view)
    View headerNavigationView;

    private double current_latitude, current_longitude;
    private View mapView;
    private boolean isShowNavigation;


    @OnClick(R.id.btn_main_menu)
    void ActionMainMenu() {
        if (isShowNavigation)
            ShowMainMenu(false);
        else
            ShowMainMenu(true);
    }

    @OnClick(R.id.btn_work)
    void ActionWork() {
        Toast.makeText(this, "Work CLikced!!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_home)
    void ActionHome() {
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
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_search)
                        .colorRes(R.color.black_333333)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Menu Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });

        isShowNavigation = false;
        navigationView.setNavigationItemSelectedListener(this);
        ShowMainMenu(false);

        //set icon main menu
        btnMainMenu.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_menu)
                        .colorRes(R.color.black_333333)
                        .actionBarSize());

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
                        .colorRes(R.color.black_333333)
                        .actionBarSize());
        btnHome.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_home)
                        .colorRes(R.color.black_333333)
                        .actionBarSize());
        btnMyLocation.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_my_location)
                        .colorRes(R.color.black_333333)
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

    private void LoadPrepareOrderFragment() {
        fragmentBottom.setVisibility(View.VISIBLE);
        current_fragment = new PrepareOrderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commit();
    }

    private void LoadWasherOrderFragment() {
        current_fragment = new WasherOrderFragment();
        fragmentBottom.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commit();
    }

    private void RemoveBottomFragment() {
        getSupportFragmentManager().beginTransaction().remove(current_fragment).commit();
        fragmentBottom.setVisibility(View.GONE);
        current_fragment = null;
    }

    private void SetCustomMenu() {

        Menu menu = navigationView.getMenu();
        MenuItem menu_home = menu.findItem(R.id.menu_home);
        menu_home.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_comment_question_outline).colorRes(R.color.white).actionBarSize());
        MenuItem menu_notification = menu.findItem(R.id.menu_notification);
        menu_notification.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_map_marker).colorRes(R.color.white).actionBarSize());
        MenuItem menu_history = menu.findItem(R.id.menu_history);
        menu_history.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_map_marker).colorRes(R.color.white).actionBarSize());
        MenuItem menu_help = menu.findItem(R.id.menu_help);
        menu_help.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_map_marker).colorRes(R.color.white).actionBarSize());
        MenuItem menu_my_account = menu.findItem(R.id.menu_my_account);
        menu_my_account.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_map_marker).colorRes(R.color.white).actionBarSize());

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ShowMainMenu(false);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_home:
                return true;
            case R.id.menu_notification:
                return true;
            case R.id.menu_history:
                return true;
            case R.id.menu_help:
                return true;
            case R.id.menu_my_account:
                return true;
            default:
                return false;
        }
    }

    private void ShowMainMenu(boolean show) {
        if (show) {
            headerNavigationView.setVisibility(View.VISIBLE);
            navigationView.setVisibility(View.VISIBLE);
            isShowNavigation = true;
        } else {
            headerNavigationView.setVisibility(View.GONE);
            navigationView.setVisibility(View.GONE);
            isShowNavigation = false;
        }
    }
}
