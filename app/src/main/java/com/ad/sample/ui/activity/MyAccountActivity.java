package com.ad.sample.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.utils.Prefs;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binderbyte on 27/12/16.
 */

public class MyAccountActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_name)
    ImageView imgName;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_email)
    TextView titleEmail;
    @BindView(R.id.title_telp)
    TextView titleTelp;
    @BindView(R.id.title_password)
    TextView titlePassword;

    @OnClick(R.id.btn_logout)
    void Logout() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, you wanted to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Logout email
                        Prefs.Reset(MyAccountActivity.this);

                        //delete data google
                        SharedPreferences settings = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                        settings.edit().remove("email").commit();
                        settings.edit().remove("nama").commit();
                        settings.edit().remove("photo").commit();

                        // Google sign out
                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();

                        Intent intent = new Intent(MyAccountActivity.this, LoginUserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_user);
        ButterKnife.bind(this);

        setToolbar();
        showAccount();
    }

    private void setToolbar() {
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
        toolbarTitle.setText(getResources().getString(R.string.title_my_account));
    }

    private void showAccount() {

        SharedPreferences bb = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = bb.edit();
        String email = bb.getString("email", "");
        String nama = bb.getString("nama", "");
        String foto = bb.getString("photo", "");
        editor.apply();
        titleName.setText("" + nama);
        titleEmail.setText("" + email);
        Log.d("Login", email + nama);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("TAG", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
