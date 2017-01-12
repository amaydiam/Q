package com.ad.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.Sample;
import com.ad.sample.api.ApiUtils;
import com.ad.sample.api.client.auth.LoginService;
import com.ad.sample.api.model.login.DataLogin;
import com.ad.sample.api.model.login.Login;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.ad.sample.ui.widget.RobotoRegularEditText;
import com.ad.sample.utils.Prefs;
import com.ad.sample.utils.ProgressDialogBuilder;
import com.google.gson.Gson;
import com.google.firebase.iid.FirebaseInstanceId;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

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

public class LoginUserActivity extends AppCompatActivity {

    private String TAG = "LoginUserActivity";

    @BindView(R.id.btn_login)
    RobotoRegularButton btnLogin;
    @BindView(R.id.btn_facebook)
    FloatingActionButton btnFacebook;
    @BindView(R.id.btn_google_plus)
    FloatingActionButton btnGooglePlus;
    @BindView(R.id.register)
    TextView register;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Length(min = 4, max = 10, trim = true, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    RobotoRegularEditText password;
    private ProgressDialogBuilder dialogProgress;
    private String firebase_id;

    @OnClick(R.id.btn_login)
    void LoginEmail() {
        validator.validate();
    }

    @OnClick(R.id.btn_facebook)
    void LoginFacebook() {
        Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginGooglePlus() {
        Toast.makeText(this, "Gplus", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.register)
    void Register() {
        startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
    }


    private Validator validator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        ButterKnife.bind(this);

        dialogProgress = new ProgressDialogBuilder(this);
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                remoteLogin();
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
                        Toast.makeText(LoginUserActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        setIcon();

    }

    private void setIcon() {
        btnFacebook.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_facebook)
                .colorRes(R.color.blue_2196F3)
                .actionBarSize());

        btnGooglePlus.setImageDrawable(new IconDrawable(this, EntypoIcons.entypo_google)
                .colorRes(R.color.blue_2196F3)
                .actionBarSize());
    }


    private void remoteLogin() {
        Log.d(TAG, "remote login...");
        dialogProgress.show("LoginService ...", "Please wait...");

        Map<String, String> params = new HashMap<>();
        params.put(Sample.EMAIL, email.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.ID_FIREBASE, firebase_id);

        LoginService mService = ApiUtils.LoginService(this);
        mService.getLoginLink(params).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.w("response", new Gson().toJson(response));
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        DataLogin dataLogin = response.body().getDataLogin();

                        Prefs.putToken(LoginUserActivity.this, response.body().getToken());

                        Prefs.putUserId(LoginUserActivity.this, dataLogin.getUserId());
                        Prefs.putUsername(LoginUserActivity.this, dataLogin.getUsername());
                        Prefs.putEmail(LoginUserActivity.this, dataLogin.getEmail());
                        Prefs.putName(LoginUserActivity.this, dataLogin.getName());
                        Prefs.putPhone(LoginUserActivity.this, dataLogin.getName());
                        Prefs.putAuthLevel(LoginUserActivity.this, String.valueOf(dataLogin.getAuthLevel()));

                        toHomeActivity();

                    }
                    Log.d(TAG, "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "error loading from API, status: " + statusCode);
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                String message = t.getMessage();
                Log.d(TAG, message);
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebase_id = FirebaseInstanceId.getInstance().getToken();
        if (Prefs.isLogedIn(this)) {
            toHomeActivity();
        }
    }

}
