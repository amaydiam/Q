package com.qwash.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.gson.Gson;
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
import com.qwash.user.api.model.GlobalError;
import com.qwash.user.api.model.customer.DataCustomer;
import com.qwash.user.api.model.login.Login;
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
    private String TAG = "X";
    private ProgressDialogBuilder dialogProgress;
    // Validator form-form
    private Validator validator;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    private int LOGIN = 1;
    private int LOGIN_SOSIAL = 2;
    private FirebaseUser user;
    private boolean AfterSuccessGetDataFacebook = false;
    private Context context;


    @OnClick(R.id.btn_login)
    void LoginEmail() {
        hideKeyboard();
        validator.validate();
    }

    @OnClick(R.id.btn_facebook)
    void LoginFacebook() {
        if (user != null) {

        } else {
            loginButton.performClick();
        }
    }

    @OnClick(R.id.btn_google_plus)
    void LoginGooglePlus() {
        signInGoogle();
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

        context = getApplicationContext();

        inisialisasiSignIn();

        dialogProgress = new ProgressDialogBuilder(this);
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                remoteLogin(LOGIN, null, null);
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
                                public void onCompleted(JSONObject json, GraphResponse response) {
                                    // Application code
                                        try {
                                            JSONObject data = response.getJSONObject();
                                            String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                            String email = json.getString("email");
                                            String str_firstname = json.getString("first_name");
                                            String str_lastname = json.getString("last_name");
                                            String id = json.getString("id");
                                            String fullName = str_firstname + " " + str_lastname;

                                            remoteLogin(LOGIN_SOSIAL, email, fullName);

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
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    if (AfterSuccessGetDataFacebook) {

                        // User is signed in
                        //  checkoutUser(user);
                    }

                } else {
                    // User is signed out
                }
            }
        };
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


    private void remoteLogin(final int method, final String email, final String fullName) {

        dialogProgress.show("Login...", "Please wait...");

        final String firebase_id = FirebaseInstanceId.getInstance().getToken();
        final Map<String, String> params = new HashMap<>();
        params.put(Sample.EMAIL, method == LOGIN_SOSIAL ? email : emailOrNumberPhone.getText().toString());
        if (method != LOGIN_SOSIAL) {
            params.put(Sample.PASSWORD, password.getText().toString());
        }
        params.put(Sample.FIREBASE_ID, firebase_id);
        Log.v("sebelum_login",firebase_id);

        for (Map.Entry entry : params.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        LoginService mService = ApiUtils.LoginService(this);
        Call<Login> ms = method == LOGIN_SOSIAL ? mService.getLoginSosialLink(params) : mService.getLoginLink(params);
        ms.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

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
                        Prefs.putProfileGender(context, data.getProfileGender());
                        Prefs.putProfilePhoto(context, data.getProfilePhoto());
                        Prefs.putProfileProvince(context, data.getProfileProvince());
                        Prefs.putProfileCity(context, data.getProfileCity());
                        Prefs.putProfileNik(context, data.getProfileNik());
                        Prefs.putOnline(context, data.getOnline());
                        Prefs.putStatus(context, data.getStatus());
                        Prefs.putCreatedAt(context, data.getCreatedAt());
                        Prefs.putUpdatedAt(context, data.getUpdatedAt());
                        Prefs.putActivityIndex(context, Sample.NO_INDEX);

                        Log.v("after_login",data.getFirebaseId());


                        // TODO Addres si customer

/*
                        List<AddressLogin> addressLoginList = response.body().getAddressLogin();
                        if (addressLoginList.size() > 0 && (AddressUser.findAll(AddressUser.class).hasNext())) {
                            AddressUser.deleteAll(AddressUser.class);
                        }

                        for (int i = 0; i < addressLoginList.size(); i++) {
                            String usersDetailsId = addressLoginList.get(i).getUsersDetailsId();
                            String userIdFk = addressLoginList.get(i).getUserIdFk();
                            String nameAddress = addressLoginList.get(i).getNameAddress();
                            String address = addressLoginList.get(i).getAddress();
                            String lat = addressLoginList.get(i).getLatlong();
                            String type = addressLoginList.get(i).getType();
                            String createAt = addressLoginList.get(i).getCreateAt();

                            AddressUser addressUser = new AddressUser(usersDetailsId, userIdFk, nameAddress, address, lat, type, createAt);
                            addressUser.save();
                        }*/

                        toActivity("home", null, null);

                    } else {
                        toActivity("register", email, fullName);
                    }
                } else {
                    int statusCode = response.code();
                    try {
                        String json = response.errorBody().string();
                        GlobalError globalError = new Gson().fromJson(json, GlobalError.class);
                        if (!globalError.getStatus()) {
                            if (method != LOGIN_SOSIAL) {
                                Toast.makeText(getApplicationContext(), globalError.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                toActivity("register", email, fullName);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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


    private void toActivity(String action, String email, String fullName) {
        Intent intent;
        if (action.equalsIgnoreCase("home")) {
            intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent = new Intent(this, RegisterUserActivity.class);
            intent.putExtra(Sample.EMAIL, email);
            intent.putExtra(Sample.FULL_NAME, fullName);
        }
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Prefs.isLogedIn(this)) {
            toActivity("home", null, null);
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
