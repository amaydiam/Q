package com.ad.sample.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.Sample;
import com.ad.sample.api.ApiUtils;
import com.ad.sample.api.client.auth.LoginService;
import com.ad.sample.api.client.register.RegisterService;
import com.ad.sample.api.model.login.DataLogin;
import com.ad.sample.api.model.login.Login;
import com.ad.sample.api.model.register.DataRegister;
import com.ad.sample.api.model.register.Register;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.ad.sample.ui.widget.RobotoRegularEditText;
import com.ad.sample.ui.widget.RobotoRegularTextView;
import com.ad.sample.utils.Prefs;
import com.ad.sample.utils.ProgressDialogBuilder;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {

    private static final String TAG = "RegisterUserActivity";
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
    private String firebase_id;
    private ProgressDialogBuilder dialogProgress;

    @OnClick(R.id.btn_login)
    void ActionLogin() {
        finish();
    }

    @OnClick(R.id.btn_register)
    void SubmitRegister() {
        validator.validate();
    }

    @OnClick(R.id.btn_facebook)
    void LoginViaFacebook() {
        Toast.makeText(this, "LoginService Via Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginViaGooglePlus() {
        Toast.makeText(this, "LoginService Via Google plus", Toast.LENGTH_SHORT).show();
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

        dialogProgress = new ProgressDialogBuilder(this); validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                remoteRegister();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getApplicationContext());

                    // Display error messages ;)
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(RegisterUserActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //set underline
        btnLogin.setPaintFlags(btnLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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


    private void remoteRegister() {
        Log.d(TAG, "remote register...");
        dialogProgress.show("RegisterService ...", "Please wait...");

        firebase_id = FirebaseInstanceId.getInstance().getToken();
        Map<String, String> params = new HashMap<>();
        params.put(Sample.EMAIL, email.getText().toString());
        params.put(Sample.NAME, nama.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.AUTH_LEVEL, String.valueOf(10));
        params.put(Sample.PHONE, "083897547006");
        params.put(Sample.CITY, "Tangerang Selatan");

        params.put(Sample.FIREBASE_ID, firebase_id);

        for (Map.Entry entry : params.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        RegisterService mService = ApiUtils.RegisterService(this);
        mService.getRegisterLink(params).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Log.w("response", new Gson().toJson(response));
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        DataRegister dataRegister = response.body().getDataRegister();
/*
                        Prefs.putToken(RegisterUserActivity.this, response.body().getToken());
                        Prefs.putFirebaseId(RegisterUserActivity.this, firebase_id);

                        Prefs.putUserId(RegisterUserActivity.this, dataRegister.getUserId());
                        Prefs.putUsername(RegisterUserActivity.this, dataRegister.getUsername());
                        Prefs.putEmail(RegisterUserActivity.this, dataRegister.getEmail());
                        Prefs.putName(RegisterUserActivity.this, dataRegister.getName());
                        Prefs.putPhone(RegisterUserActivity.this, dataRegister.getName());
                        Prefs.putAuthLevel(RegisterUserActivity.this, String.valueOf(dataRegister.getAuthLevel()));*/

                        toVerificationCodeActivity();

                    }
                    Log.d(TAG, "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "error loading from API, status: " + statusCode);
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        password.setText("");
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                String message = t.getMessage();
                Log.d(TAG, message);
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void toVerificationCodeActivity() {
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule());
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebase_id = FirebaseInstanceId.getInstance().getToken();
    }

    void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
