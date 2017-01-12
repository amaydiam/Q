package com.ad.sample.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    RobotoRegularTextView btnLogin;
    @BindView(R.id.btn_register)
    RobotoRegularButton btnRegister;
    @BindView(R.id.btn_facebook)
    FloatingActionButton btnFacebook;
    @BindView(R.id.btn_google_plus)
    FloatingActionButton btnGooglePlus;

    @NotEmpty
    @Length(min = 2, max = 50, trim = true, messageResId = R.string.val_name_length)
    @BindView(R.id.nama)
    RobotoRegularEditText nama;

    @NotEmpty
    @Length(min = 6, max = 50, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Length(min = 4, max = 100, trim = true, messageResId = R.string.val_password_length)
    @Password
    @BindView(R.id.password)
    RobotoRegularEditText password;

    @OnClick(R.id.btn_login)
    void ActionLogin(){
        finish();
    }

    @OnClick(R.id.btn_register)
    void SubmitRegister(){
        //Login
        validator.validate();
        startActivity(new Intent(this, VerificationCodeActivity.class));
    }

    @OnClick(R.id.btn_facebook)
    void LoginViaFacebook(){
        Toast.makeText(this, "Login Via Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginViaGooglePlus(){
        Toast.makeText(this, "Login Via Google plus", Toast.LENGTH_SHORT).show();
    }

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        ButterKnife.bind(this);
        setValidator();
        //set underline
        btnLogin.setPaintFlags(btnLogin.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //set icon
        btnFacebook.setImageDrawable(
                new IconDrawable(this, FontAwesomeIcons.fa_facebook)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());
        btnGooglePlus.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_google)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());
    }

    private void setValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                registerSucces();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                registerFailed(errors);
            }
        });
    }

    private void registerSucces() {

        String result_nama = nama.getText().toString();
        String result_email = email.getText().toString();
        String result_password = password.getText().toString();

        startActivity(new Intent(this, LoginUserActivity.class));
    }

    private void registerFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getApplicationContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule());
    }
}
