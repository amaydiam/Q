package com.ad.sample.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.ad.sample.ui.widget.RobotoRegularEditText;
import com.ad.sample.ui.widget.RobotoRegularTextView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    RobotoRegularTextView btnLogin;

    @BindView(R.id.nama)
    RobotoRegularEditText nama;
    @BindView(R.id.email)
    RobotoRegularEditText email;
    @BindView(R.id.password)
    RobotoRegularEditText password;
    @BindView(R.id.btn_register)
    RobotoRegularButton btnRegister;
    @BindView(R.id.btn_facebook)
    FloatingActionButton btnFacebook;
    @BindView(R.id.btn_google_plus)
    FloatingActionButton btnGooglePlus;

    @OnClick(R.id.btn_login)
    void ActionLogin(){
        //Login
        //  startActivity(new Intent(this,LoginActivity.class));
    }

    @OnClick(R.id.btn_register)
    void SubmitRegister(){
        //Submit
          startActivity(new Intent(this,HomeActivity.class));
    }

    @OnClick(R.id.btn_facebook)
    void LoginViaFacebook(){
        Toast.makeText(this, "Login Via Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginViaGooglePlus(){
        Toast.makeText(this, "Login Via Google plus", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule());
        setContentView(R.layout.register_user);
        ButterKnife.bind(this);
        //set underline
        btnLogin.setPaintFlags(btnLogin.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //set icon
        btnFacebook.setImageDrawable(
                new IconDrawable(this, FontAwesomeIcons.fa_facebook)
                        .colorRes(R.color.black_333333)
                        .actionBarSize());
        btnGooglePlus.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_google)
                        .colorRes(R.color.black_333333)
                        .actionBarSize());
    }
}