package com.qwash.user.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import com.qwash.user.ui.activity.BaseActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
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
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.register.RegisterService;
import com.qwash.user.api.model.customer.DataCustomer;
import com.qwash.user.api.model.register.Register;
import com.qwash.user.ui.activity.register.VerificationCodeActivity;
import com.qwash.user.ui.widget.RobotoRegularButton;
import com.qwash.user.ui.widget.RobotoRegularEditText;
import com.qwash.user.utils.Prefs;
import com.qwash.user.utils.ProgressDialogBuilder;
import com.qwash.user.utils.TextUtils;

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

public class RegisterUserActivity extends BaseActivity {

    private static final String TAG = "RegisterUserActivity";
    @BindView(R.id.btn_new_account)
    RobotoRegularButton btnRegister;
    @BindView(R.id.btn_facebook)
    FloatingActionButton btnFacebook;
    @BindView(R.id.btn_google_plus)
    FloatingActionButton btnGooglePlus;

    @NotEmpty
    @Length(min = 2, max = 30, trim = true, messageResId = R.string.val_full_name)
    @BindView(R.id.full_name)
    RobotoRegularEditText fullName;


    @BindView(R.id.no_telp)
    RobotoRegularEditText noTelp;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Password(min = 5, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    RobotoRegularEditText password;

    @NotEmpty
    @ConfirmPassword()
    @BindView(R.id.confirm_password)
    RobotoRegularEditText confirmPassword;


    private String firebase_id;
    private ProgressDialogBuilder dialogProgress;
    private Validator validator;
    private Context context;

    @OnClick(R.id.btn_new_account)
    void SubmitRegister() {
        validator.validate();
    }


    @BindView(R.id.cb_agree)
    CheckBox cbAgree;

    @OnClick(R.id.btn_facebook)
    void LoginViaFacebook() {
        Toast.makeText(this, "LoginService Via Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginViaGooglePlus() {
        Toast.makeText(this, "LoginService Via Google plus", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.register_user);
        ButterKnife.bind(this);

        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                if (!cbAgree.isChecked()) {
                    showTermNotif();
                } else {

                    RegisterAction();
                }
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
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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

    public void showTermNotif() {
        final Dialog dialog = new Dialog(RegisterUserActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_agreement_register);

        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }


    private void RegisterAction() {
        dialogProgress.show("Register...", "Please wait...");

        firebase_id = FirebaseInstanceId.getInstance().getToken();

        String inputStr = noTelp.getText().toString().trim();
        String num = "+62" + TextUtils.ReplaceFirstCaracters(inputStr);

        Map<String, String> params = new HashMap<>();
        params.put(Sample.FULL_NAME, fullName.getText().toString());
        params.put(Sample.EMAIL, email.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.USERNAME, num);
        params.put(Sample.FIREBASE_ID, firebase_id);
        params.put(Sample.LAT, "0");
        params.put(Sample.LONG, "0");


        RegisterService mService = ApiUtils.RegisterService(this);
        mService.getRegisterLink(params).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {

                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        DataCustomer data = response.body().getDataCustomer();
                        Prefs.putToken(context, response.body().getToken());
                        Prefs.putUserId(context, data.getUserId());
                        Prefs.putEmail(context, data.getEmail());
                        Prefs.putUsername(context, data.getUsername());
                        Prefs.putType(context, data.getType());
                        Prefs.putFullName(context, data.getFullName());
                        Prefs.putSaldo(context, data.getSaldo());
                        Prefs.putFirebaseId(context, data.getFirebaseId());
                        Prefs.putGeometryLat(context, data.getGeometryLat());
                        Prefs.putGeometryLong(context, data.getGeometryLong());
                        Prefs.putOnline(context, data.getOnline());
                        Prefs.putStatus(context, data.getStatus());
                        Prefs.putCreatedAt(context, data.getCreatedAt());
                        Prefs.putUpdatedAt(context, data.getUpdatedAt());
                        Prefs.putActivityIndex(context, Sample.ACTIVATION_CODE_INDEX);
                        toVerificationCodeActivity();

                    }
                } else {
                    int statusCode = response.code();
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
