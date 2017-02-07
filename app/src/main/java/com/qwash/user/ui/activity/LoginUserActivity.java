package com.qwash.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.auth.LoginService;
import com.qwash.user.api.model.login.AddressLogin;
import com.qwash.user.api.model.login.DataLogin;
import com.qwash.user.api.model.login.Login;
import com.qwash.user.api.model.vehicle.DataVehicle;
import com.qwash.user.model.AddressUser;
import com.qwash.user.model.VehicleUser;
import com.qwash.user.ui.widget.RobotoRegularButton;
import com.qwash.user.ui.widget.RobotoRegularEditText;
import com.qwash.user.utils.Prefs;
import com.qwash.user.utils.ProgressDialogBuilder;

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

public class LoginUserActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //GoogleSignIn Firebase
    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.button_facebook_login)
    LoginButton loginButton;
    @BindView(R.id.btn_login)
    RobotoRegularButton btnLogin;
    @BindView(R.id.btn_facebook)
    FloatingActionButton btnFacebook;
    @BindView(R.id.btn_google_plus)
    FloatingActionButton btnGooglePlus;
    @BindView(R.id.register)
    TextView register;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_or_number_length)
    @BindView(R.id.email_or_number_phone)
    RobotoRegularEditText emailOrNumberPhone;

    @NotEmpty
    @Length(min = 4, max = 10, trim = true, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    com.txusballesteros.PasswordEditText password;
    private String TAG = "LoginUserActivity";
    private ProgressDialogBuilder dialogProgress;
    // Validator form-form
    private Validator validator;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    @OnClick(R.id.btn_login)
    void LoginEmail() {
        hideKeyboard();
        validator.validate();
    }

    @OnClick(R.id.btn_facebook)
    void LoginFacebook() {
        loginButton.performClick();
        //Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginGooglePlus() {
        signInGoogle();
        startActivity(new Intent(this, HomeActivity.class));
        //Toast.makeText(this, "Gplus", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.register)
    void Register() {
        startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule());
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login_user);
        ButterKnife.bind(this);
        inisialisasiSignIn();

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

        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    hideKeyboard();
                    validator.validate();
                    return true;
                }
                return false;
            }
        });
        setIcon();

    }

    private void inisialisasiSignIn() {
        //LoginFacebook
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

                if (loginResult.getAccessToken() != null) {

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    // Application code
                                    try {
                                        String email = object.getString("email");
                                        String name = object.getString("name");

                                        startActivity(new Intent(LoginUserActivity.this, HomeActivity.class));
                                        SharedPreferences bb = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = bb.edit();
                                        editor.putString("email", email);
                                        editor.putString("nama", name);
                                        editor.commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday");
                    request.setParameters(parameters);
                    request.executeAsync();

                } else {
                    //
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //LoginGoogle
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    checkoutUser(user);
                } else {
                    // User is signed out
                }
            }
        };
    }

    private void checkoutUser(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, HomeActivity.class));
            SharedPreferences bb = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = bb.edit();
            editor.putString("email", user.getEmail().toString());
            editor.putString("nama", user.getDisplayName().toString());
            editor.putString("photo", user.getPhotoUrl().toString());
            editor.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                //activity sign in failed
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
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
        dialogProgress.show("LoginService ...", "Please wait...");

        final String firebase_id = FirebaseInstanceId.getInstance().getToken();
        Map<String, String> params = new HashMap<>();
        params.put(Sample.EMAIL, emailOrNumberPhone.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.FIREBASE_ID, firebase_id);
        params.put(Sample.AUTH_LEVEL, "10");

        LoginService mService = ApiUtils.LoginService(this);
        mService.getLoginLink(params).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        DataLogin dataLogin = response.body().getDataLogin();

                        Prefs.putToken(LoginUserActivity.this, response.body().getToken());
                        Prefs.putFirebaseId(LoginUserActivity.this, firebase_id);

                        Prefs.putUserId(LoginUserActivity.this, dataLogin.getUserId());
                        Prefs.putUsername(LoginUserActivity.this, dataLogin.getUsername());
                        Prefs.putEmail(LoginUserActivity.this, dataLogin.getEmail());
                        Prefs.putName(LoginUserActivity.this, dataLogin.getName());
                        Prefs.putPhone(LoginUserActivity.this, dataLogin.getPhone());
                        Prefs.putPhoto(LoginUserActivity.this, dataLogin.getPhoto());
                        Prefs.putAuthLevel(LoginUserActivity.this, String.valueOf(dataLogin.getAuthLevel()));


                        List<DataVehicle> dataVehicleList = response.body().getVehicle();
                        if (dataVehicleList.size() > 0 && (VehicleUser.findAll(VehicleUser.class).hasNext())) {
                            VehicleUser.deleteAll(VehicleUser.class);
                        }

                        for (int i = 0; i < dataVehicleList.size(); i++) {
                            String vCustomersId = dataVehicleList.get(i).getVCustomersId();
                            String vName = dataVehicleList.get(i).getVName();
                            String vBrand = dataVehicleList.get(i).getVBrand();
                            String models = dataVehicleList.get(i).getModels();
                            String vTransmission = dataVehicleList.get(i).getVTransmission();
                            String years = dataVehicleList.get(i).getYears();
                            String vId = dataVehicleList.get(i).getVId();
                            String vBrandId = dataVehicleList.get(i).getVBrandId();
                            String vModelId = dataVehicleList.get(i).getVModelId();
                            String vTransId = dataVehicleList.get(i).getVTransId();
                            String vYearsId = dataVehicleList.get(i).getVYearsId();
                            VehicleUser vehicleUser = new VehicleUser(vCustomersId, vName, vBrand, models, vTransmission, years, vId, vBrandId, vModelId, vTransId, vYearsId);
                            vehicleUser.save();
                        }


                        List<AddressLogin> addressLoginList = response.body().getAddressLogin();
                        if (addressLoginList.size() > 0 && (AddressUser.findAll(AddressUser.class).hasNext())) {
                            AddressUser.deleteAll(AddressUser.class);
                        }

                        for (int i = 0; i < addressLoginList.size(); i++) {
                            String usersDetailsId = addressLoginList.get(i).getUsersDetailsId();
                            String userIdFk = addressLoginList.get(i).getUserIdFk();
                            String nameAddress = addressLoginList.get(i).getNameAddress();
                            String address = addressLoginList.get(i).getAddress();
                            String latlong = addressLoginList.get(i).getLatlong();
                            String type = addressLoginList.get(i).getType();
                            String createAt = addressLoginList.get(i).getCreateAt();

                            AddressUser addressUser = new AddressUser(usersDetailsId, userIdFk, nameAddress, address, latlong, type, createAt);
                            addressUser.save();
                        }

                        toHomeActivity();

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
            public void onFailure(Call<Login> call, Throwable t) {
                String message = t.getMessage();
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
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        if (Prefs.isLogedIn(this)) {
            toHomeActivity();
        }
    }

    void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
