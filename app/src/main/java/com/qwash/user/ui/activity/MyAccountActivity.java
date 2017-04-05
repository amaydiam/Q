package com.qwash.user.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import com.qwash.user.ui.activity.BaseActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.user.R;
import com.qwash.user.model.AddressUser;
import com.qwash.user.model.temporary.ChangePassword;
import com.qwash.user.ui.fragment.DialogChangeLanguageFragment;
import com.qwash.user.ui.fragment.DialogChangePasswordFragment;
import com.qwash.user.ui.fragment.DialogRequestNewPasswordFragment;
import com.qwash.user.ui.widget.RobotoRegularTextView;
import com.qwash.user.utils.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binderbyte on 27/12/16.
 */

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.washer_photo)
    AvatarView washerPhoto;
    @BindView(R.id.full_name)
    RobotoRegularTextView fullName;
    @BindView(R.id.email)
    RobotoRegularTextView email;
    @BindView(R.id.phone)
    RobotoRegularTextView phone;

    @OnClick(R.id.layout_change_password)
    public void ChangePassword() {

        FragmentManager ft = getSupportFragmentManager();
        DialogRequestNewPasswordFragment dialogRequestNewPasswordFragment = new DialogRequestNewPasswordFragment();
        dialogRequestNewPasswordFragment.show(ft, "request_password");
    }


    @OnClick(R.id.layout_change_language)
    public void ChangeLanguage() {

        FragmentManager ft = getSupportFragmentManager();
        DialogChangeLanguageFragment dialogChangeLanguageFragment = new DialogChangeLanguageFragment();
        dialogChangeLanguageFragment.show(ft, "request_language");
    }



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

                        //delete database
                        if (AddressUser.findAll(AddressUser.class).hasNext()) {
                            AddressUser.deleteAll(AddressUser.class);
                        }

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
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.my_account_user);
        ButterKnife.bind(this);
        setToolbar();
        setData();
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
        titleToolbar.setText(getResources().getString(R.string.title_my_account));
    }

    private void setData() {
        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(washerPhoto, Prefs.getProfilePhoto(this), Prefs.getFullName(this));
        fullName.setText(Prefs.getFullName(this));
        email.setText(Prefs.getEmail(this));
        phone.setText(Prefs.getUsername(this));
    }



    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onChangePassword(ChangePassword cp) {
        if (cp.getStatus()) {
            FragmentManager ft = getSupportFragmentManager();
            DialogChangePasswordFragment changePasswordFragment = new DialogChangePasswordFragment();
            changePasswordFragment.setPassword(cp.getPassword());
            changePasswordFragment.show(ft, "change_password");
        }

        ChangePassword stickyEvent = EventBus.getDefault().getStickyEvent(ChangePassword.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}



